package com.aslnstbk.komek.common.domain

import com.aslnstbk.komek.common.data.models.PersonHelp

interface PeopleHelpDataSource {

    fun createPersonHelp(
        personHelp: PersonHelp,
        onSuccess: () -> Unit,
        onFail: () -> Unit
    )

    fun getPersonHelp(
        personHelpId: String,
        helpNeedId: String,
        onSuccess: (PersonHelp) -> Unit,
        onFail: () -> Unit
    )

    fun getApprovePersonHelp(
        onSuccess: (PersonHelp) -> Unit,
        onFail: () -> Unit
    )

    fun getPeopleHelp(
        onSuccess: (List<PersonHelp>) -> Unit,
        onFail: () -> Unit
    )

    fun refusePersonHelp(
        personHelp: PersonHelp,
        onSuccess: () -> Unit,
        onFail: () -> Unit
    )

    fun approvePersonHelp(
        personHelp: PersonHelp,
        onSuccess: () -> Unit,
        onFail: () -> Unit
    )

    fun donePersonHelp(
        personHelp: PersonHelp,
        onSuccess: () -> Unit,
        onFail: () -> Unit
    )
}