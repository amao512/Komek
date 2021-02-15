package com.aslnstbk.komek.help_list.data

import com.aslnstbk.komek.common.data.models.HelpNeed
import com.aslnstbk.komek.common.data.models.PersonHelp
import com.aslnstbk.komek.common.domain.HelpDataSource
import com.aslnstbk.komek.help_list.domain.HelpListRepository

class DefaultHelpListRepository(
    private val helpDataSource: HelpDataSource
) : HelpListRepository {

    override fun getPeopleHelp(
        onSuccess: (List<PersonHelp>) -> Unit,
        onFail: () -> Unit
    ) {
        helpDataSource.getPeopleHelp(
            onSuccess = {
                onSuccess(it)
            },
            onFail = {
                onFail()
            }
        )
    }

    override fun getHelpNeedPeople(
        onSuccess: (List<HelpNeed>) -> Unit,
        onFail: () -> Unit
    ) {
        helpDataSource.getHelpNeedPeople(
            onSuccess = {
                onSuccess(it)
            },
            onFail = {
                onFail()
            }
        )
    }

    override fun changePersonHelpValue(
        personHelp: PersonHelp,
        onSuccess: () -> Unit,
        onFail: () -> Unit
    ) {
        helpDataSource.updatePersonHelp(
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