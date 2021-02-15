package com.aslnstbk.komek.navigation

sealed class NavigationState {

    object Auth : NavigationState()

    object BaseFlow : NavigationState()

    object AskHelp : NavigationState()

    object HelpList : NavigationState()

    data class HelpNeed(val helpNeedId: String) : NavigationState()

    object Back : NavigationState()
}