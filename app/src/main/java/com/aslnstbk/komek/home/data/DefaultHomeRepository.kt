package com.aslnstbk.komek.home.data

import com.aslnstbk.komek.common.data.FirebaseClient
import com.aslnstbk.komek.home.domain.HomeRepository
import com.google.firebase.database.FirebaseDatabase

class DefaultHomeRepository(
    private val firebaseClient: FirebaseClient,
    private val firebaseDatabase: FirebaseDatabase
) : HomeRepository {

}