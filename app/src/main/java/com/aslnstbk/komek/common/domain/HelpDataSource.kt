package com.aslnstbk.komek.common.domain

import com.aslnstbk.komek.common.data.models.HelpNeed
import com.aslnstbk.komek.common.data.models.PersonHelp
import com.google.firebase.database.DataSnapshot

interface HelpDataSource {

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

    fun createPersonHelp(
        helpNeedId: String,
        helpName: String,
        transmissionLetter: String,
        onSuccess: () -> Unit,
        onFail: () -> Unit
    )

    fun getPeopleHelp(
        onSuccess: (List<PersonHelp>) -> Unit,
        onFail: () -> Unit
    )

    fun updatePersonHelp(
        personHelp: PersonHelp,
        onSuccess: () -> Unit,
        onFail: () -> Unit
    )
}