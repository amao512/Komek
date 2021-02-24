package com.aslnstbk.komek.common.domain

import com.aslnstbk.komek.common.data.models.HelpNeed
import com.google.firebase.database.DataSnapshot

interface HelpNeedDataSource {

    fun createHelpNeed(
        helpNeed: HelpNeed,
        onSuccess: () -> Unit,
        onFail: () -> Unit
    )

    fun isOwnHelpNeed(snapshot: DataSnapshot): Boolean

    fun getHelpNeed(
        helpNeedId: String,
        onSuccess: (HelpNeed) -> Unit,
        onFail: () -> Unit
    )

    fun getHelpNeedPeople(
        onSuccess: (List<HelpNeed>) -> Unit,
        onFail: () -> Unit
    )
}