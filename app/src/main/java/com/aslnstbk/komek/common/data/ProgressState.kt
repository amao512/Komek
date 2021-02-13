package com.aslnstbk.komek.common.data

sealed class ProgressState {
    object Loading: ProgressState()
    object Done: ProgressState()
}