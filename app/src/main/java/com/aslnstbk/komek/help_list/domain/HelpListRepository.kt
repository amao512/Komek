package com.aslnstbk.komek.help_list.domain

import com.aslnstbk.komek.common.data.models.HelpNeed
import com.aslnstbk.komek.common.data.models.PersonHelp

interface HelpListRepository {

    fun getPeopleHelp(
        onSuccess: (List<PersonHelp>) -> Unit,
        onFail: () -> Unit
    )

    fun getHelpNeedPeople(
        onSuccess: (List<HelpNeed>) -> Unit,
        onFail: () -> Unit
    )

    fun changePersonHelpValue(
        personHelp: PersonHelp,
        onSuccess: () -> Unit,
        onFail: () -> Unit,
    )
}