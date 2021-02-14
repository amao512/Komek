package com.aslnstbk.komek.help_need.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aslnstbk.komek.common.data.models.HelpNeed
import com.aslnstbk.komek.help_need.domain.HelpNeedRepository
import com.github.terrakok.cicerone.Router

class HelpNeedViewModel(
    private val helpNeedRepository: HelpNeedRepository,
    private val router: Router
) : ViewModel() {

    private val _helpNeedLiveData: MutableLiveData<HelpNeed> = MutableLiveData()

    val helpNeedLiveData: LiveData<HelpNeed> = _helpNeedLiveData

    fun onStart(helpNeedId: String) {
        getHelpNeed(helpNeedId)
    }

    fun onHelp(
        helpNeedId: String,
        helpName: String,
        transmissionLetter: String,
    ) {
        helpNeedRepository.onHelp(
            helpNeedId = helpNeedId,
            helpName = helpName,
            transmissionLetter = transmissionLetter,
            onSuccess = {
                router.exit()
            },
            onFail = {}
        )
    }

    private fun getHelpNeed(helpNeedId: String) {
        helpNeedRepository.getHelpNeed(
            helpNeedId = helpNeedId,
            onSuccess = {
                _helpNeedLiveData.value = it
            },
            onFail = {}
        )
    }
}