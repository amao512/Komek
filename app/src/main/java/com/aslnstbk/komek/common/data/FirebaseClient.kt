package com.aslnstbk.komek.common.data

import com.aslnstbk.komek.R
import com.aslnstbk.komek.main.presentation.APP_ACTIVITY
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.ktx.Firebase

const val EMPTY_STRING = ""

const val USER_LIST_REF = "user_list_ref_db"

const val HELP_NEED_REF_DB = "help_need_ref_db"
const val HELP_NEED_OWNER_ID = "ownerId"
const val HELP_NEED_PEOPLE_HELP = "peopleHelp"

class FirebaseClient {

    private val gso: GoogleSignInOptions get() = getGoogleSignInOptions()
    val googleSignInClient: GoogleSignInClient get() = GoogleSignIn.getClient(APP_ACTIVITY, gso)

    fun isOwnHelpNeed(snapshot: DataSnapshot): Boolean {
        return snapshot.child(HELP_NEED_OWNER_ID).value == Firebase.auth.currentUser?.uid
    }

    private fun getGoogleSignInOptions(): GoogleSignInOptions {
        return GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(APP_ACTIVITY.getString(R.string.oauth2_web_client_key))
            .requestEmail()
            .build()
    }
}