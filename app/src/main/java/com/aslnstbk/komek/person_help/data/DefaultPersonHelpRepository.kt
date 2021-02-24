package com.aslnstbk.komek.person_help.data

import com.aslnstbk.komek.common.data.models.PersonHelp
import com.aslnstbk.komek.common.domain.PeopleHelpDataSource
import com.aslnstbk.komek.person_help.domain.PersonHelpRepository

class DefaultPersonHelpRepository(
    private val peopleHelpDataSource: PeopleHelpDataSource
) : PersonHelpRepository {

    override fun getPersonHelp(
        personHelp: PersonHelp,
        onSuccess: (PersonHelp) -> Unit,
        onFail: () -> Unit
    ) {
        peopleHelpDataSource.getPersonHelp(
            personHelpId = personHelp.id,
            helpNeedId = personHelp.helpNeedId,
            onSuccess = onSuccess,
            onFail = onFail
        )
    }

    override fun approvePersonHelp(
        personHelp: PersonHelp,
        onSuccess: () -> Unit,
        onFail: () -> Unit
    ) {
        peopleHelpDataSource.approvePersonHelp(
            personHelp = personHelp,
            onSuccess = {
                onSuccess()
            },
            onFail = {
                onFail()
            }
        )
    }
}