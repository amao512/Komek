package com.aslnstbk.komek.help_need.presentation

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.aslnstbk.komek.R
import com.aslnstbk.komek.common.data.EMPTY_STRING
import com.aslnstbk.komek.common.data.models.HelpNeed
import com.aslnstbk.komek.common.view.HelpInfoView
import com.aslnstbk.komek.common.view.ToolbarBuilder
import com.aslnstbk.komek.common.view.viewHolders.FREE_PRICE_TEXT
import com.aslnstbk.komek.common.view.viewHolders.PRICE_TEXT_FORMAT
import com.aslnstbk.komek.help_need.presentation.viewModel.HelpNeedViewModel
import com.aslnstbk.komek.main.presentation.APP_ACTIVITY
import com.aslnstbk.komek.navigation.NavigationState
import com.github.terrakok.cicerone.Router
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class HelpNeedFragment(
    private val helpNeedId: String
) : Fragment(R.layout.fragment_help_need) {

    private val helpNeedViewModel: HelpNeedViewModel by viewModel()
    private val router: Router by inject()

    private lateinit var helpInfoView: HelpInfoView
    private lateinit var transmissionLetterEditText: EditText
    private lateinit var sendButton: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        helpNeedViewModel.onStart(helpNeedId = helpNeedId)

        buildToolbar()
        initViews(view)
        observeLiveData()
    }

    private fun buildToolbar() {
        val toolbar: Toolbar = ToolbarBuilder()
            .setNavigationIcon(
                ContextCompat.getDrawable(
                    APP_ACTIVITY.applicationContext,
                    R.drawable.ic_arrow_back
                )
            )
            .build()

        toolbar.setNavigationOnClickListener {
            helpNeedViewModel.setNavigation(NavigationState.Back)
        }
    }

    private fun initViews(view: View) {
        helpInfoView = view.findViewById(R.id.fragment_help_need_help_info_view)
        transmissionLetterEditText = view.findViewById(R.id.fragment_help_need_transmission_letter_edit_text)
        sendButton = view.findViewById(R.id.fragment_help_need_send_button)
    }

    private fun observeLiveData() {
        helpNeedViewModel.helpNeedLiveData.observe(viewLifecycleOwner, ::handleHelpNeed)
        helpNeedViewModel.navigationLiveData.observe(viewLifecycleOwner, ::handleNavigation)
    }

    private fun handleHelpNeed(helpNeed: HelpNeed) {
        helpInfoView.setUserName(helpNeed.ownerName)
        helpInfoView.setUserPhoto(helpNeed.ownerPhotoUrl)
        helpInfoView.setTitleTextView(helpNeed.title)
        helpInfoView.setDec(helpNeed.desc)

        when (helpNeed.price == EMPTY_STRING) {
            true -> helpInfoView.setPrice(FREE_PRICE_TEXT)
            false -> helpInfoView.setPrice(PRICE_TEXT_FORMAT.format(helpNeed.price))
        }

        initListeners(helpName = helpNeed.title)
    }

    private fun initListeners(helpName: String) {
        sendButton.setOnClickListener {
            helpNeedViewModel.onHelp(
                helpNeedId = helpNeedId,
                helpName = helpName,
                transmissionLetter = transmissionLetterEditText.text.toString()
            )
        }
    }

    private fun handleNavigation(navigationState: NavigationState?) {
        when (navigationState) {
            is NavigationState.Back -> router.exit()
            else -> {}
        }
    }
}