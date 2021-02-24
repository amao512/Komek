package com.aslnstbk.komek.help_need.data

import com.aslnstbk.komek.common.data.models.HelpNeed
import com.aslnstbk.komek.common.data.models.PersonHelp
import com.aslnstbk.komek.common.domain.PeopleHelpDataSource
import com.aslnstbk.komek.common.domain.HelpNeedDataSource
import com.aslnstbk.komek.help_need.domain.HelpNeedRepository

class DefaultHelpNeedRepository(
    private val peopleHelpDataSource: PeopleHelpDataSource,
    private val helpNeedDataSource: HelpNeedDataSource
) : HelpNeedRepository {

    override fun getHelpNeed(
        helpNeedId: String,
        onSuccess: (HelpNeed) -> Unit,
        onFail: () -> Unit
    ) {
        helpNeedDataSource.getHelpNeed(
            helpNeedId = helpNeedId,
            onSuccess = {
                onSuccess(it)
            },
            onFail = {
                onFail()
            }
        )
    }

    override fun onHelp(
        personHelp: PersonHelp,
        onSuccess: () -> Unit,
        onFail: () -> Unit
    ) {
        peopleHelpDataSource.createPersonHelp(
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