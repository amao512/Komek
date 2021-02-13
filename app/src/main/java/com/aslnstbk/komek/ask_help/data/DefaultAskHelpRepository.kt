package com.aslnstbk.komek.ask_help.data

import com.aslnstbk.komek.ask_help.domain.AskHelpRepository
import com.aslnstbk.komek.common.data.HELP_NEED_REF_DB
import com.aslnstbk.komek.common.data.models.HelpNeed
import com.google.firebase.database.FirebaseDatabase

class DefaultAskHelpRepository(
    private val firebaseDatabase: FirebaseDatabase,
) : AskHelpRepository {

    override fun onAskHelp(
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
}