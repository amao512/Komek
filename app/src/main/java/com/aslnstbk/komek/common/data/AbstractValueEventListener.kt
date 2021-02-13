package com.aslnstbk.komek.common.data

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

open class AbstractValueEventListener : ValueEventListener {

    override fun onDataChange(snapshot: DataSnapshot) = Unit

    override fun onCancelled(error: DatabaseError) = Unit
}