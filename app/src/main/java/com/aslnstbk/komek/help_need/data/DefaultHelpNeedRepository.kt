package com.aslnstbk.komek.help_need.data

import com.aslnstbk.komek.common.data.models.HelpNeed
import com.aslnstbk.komek.common.domain.HelpDataSource
import com.aslnstbk.komek.help_need.domain.HelpNeedRepository

class DefaultHelpNeedRepository(
    private val helpDataSource: HelpDataSource
) : HelpNeedRepository {

    override fun getHelpNeed(
        helpNeedId: String,
        onSuccess: (HelpNeed) -> Unit,
        onFail: () -> Unit
    ) {
        helpDataSource.getHelpNeed(
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
        helpNeedId: String,
        helpName: String,
        transmissionLetter: String,
        onSuccess: () -> Unit,
        onFail: () -> Unit
    ) {
        helpDataSource.createPersonHelp(
            helpNeedId = helpNeedId,
            helpName = helpName,
            transmissionLetter = transmissionLetter,
            onSuccess = {
                onSuccess()
            },
            onFail = {
                onFail()
            }
        )
    }
}