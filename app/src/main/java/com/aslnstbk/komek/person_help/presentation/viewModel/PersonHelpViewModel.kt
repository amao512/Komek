package com.aslnstbk.komek.person_help.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aslnstbk.komek.common.data.models.PersonHelp
import com.aslnstbk.komek.navigation.NavigationState
import com.aslnstbk.komek.person_help.domain.PersonHelpRepository

class PersonHelpViewModel(
    private val personHelpRepository: PersonHelpRepository
) : ViewModel() {

    private val _personHelpLiveData: MutableLiveData<PersonHelp> = MutableLiveData()
    private val _navigationLiveData: MutableLiveData<NavigationState> = MutableLiveData()

    val personHelpLiveData: LiveData<PersonHelp> = _personHelpLiveData
    val navigationLiveData: LiveData<NavigationState> = _navigationLiveData

    fun onStart(personHelp: PersonHelp) {
        loadPersonHelp(personHelp)
    }

    fun onApprove(personHelp: PersonHelp) {
        personHelp.isHelp = true

        personHelpRepository.approvePersonHelp(
            personHelp = personHelp,
            onSuccess = {
                _navigationLiveData.value = NavigationState.Back
            },
            onFail = {}
        )
    }

    private fun loadPersonHelp(personHelp: PersonHelp) {
        personHelpRepository.getPersonHelp(
            personHelp = personHelp,
            onSuccess = {
                _personHelpLiveData.value = it
            },
            onFail = {}
        )
    }
}