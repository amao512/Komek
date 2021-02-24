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
    private val _approvedPersonHelpLiveData: MutableLiveData<PersonHelp> = MutableLiveData()
    private val _navigationLiveData: MutableLiveData<NavigationState> = MutableLiveData()

    val helpNeedPeopleLiveData: LiveData<List<HelpNeed>> = _helpNeedPeopleLiveData
    val peopleHelpLiveData: LiveData<List<PersonHelp>> = _peopleHelpLiveData
    val approvedPersonHelpLiveData: LiveData<PersonHelp> = _approvedPersonHelpLiveData
    val navigationLiveData: LiveData<NavigationState> = _navigationLiveData

    fun onStart() {
        getHelpMePeople()
        getHelpNeedPeople()
        getApprovedPersonHelp()
    }

    fun setNavigation(navigationState: NavigationState) {
        _navigationLiveData.value = navigationState
    }

    fun onRefuseHelp(personHelp: PersonHelp) {
        personHelp.isRefuse = true

        helpListRepository.refusePersonHelp(
            personHelp = personHelp,
            onSuccess = {},
            onFail = {}
        )
    }

    fun onDoneHelp(personHelp: PersonHelp) {
        personHelp.isDone = true

        helpListRepository.donePersonHelp(
            personHelp = personHelp,
            onSuccess = {},
            onFail = {}
        )
    }

    private fun getHelpNeedPeople() {
        helpListRepository.getHelpNeedPeople(
            onSuccess = {
                if (it.isNotEmpty()) {
                    _helpNeedPeopleLiveData.value = it
                }
            },
            onFail = {}
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

    private fun getApprovedPersonHelp() {
        helpListRepository.getApprovedPersonHelp(
            onSuccess = {
                _approvedPersonHelpLiveData.value = it
            },
            onFail = {}
        )
    }
}