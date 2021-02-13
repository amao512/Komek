package com.aslnstbk.komek.home.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aslnstbk.komek.common.data.models.HelpNeed
import com.aslnstbk.komek.common.data.models.PersonHelp
import com.aslnstbk.komek.home.domain.HomeRepository

class HomeViewModel(
    private val homeRepository: HomeRepository
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
        homeRepository.getHelpNeedPeople(
            onSuccess = {
                helpNeedPeopleLiveData.value = it
            },
            onFail = {

            }
        )
    }

    private fun getHelpMePeople() {}
}