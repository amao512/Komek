package com.aslnstbk.komek.main.presentation.viewModel

import androidx.lifecycle.ViewModel
import com.aslnstbk.komek.navigation.Screens
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.google.firebase.auth.FirebaseAuth

class MainViewModel(
    private val router: Router,
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    fun setStartFragment() {
        val screen: FragmentScreen = when (firebaseAuth.currentUser != null) {
            true -> Screens.Home()
            false -> Screens.Auth()
        }

        router.replaceScreen(screen)
    }

    fun selectMenuItem(screen: FragmentScreen) {
        router.navigateTo(screen)
    }
}