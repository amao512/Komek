package com.aslnstbk.komek.common.data.models

data class User(
    val id: String = "",
    val email: String = "",
    val name: String = "",
    val photoUrl: String = "",
    val phone: String = "",
    val helpNeedList: List<HelpNeed> = emptyList(),
    val helpList: List<HelpNeed> = emptyList(),
    val starList: List<Star> = emptyList(),
    val reviewList: List<Review> = emptyList()
)