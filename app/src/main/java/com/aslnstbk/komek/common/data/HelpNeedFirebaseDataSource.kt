package com.aslnstbk.komek.common.data

import com.aslnstbk.komek.common.data.models.HelpNeed
import com.aslnstbk.komek.common.domain.HelpNeedDataSource
import com.aslnstbk.komek.utils.mappers.HelpNeedMapper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase

class HelpNeedFirebaseDataSource(
    private val firebaseDatabase: FirebaseDatabase,
    private val firebaseAuth: FirebaseAuth,
    private val helpNeedMapper: HelpNeedMapper
) : HelpNeedDataSource {

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
                        checkSnapshot(
                            snapshot = it,
                            onSuccess = { helpNeed ->
                                helpNeedModeList.add(helpNeed)
                            },
                            onFail = onFail
                        )
                    }

                    onSuccess(helpNeedModeList)
                }
            })
    }

    private fun checkSnapshot(
        snapshot: DataSnapshot,
        onSuccess: (HelpNeed) -> Unit,
        onFail: () -> Unit
    ) {
        if (!isOwnHelpNeed(snapshot)) {
            val helpNeed: HelpNeed = helpNeedMapper.map(snapshot)

            if (!helpNeed.isHelp || !helpNeed.isDone) {
                if (!helpNeed.isHelp) {
                    onSuccess(helpNeed)
                }
            }
        }
    }
}