package com.aslnstbk.komek.utils.mappers

import com.aslnstbk.komek.common.data.models.HelpNeed
import com.aslnstbk.komek.common.data.models.PersonHelp
import com.google.firebase.database.DataSnapshot

class HelpNeedMapper {

    fun map(snapshot: DataSnapshot): HelpNeed {
        val peopleHelp: MutableList<PersonHelp> = mutableListOf()

        snapshot.child("peopleHelp").children.forEach {
            peopleHelp.add(it.getValue(PersonHelp::class.java)!!)
        }

        return HelpNeed(
            id = snapshot.child("id").value as String,
            ownerId = snapshot.child("ownerId").value as String,
            ownerPhotoUrl = snapshot.child("ownerPhotoUrl").value as String,
            ownerName = snapshot.child("ownerName").value as String,
            title = snapshot.child("title").value as String,
            desc = snapshot.child("desc").value as String,
            isHelp = snapshot.child("help").value as Boolean,
            isDone = snapshot.child("done").value as Boolean,
            personHelpId = snapshot.child("personHelpId").value as String,
            peopleHelp = peopleHelp
        )
    }
}