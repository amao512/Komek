package com.aslnstbk.komek.help_list.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aslnstbk.komek.common.data.models.HelpNeed
import com.aslnstbk.komek.common.data.models.PersonHelp
import com.aslnstbk.komek.help_list.domain.HelpListRepository
import com.aslnstbk.komek.navigation.NavigationState

class HelpListViewModel(
    private val helpListRepository: HelpListRepository
) : ViewModel() {

    private val _helpNeedPeopleLiveData: MutableLiveData<List<HelpNeed>> = MutableLiveData()
    private val _peopleHelpLiveData: MutableLiveData<List<PersonHelp>> = MutableLiveData()
    private val _navigationLiveData: MutableLiveData<NavigationState> = MutableLiveData()

    val helpNeedPeopleLiveData: LiveData<List<HelpNeed>> = _helpNeedPeopleLiveData
    val peopleHelpLiveData: LiveData<List<PersonHelp>> = _peopleHelpLiveData
    val navigationLiveData: LiveData<NavigationState> = _navigationLiveData

    fun onStart() {
        getHelpNeedPeople()
        getHelpMePeople()
    }

    fun setNavigation(navigationState: NavigationState) {
        _navigationLiveData.value = navigationState
    }

    fun onRefuseHelp(personHelp: PersonHelp) {
        personHelp.isRefuse = true

        helpListRepository.changePersonHelpValue(
            personHelp = personHelp,
            onSuccess = {},
            onFail = {}
        )
    }

    fun onApproveHelp(personHelp: PersonHelp) {
        personHelp.isHelp = true

        helpListRepository.changePersonHelpValue(
            personHelp = personHelp,
            onSuccess = {},
            onFail = {}
        )
    }

    private fun getHelpNeedPeople() {
        helpListRepository.getHelpNeedPeople(
            onSuccess = {
                _helpNeedPeopleLiveData.value = it
            },
            onFail = {

            }
        )
    }

    private fun getHelpMePeople() {
        helpListRepository.getPeopleHelp(
            onSuccess = {
                if (it.isNotEmpty()){
                    _peopleHelpLiveData.value = it
                }
            },
            onFail = {}
        )
    }
}