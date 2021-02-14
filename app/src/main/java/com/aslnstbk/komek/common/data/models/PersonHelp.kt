package com.aslnstbk.komek.common.data.models

data class PersonHelp(
    val id: String = "",
    val helpNeedId: String = "",
    val userName: String = "",
    val userPhoto: String = "",
    val helpName: String = "",
    val transmissionLetter: String = "",
    var isHelp: Boolean = false,
    var isRefuse: Boolean = false,
    var isDone: Boolean = false
)