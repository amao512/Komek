package com.aslnstbk.komek.ask_help.data

import com.aslnstbk.komek.ask_help.domain.AskHelpRepository
import com.aslnstbk.komek.common.data.models.HelpNeed
import com.aslnstbk.komek.common.domain.HelpDataSource

class DefaultAskHelpRepository(
    private val helpDataSource: HelpDataSource
) : AskHelpRepository {

    override fun onAskHelp(
        helpNeed: HelpNeed,
        onSuccess: () -> Unit,
        onFail: () -> Unit
    ) {
        helpDataSource.createHelpNeed(
            helpNeed = helpNeed,
            onSuccess = {
                onSuccess()
            },
            onFail = {
                onFail()
            }
        )
    }
}