package com.aslnstbk.komek.auth.presentation.viewModel

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aslnstbk.komek.auth.domain.AuthRepository
import com.aslnstbk.komek.navigation.NavigationState

class AuthViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _navigationLiveData: MutableLiveData<NavigationState> = MutableLiveData()

    val navigationLiveData: LiveData<NavigationState> = _navigationLiveData

    fun signIn(data: Intent?) {
        authRepository.signIn(
            data = data,
            onSuccess = {
                _navigationLiveData.value = NavigationState.BaseFlow
            },
            onFail = {}
        )
    }
}