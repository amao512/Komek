package com.aslnstbk.komek.common.data

import com.aslnstbk.komek.common.data.models.HelpNeed
import com.aslnstbk.komek.common.data.models.PersonHelp
import com.aslnstbk.komek.common.domain.HelpDataSource
import com.aslnstbk.komek.utils.mappers.HelpNeedMapper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase

const val EMPTY_STRING = ""
const val HELP_NEED_REF_DB = "help_need_ref_db"
const val HELP_NEED_OWNER_ID = "ownerId"
const val HELP_NEED_PEOPLE_HELP = "peopleHelp"

class HelpFirebaseDataSource(
    private val firebaseDatabase: FirebaseDatabase,
    private val firebaseAuth: FirebaseAuth,
    private val helpNeedMapper: HelpNeedMapper
) : HelpDataSource {

    override fun createHelpNeed(
        helpNeed: HelpNeed,
        onSuccess: () -> Unit,
        onFail: () -> Unit
    ) {
        firebaseDatabase.getReference(HELP_NEED_REF_DB)
            .child(helpNeed.id)
            .setValue(helpNeed)
            .addOnSuccessListener {
                onSuccess()
            }.addOnFailureListener {
                onFail()
            }
    }

    override fun isOwnHelpNeed(snapshot: DataSnapshot): Boolean {
        return snapshot.child(HELP_NEED_OWNER_ID).value == firebaseAuth.currentUser?.uid
    }

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

    override fun getHelpNeedPeople(
        onSuccess: (List<HelpNeed>) -> Unit,
        onFail: () -> Unit
    ) {
        firebaseDatabase.getReference(HELP_NEED_REF_DB)
            .addValueEventListener(object : AbstractValueEventListener() {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val helpNeedModeList: MutableList<HelpNeed> = mutableListOf()

                    snapshot.children.forEach {
                        if (!isOwnHelpNeed(it)) {
                            helpNeedModeList.add(helpNeedMapper.map(it))
                        }
                    }

                    onSuccess(helpNeedModeList)
                }
            })
    }

    override fun createPersonHelp(
        helpNeedId: String,
        helpName: String,
        transmissionLetter: String,
        onSuccess: () -> Unit,
        onFail: () -> Unit
    ) {
        val currentUser = firebaseAuth.currentUser!!
        val personHelp = PersonHelp(
            id = currentUser.uid,
            helpNeedId = helpNeedId,
            helpName = helpName,
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

    override fun getPeopleHelp(
        onSuccess: (List<PersonHelp>) -> Unit,
        onFail: () -> Unit
    ) {
        firebaseDatabase.getReference(HELP_NEED_REF_DB)
            .addValueEventListener(object : AbstractValueEventListener() {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val peopleHelp: MutableList<PersonHelp> = mutableListOf()

                    snapshot.children.forEach {
                        if (isOwnHelpNeed(it)) {
                            helpNeedMapper.map(it).peopleHelp.forEach { personHelp ->
                                peopleHelp.add(personHelp)
                            }
                        }
                    }

                    onSuccess(peopleHelp)
                }
            })
    }

    override fun updatePersonHelp(
        personHelp: PersonHelp,
        onSuccess: () -> Unit,
        onFail: () -> Unit
    ) {
        firebaseDatabase.getReference(HELP_NEED_REF_DB)
            .child(personHelp.helpNeedId)
            .child(HELP_NEED_PEOPLE_HELP)
            .child(personHelp.id)
            .setValue(personHelp)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener {
                onFail()
            }
    }
}