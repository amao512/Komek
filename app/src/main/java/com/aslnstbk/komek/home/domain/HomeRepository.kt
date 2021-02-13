package com.aslnstbk.komek.home.domain

import com.aslnstbk.komek.common.data.models.HelpNeed
import com.aslnstbk.komek.common.data.models.PersonHelp

interface HomeRepository {

    fun getHelpModelList(): List<PersonHelp>

    fun getHelpNeedPeople(
        onSuccess: (List<HelpNeed>) -> Unit,
        onFail: () -> Unit
    )

    fun onHelp() {

    }
}