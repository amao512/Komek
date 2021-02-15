package com.aslnstbk.komek.common.data

import android.content.Intent
import android.util.Log
import com.aslnstbk.komek.common.data.models.User
import com.aslnstbk.komek.common.domain.UserDataSource
import com.aslnstbk.komek.main.presentation.APP_ACTIVITY
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase

const val USER_LIST_REF = "user_list_ref_db"

class UserFirebaseDataSource(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseDatabase: FirebaseDatabase
) : UserDataSource {

    override fun signIn(
        data: Intent?,
        onSuccess: () -> Unit,
        onFail: () -> Unit
    ) {
        val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)

        try {
            val account: GoogleSignInAccount? = task.getResult(ApiException::class.java)

            fireBaseAuthWithGoogle(
                idToken = account?.idToken!!,
                onSuccess = onSuccess,
                onFail = onFail
            )
        } catch (e: ApiException) {
            Log.w("AUTH", "signInResult:failed code=" + e.statusCode)
        }
    }

    private fun fireBaseAuthWithGoogle(
        idToken: String,
        onSuccess: () -> Unit,
        onFail: () -> Unit
    ) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)

        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(APP_ACTIVITY) {
                if (it.isSuccessful) {
                    checkProfile()
                    onSuccess()
                }
            }
            .addOnFailureListener {
                onFail()
            }
    }

    private fun checkProfile() {
        firebaseDatabase.getReference(USER_LIST_REF)
            .addListenerForSingleValueEvent(object : AbstractValueEventListener() {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val currentUserId = firebaseAuth.currentUser?.uid

                    if (snapshot.hasChildren()) {
                        for (data in snapshot.children) {
                            if (data.key != currentUserId) {
                                addUser()
                            }
                        }
                    } else {
                        addUser()
                    }
                }
            })
    }

    private fun addUser() {
        val currentUser: FirebaseUser = firebaseAuth.currentUser!!
        val user: User? = User(
            id = currentUser.uid,
            email = currentUser.email ?: EMPTY_STRING,
            name = currentUser.displayName ?: EMPTY_STRING,
            photoUrl = currentUser.photoUrl.toString(),
        )

        firebaseDatabase.getReference(USER_LIST_REF)
            .child(currentUser.uid)
            .setValue(user)
    }
}