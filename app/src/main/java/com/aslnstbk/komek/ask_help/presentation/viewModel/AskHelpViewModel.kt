package com.aslnstbk.komek.ask_help.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aslnstbk.komek.common.data.models.HelpNeed
import com.aslnstbk.komek.ask_help.domain.AskHelpRepository
import com.aslnstbk.komek.common.data.ProgressState
import com.aslnstbk.komek.utils.generateRandomString
import com.github.terrakok.cicerone.Router
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class AskHelpViewModel(
    private val router: Router,
    private val askHelpRepository: AskHelpRepository,
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    private val emptyFieldsLiveData: MutableLiveData<Boolean> = MutableLiveData()
    private val progressLiveData: MutableLiveData<ProgressState> = MutableLiveData()

    fun getEmptyFieldsLiveData(): LiveData<Boolean> = emptyFieldsLiveData

    fun getProgressLiveData(): LiveData<ProgressState> = progressLiveData

    fun createAskHelp(
        title: String,
        desc: String,
        price: String
    ) {
        val isFieldsEmpty: Boolean = title.isBlank() || desc.isBlank()

        emptyFieldsLiveData.value = isFieldsEmpty

        if (!isFieldsEmpty) {
            progressLiveData.value = ProgressState.Loading

            val currentUser: FirebaseUser = firebaseAuth.currentUser!!
            val askHelpModel = HelpNeed(
                id = generateRandomString(),
                ownerId = currentUser.uid,
                ownerPhotoUrl = currentUser.photoUrl.toString(),
                ownerName = currentUser.displayName.toString(),
                title = title,
                desc = desc,
                price = price
            )

            askHelpRepository.onAskHelp(
                helpNeed = askHelpModel,
                onSuccess = {
                    progressLiveData.value = ProgressState.Done
                    router.exit()
                },
                onFail = {}
            )
        }
    }
}