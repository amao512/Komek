package com.aslnstbk.komek.ask_help.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aslnstbk.komek.ask_help.domain.AskHelpRepository
import com.aslnstbk.komek.common.data.model.ProgressState
import com.aslnstbk.komek.common.data.models.HelpNeed
import com.aslnstbk.komek.navigation.NavigationState
import com.aslnstbk.komek.utils.generateRandomString
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class AskHelpViewModel(
    private val askHelpRepository: AskHelpRepository,
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    private val _emptyFieldsLiveData: MutableLiveData<Boolean> = MutableLiveData()
    private val _progressLiveData: MutableLiveData<ProgressState> = MutableLiveData()
    private val _navigationLiveData: MutableLiveData<NavigationState> = MutableLiveData()

    val emptyFieldsLiveData: LiveData<Boolean> = _emptyFieldsLiveData
    val progressLiveData: LiveData<ProgressState> = _progressLiveData
    val navigationLiveData: LiveData<NavigationState> = _navigationLiveData

    fun createAskHelp(
        title: String,
        desc: String,
        price: String
    ) {
        val isFieldsEmpty: Boolean = title.isBlank() || desc.isBlank()

        _emptyFieldsLiveData.value = isFieldsEmpty

        if (!isFieldsEmpty) {
            _progressLiveData.value = ProgressState.Loading

            val helpNeed: HelpNeed = createHelpNeed(title, desc, price)

            askHelpRepository.onAskHelp(
                helpNeed = helpNeed,
                onSuccess = {
                    _progressLiveData.value = ProgressState.Done
                    _navigationLiveData.value = NavigationState.Back
                },
                onFail = {}
            )
        }
    }

    fun setNavigation(navigationState: NavigationState) {
        _navigationLiveData.value = navigationState
    }

    private fun createHelpNeed(
        title: String,
        desc: String,
        price: String
    ): HelpNeed {
        val currentUser: FirebaseUser = firebaseAuth.currentUser!!

        return HelpNeed(
            id = generateRandomString(),
            ownerId = currentUser.uid,
            ownerPhotoUrl = currentUser.photoUrl.toString(),
            ownerName = currentUser.displayName.toString(),
            title = title,
            desc = desc,
            price = price
        )
    }
}