package com.aslnstbk.komek.auth.presentation.viewModel

import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModel
import com.aslnstbk.komek.auth.domain.AuthRepository
import com.aslnstbk.komek.navigation.Screens
import com.github.terrakok.cicerone.Router
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task

class AuthViewModel(
    private val router: Router,
    private val authRepository: AuthRepository
) : ViewModel() {

    fun signIn(data: Intent?) {
        val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
        handleSignInResult(task)
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount? = completedTask.getResult(ApiException::class.java)

            fireBaseAuthWithGoogle(account?.idToken!!)
        } catch (e: ApiException) {
            Log.w("AUTH", "signInResult:failed code=" + e.statusCode)
        }
    }

    private fun fireBaseAuthWithGoogle(idToken: String) {
        authRepository.signIn(
            idToken = idToken,
            onSuccess = {
                router.replaceScreen(screen = Screens.Profile())
            },
            onFail = {}
        )
    }
}