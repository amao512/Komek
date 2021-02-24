package com.aslnstbk.komek.person_help.domain

import com.aslnstbk.komek.common.data.models.PersonHelp

interface PersonHelpRepository {

    fun getPersonHelp(
        personHelp: PersonHelp,
        onSuccess: (PersonHelp) -> Unit,
        onFail: () -> Unit
    )

    fun approvePersonHelp(
        personHelp: PersonHelp,
        onSuccess: () -> Unit,
        onFail: () -> Unit,
    )
}