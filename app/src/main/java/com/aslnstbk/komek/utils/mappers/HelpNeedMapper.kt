package com.aslnstbk.komek.utils.mappers

import com.aslnstbk.komek.common.data.models.HelpNeed
import com.aslnstbk.komek.common.data.models.PersonHelp
import com.google.firebase.database.DataSnapshot

class HelpNeedMapper {

    fun map(snapshot: DataSnapshot): HelpNeed {
        val peopleHelp: MutableList<PersonHelp> = mutableListOf()

        snapshot.child("peopleHelp").children.forEach {
            peopleHelp.add(
                PersonHelp(
                    id = it.child("id").value as String,
                    helpNeedId = it.child("helpNeedId").value as String,
                    userName = it.child("userName").value as String,
                    userPhoto = it.child("userPhoto").value as String,
                    helpName = it.child("helpName").value as String,
                    transmissionLetter = it.child("transmissionLetter").value as String,
                    isHelp = it.child("help").value as Boolean,
                    isRefuse = it.child("refuse").value as Boolean,
                    isDone = it.child("done").value as Boolean
                )
            )
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
            peopleHelp = checkPeopleHelp(peopleHelp)
        )
    }

    private fun checkPeopleHelp(peopleHelp: List<PersonHelp>): List<PersonHelp> {
        val checkedPeopleHelp: MutableList<PersonHelp> = mutableListOf()

        peopleHelp.map {
            if (!it.isDone) {
                checkedPeopleHelp.add(it)
            }
        }

        return checkedPeopleHelp
    }
}