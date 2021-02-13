package com.aslnstbk.komek.help_need.data

import com.aslnstbk.komek.common.data.AbstractValueEventListener
import com.aslnstbk.komek.common.data.EMPTY_STRING
import com.aslnstbk.komek.common.data.HELP_NEED_PEOPLE_HELP
import com.aslnstbk.komek.common.data.HELP_NEED_REF_DB
import com.aslnstbk.komek.common.data.models.HelpNeed
import com.aslnstbk.komek.common.data.models.PersonHelp
import com.aslnstbk.komek.help_need.domain.HelpNeedRepository
import com.aslnstbk.komek.utils.mappers.HelpNeedMapper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase

class DefaultHelpNeedRepository(
    private val firebaseDatabase: FirebaseDatabase,
    private val firebaseAuth: FirebaseAuth,
    private val helpNeedMapper: HelpNeedMapper
) : HelpNeedRepository {

    override fun getHelpNeed(
        helpNeedId: String,
        onSuccess: (HelpNeed) -> Unit,
        onFail: () -> Unit
    ) {
        firebaseDatabase.getReference(HELP_NEED_REF_DB)
            .child(helpNeedId)
            .addValueEventListener(object : AbstractValueEventListener() {
                override fun onDataChange(snapshot: DataSnapshot) {
                    super.onDataChange(snapshot)

                    if (snapshot.key == helpNeedId) {
                        onSuccess(helpNeedMapper.map(snapshot))
                    }
                }
            })
    }

    override fun onHelp(
        helpNeedId: String,
        transmissionLetter: String,
        onSuccess: () -> Unit,
        onFail: () -> Unit
    ) {
        val currentUser = firebaseAuth.currentUser!!
        val personHelp = PersonHelp(
            id = currentUser.uid,
            userName = currentUser.displayName ?: EMPTY_STRING,
            userPhoto = currentUser.photoUrl.toString(),
            transmissionLetter = transmissionLetter
        )

        firebaseDatabase.getReference(HELP_NEED_REF_DB)
            .child(helpNeedId)
            .child(HELP_NEED_PEOPLE_HELP)
            .child(currentUser.uid)
            .setValue(personHelp)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener {
                onFail()
            }
    }
}