package com.aslnstbk.komek.ask_help.domain

import com.aslnstbk.komek.common.data.models.HelpNeed

interface AskHelpRepository {

    fun onAskHelp(
        helpNeed: HelpNeed,
        onSuccess: () -> Unit,
        onFail: () -> Unit
    )
}