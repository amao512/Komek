package com.aslnstbk.komek.common.data.models

data class HelpNeed(
    val id: String = "",
    val ownerId: String = "",
    val ownerPhotoUrl: String = "",
    val ownerName: String = "",
    val title: String = "",
    val desc: String = "",
    val price: String = "",
    val isHelp: Boolean = false,
    val isDone: Boolean = false,
    val personHelpId: String = "",
    val peopleHelp: List<PersonHelp> = emptyList()
)