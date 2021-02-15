package com.aslnstbk.komek.common.data.model

import com.aslnstbk.komek.R
import com.aslnstbk.komek.main.presentation.APP_ACTIVITY
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class FirebaseClient {

    private val gso: GoogleSignInOptions get() = getGoogleSignInOptions()
    val googleSignInClient: GoogleSignInClient get() = GoogleSignIn.getClient(APP_ACTIVITY, gso)

    private fun getGoogleSignInOptions(): GoogleSignInOptions {
        return GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(APP_ACTIVITY.getString(R.string.oauth2_web_client_key))
            .requestEmail()
            .build()
    }
}