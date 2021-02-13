package com.aslnstbk.komek.help_list.domain

import com.aslnstbk.komek.common.data.models.HelpNeed
import com.aslnstbk.komek.common.data.models.PersonHelp

interface HelpListRepository {

    fun getHelpModelList(): List<PersonHelp>

    fun getHelpNeedPeople(
        onSuccess: (List<HelpNeed>) -> Unit,
        onFail: () -> Unit
    )
}