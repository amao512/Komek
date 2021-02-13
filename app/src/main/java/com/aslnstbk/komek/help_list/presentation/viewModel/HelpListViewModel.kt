package com.aslnstbk.komek.help_list.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aslnstbk.komek.common.data.models.HelpNeed
import com.aslnstbk.komek.common.data.models.PersonHelp
import com.aslnstbk.komek.help_list.domain.HelpListRepository

class HelpListViewModel(
    private val helpListRepository: HelpListRepository
) : ViewModel() {

    private val helpNeedPeopleLiveData: MutableLiveData<List<HelpNeed>> = MutableLiveData()
    private val peopleHelpLiveData: MutableLiveData<List<PersonHelp>> = MutableLiveData()

    fun getHelpNeedPeopleLiveData(): LiveData<List<HelpNeed>> = helpNeedPeopleLiveData

    fun getPeopleHelpMeLiveData(): LiveData<List<PersonHelp>> = peopleHelpLiveData

    fun onStart() {
        getHelpNeedPeople()
        getHelpMePeople()
    }

    private fun getHelpNeedPeople() {
        helpListRepository.getHelpNeedPeople(
            onSuccess = {
                helpNeedPeopleLiveData.value = it
            },
            onFail = {

            }
        )
    }

    private fun getHelpMePeople() {}
}