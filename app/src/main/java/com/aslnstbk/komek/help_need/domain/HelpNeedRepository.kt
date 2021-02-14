package com.aslnstbk.komek.help_need.domain

import com.aslnstbk.komek.common.data.models.HelpNeed

interface HelpNeedRepository {

    fun getHelpNeed(
        helpNeedId: String,
        onSuccess: (HelpNeed) -> Unit,
        onFail: () -> Unit
    )

    fun onHelp(
        helpNeedId: String,
        helpName: String,
        transmissionLetter: String,
        onSuccess: () -> Unit,
        onFail: () -> Unit
    )
}