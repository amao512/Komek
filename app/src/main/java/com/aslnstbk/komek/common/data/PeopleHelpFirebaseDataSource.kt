package com.aslnstbk.komek.common.data

import com.aslnstbk.komek.common.data.models.HelpNeed
import com.aslnstbk.komek.common.data.models.PersonHelp
import com.aslnstbk.komek.common.domain.PeopleHelpDataSource
import com.aslnstbk.komek.common.domain.HelpNeedDataSource
import com.aslnstbk.komek.utils.mappers.HelpNeedMapper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase

const val EMPTY_STRING = ""
const val HELP_NEED_REF_DB = "help_need_ref_db"
const val HELP_NEED_OWNER_ID = "ownerId"
const val HELP_NEED_PEOPLE_HELP = "peopleHelp"
const val HELP_NEED_IS_HELP = "help"
const val HELP_NEED_DONE = "done"

class PeopleHelpFirebaseDataSource(
    private val helpNeedDataSource: HelpNeedDataSource,
    private val firebaseDatabase: FirebaseDatabase,
    private val firebaseAuth: FirebaseAuth,
    private val helpNeedMapper: HelpNeedMapper
) : PeopleHelpDataSource {

    override fun createPersonHelp(
        personHelp: PersonHelp,
        onSuccess: () -> Unit,
        onFail: () -> Unit
    ) {
        firebaseDatabase.getReference(HELP_NEED_REF_DB)
            .child(personHelp.helpNeedId)
            .child(HELP_NEED_PEOPLE_HELP)
            .child(firebaseAuth.currentUser!!.uid)
            .setValue(personHelp)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener {
                onFail()
            }
    }

    override fun getPersonHelp(
        personHelpId: String,
        helpNeedId: String,
        onSuccess: (PersonHelp) -> Unit,
        onFail: () -> Unit
    ) {
        firebaseDatabase.getReference(HELP_NEED_REF_DB)
            .child(helpNeedId)
            .child(HELP_NEED_PEOPLE_HELP)
            .child(personHelpId)
            .addValueEventListener(object : AbstractValueEventListener() {
                override fun onDataChange(snapshot: DataSnapshot) {
                    super.onDataChange(snapshot)

                    onSuccess(snapshot.getValue(PersonHelp::class.java)!!)
                }
            })
    }

    override fun getApprovePersonHelp(
        onSuccess: (PersonHelp) -> Unit,
        onFail: () -> Unit
    ) {
        firebaseDatabase.getReference(HELP_NEED_REF_DB)
            .addValueEventListener(object : AbstractValueEventListener() {
                override fun onDataChange(snapshot: DataSnapshot) {
                    super.onDataChange(snapshot)

                    snapshot.children.forEach {
                        checkSnapshot(
                            snapshot = it,
                            onSuccess = { personHelp ->
                                onSuccess(personHelp)
                            },
                            onFail = onFail
                        )
                    }
                }
            })
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
                        checkSnapshot(
                            snapshot = it,
                            onSuccess = { personHelp ->
                                peopleHelp.add(personHelp)
                            },
                            onFail = onFail
                        )
                    }

                    onSuccess(peopleHelp)
                }
            })
    }

    override fun refusePersonHelp(
        personHelp: PersonHelp,
        onSuccess: () -> Unit,
        onFail: () -> Unit
    ) {
        updatePersonHelp(
            personHelp = personHelp,
            onSuccess = onSuccess,
            onFail = onFail
        )
    }

    override fun approvePersonHelp(
        personHelp: PersonHelp,
        onSuccess: () -> Unit,
        onFail: () -> Unit
    ) {
        firebaseDatabase.getReference(HELP_NEED_REF_DB)
            .child(personHelp.helpNeedId)
            .child(HELP_NEED_IS_HELP)
            .setValue(true)
            .addOnSuccessListener {
                updatePersonHelp(
                    personHelp = personHelp,
                    onSuccess = onSuccess,
                    onFail = onFail
                )
            }
            .addOnFailureListener {
                onFail()
            }
    }

    override fun donePersonHelp(
        personHelp: PersonHelp,
        onSuccess: () -> Unit,
        onFail: () -> Unit
    ) {
        firebaseDatabase.getReference(HELP_NEED_REF_DB)
            .child(personHelp.helpNeedId)
            .child(HELP_NEED_DONE)
            .setValue(true)
            .addOnSuccessListener {
                updatePersonHelp(
                    personHelp = personHelp,
                    onSuccess = onSuccess,
                    onFail = onFail
                )
            }
            .addOnFailureListener {
                onFail()
            }
    }

    private fun updatePersonHelp(
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

    private fun checkSnapshot(
        snapshot: DataSnapshot,
        onSuccess: (PersonHelp) -> Unit,
        onFail: () -> Unit
    ) {
        if (helpNeedDataSource.isOwnHelpNeed(snapshot)) {
            val helpNeed: HelpNeed = helpNeedMapper.map(snapshot)

            if (!helpNeed.isHelp || !helpNeed.isDone) {
                helpNeed.peopleHelp.forEach { personHelp ->
                    if (!personHelp.isRefuse) {
                        onSuccess(personHelp)
                    } else {
                        onFail()
                    }
                }
            } else {
                onFail()
            }
        }
    }
}