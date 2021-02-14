package com.aslnstbk.komek.home.presentation.viewModel

import androidx.lifecycle.ViewModel
import com.aslnstbk.komek.home.domain.HomeRepository

class HomeViewModel(
    private val homeRepository: HomeRepository
) : ViewModel() {

    fun onStart() {}
}