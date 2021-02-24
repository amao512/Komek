package com.aslnstbk.komek.help_list.data

import com.aslnstbk.komek.common.data.models.HelpNeed
import com.aslnstbk.komek.common.data.models.PersonHelp
import com.aslnstbk.komek.common.domain.PeopleHelpDataSource
import com.aslnstbk.komek.common.domain.HelpNeedDataSource
import com.aslnstbk.komek.help_list.domain.HelpListRepository

class DefaultHelpListRepository(
    private val peopleHelpDataSource: PeopleHelpDataSource,
    private val helpNeedDataSource: HelpNeedDataSource
) : HelpListRepository {

    override fun getApprovedPersonHelp(
        onSuccess: (PersonHelp) -> Unit,
        onFail: () -> Unit
    ) {
        peopleHelpDataSource.getApprovePersonHelp(
            onSuccess = onSuccess,
            onFail = onFail
        )
    }

    override fun getPeopleHelp(
        onSuccess: (List<PersonHelp>) -> Unit,
        onFail: () -> Unit
    ) {
        peopleHelpDataSource.getPeopleHelp(
            onSuccess = onSuccess,
            onFail = onFail
        )
    }

    override fun getHelpNeedPeople(
        onSuccess: (List<HelpNeed>) -> Unit,
        onFail: () -> Unit
    ) {
        helpNeedDataSource.getHelpNeedPeople(
            onSuccess = onSuccess,
            onFail = onFail
        )
    }

    override fun refusePersonHelp(
        personHelp: PersonHelp,
        onSuccess: () -> Unit,
        onFail: () -> Unit
    ) {
        peopleHelpDataSource.refusePersonHelp(
            personHelp = personHelp,
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
            onSuccess = onSuccess,
            onFail = onFail
        )
    }

    override fun donePersonHelp(
        personHelp: PersonHelp,
        onSuccess: () -> Unit,
        onFail: () -> Unit
    ) {
        peopleHelpDataSource.donePersonHelp(
            personHelp = personHelp,
            onSuccess = onSuccess,
            onFail = onFail
        )
    }
}