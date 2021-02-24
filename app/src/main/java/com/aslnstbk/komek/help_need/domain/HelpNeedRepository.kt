package com.aslnstbk.komek.help_need.domain

import com.aslnstbk.komek.common.data.models.HelpNeed
import com.aslnstbk.komek.common.data.models.PersonHelp

interface HelpNeedRepository {

    fun getHelpNeed(
        helpNeedId: String,
        onSuccess: (HelpNeed) -> Unit,
        onFail: () -> Unit
    )

    fun onHelp(
        personHelp: PersonHelp,
        onSuccess: () -> Unit,
        onFail: () -> Unit
    )
}