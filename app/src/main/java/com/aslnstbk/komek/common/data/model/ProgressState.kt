package com.aslnstbk.komek.common.data.model

sealed class ProgressState {
    object Loading: ProgressState()
    object Done: ProgressState()
}