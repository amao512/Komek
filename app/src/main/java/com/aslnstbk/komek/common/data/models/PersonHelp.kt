package com.aslnstbk.komek.common.data.models

data class PersonHelp(
    val id: String = "",
    val userName: String = "",
    val userPhoto: String = "",
    val helpName: String = "",
    val transmissionLetter: String = "",
    val isHelp: Boolean = false,
    val isRefuse: Boolean = false,
    val isDone: Boolean = false
)