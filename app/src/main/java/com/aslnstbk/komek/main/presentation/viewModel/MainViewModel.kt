package com.aslnstbk.komek.main.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aslnstbk.komek.navigation.NavigationState
import com.google.firebase.auth.FirebaseAuth

class MainViewModel(
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    private val _navigationLiveData: MutableLiveData<NavigationState> = MutableLiveData()

    val navigationLiveData: LiveData<NavigationState> = _navigationLiveData

    fun setStartFragment() {
        when (firebaseAuth.currentUser != null) {
            true -> _navigationLiveData.value = NavigationState.BaseFlow
            false -> _navigationLiveData.value = NavigationState.Auth
        }
    }
}