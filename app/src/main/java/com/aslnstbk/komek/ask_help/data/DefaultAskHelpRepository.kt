package com.aslnstbk.komek.ask_help.data

import com.aslnstbk.komek.ask_help.domain.AskHelpRepository
import com.aslnstbk.komek.common.data.models.HelpNeed
import com.aslnstbk.komek.common.domain.HelpNeedDataSource

class DefaultAskHelpRepository(
    private val helpNeedDataSource: HelpNeedDataSource,
) : AskHelpRepository {

    override fun onAskHelp(
        helpNeed: HelpNeed,
        onSuccess: () -> Unit,
        onFail: () -> Unit
    ) {
        helpNeedDataSource.createHelpNeed(
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