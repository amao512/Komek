package com.aslnstbk.komek.help_list.data

import com.aslnstbk.komek.common.data.AbstractValueEventListener
import com.aslnstbk.komek.common.data.FirebaseClient
import com.aslnstbk.komek.common.data.HELP_NEED_REF_DB
import com.aslnstbk.komek.common.data.models.HelpNeed
import com.aslnstbk.komek.common.data.models.PersonHelp
import com.aslnstbk.komek.help_list.domain.HelpListRepository
import com.aslnstbk.komek.utils.mappers.HelpNeedMapper
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase

class DefaultHelpListRepository(
    private val firebaseClient: FirebaseClient,
    private val firebaseDatabase: FirebaseDatabase,
    private val helpNeedMapper: HelpNeedMapper
) : HelpListRepository {

    override fun getHelpModelList(): List<PersonHelp> {
        return emptyList()
    }

    override fun getHelpNeedPeople(
        onSuccess: (List<HelpNeed>) -> Unit,
        onFail: () -> Unit
    ) {
        firebaseDatabase.getReference(HELP_NEED_REF_DB)
            .addValueEventListener(object : AbstractValueEventListener() {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val helpNeedModeList: MutableList<HelpNeed> = mutableListOf()
                    snapshot.children.forEach {
                        if (!firebaseClient.isOwnHelpNeed(it)) {
                            helpNeedModeList.add(helpNeedMapper.map(it))
                        }
                    }

                    onSuccess(helpNeedModeList)
                }
            })
    }
}