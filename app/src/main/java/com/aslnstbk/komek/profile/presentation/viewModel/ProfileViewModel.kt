package com.aslnstbk.komek.profile.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aslnstbk.komek.navigation.NavigationState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class ProfileViewModel(
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    private val _profileLiveData: MutableLiveData<FirebaseUser> = MutableLiveData()
    private val _navigationLiveData: MutableLiveData<NavigationState> = MutableLiveData()

    val profileLiveData: LiveData<FirebaseUser> = _profileLiveData
    val navigationLiveData: LiveData<NavigationState> = _navigationLiveData

    fun onStart() {
        getCurrentUser()
    }

    fun signOut() {
        firebaseAuth.signOut()
        _navigationLiveData.value = NavigationState.Auth
    }

    private fun getCurrentUser() {
        _profileLiveData.value = firebaseAuth.currentUser
    }
}