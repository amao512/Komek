package com.aslnstbk.komek.auth.data

import com.aslnstbk.komek.auth.domain.AuthRepository
import com.aslnstbk.komek.common.data.EMPTY_STRING
import com.aslnstbk.komek.common.data.USER_LIST_REF
import com.aslnstbk.komek.common.data.models.User
import com.aslnstbk.komek.main.presentation.APP_ACTIVITY
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.*

class DefaultAuthRepository(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseDatabase: FirebaseDatabase
) : AuthRepository {

    override fun signIn(
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
            .addListenerForSingleValueEvent(object : ValueEventListener {
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

            override fun onCancelled(error: DatabaseError) {}
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