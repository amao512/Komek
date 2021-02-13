package com.aslnstbk.komek.profile.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aslnstbk.komek.navigation.Screens
import com.github.terrakok.cicerone.Router
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class ProfileViewModel(
    private val router: Router,
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    private val profileLiveData: MutableLiveData<FirebaseUser> = MutableLiveData()

    fun getProfileLiveData(): LiveData<FirebaseUser> = profileLiveData

    fun onStart() {
        getCurrentUser()
    }

    fun signOut() {
        firebaseAuth.signOut()
        router.replaceScreen(Screens.Auth())
    }

    private fun getCurrentUser() {
        profileLiveData.value = firebaseAuth.currentUser
    }
}