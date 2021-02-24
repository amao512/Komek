package com.aslnstbk.komek.person_help.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.aslnstbk.komek.R
import com.aslnstbk.komek.common.data.models.PersonHelp
import com.aslnstbk.komek.common.view.HelpInfoView
import com.aslnstbk.komek.common.view.ToolbarBuilder
import com.aslnstbk.komek.main.presentation.APP_ACTIVITY
import com.aslnstbk.komek.navigation.NavigationState
import com.aslnstbk.komek.person_help.presentation.viewModel.PersonHelpViewModel
import com.github.terrakok.cicerone.Router
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class PersonHelpFragment(
    private val personHelp: PersonHelp
) : Fragment() {

    private val personHelpViewModel: PersonHelpViewModel by viewModel()
    private val router: Router by inject()

    private lateinit var helpInfoView: HelpInfoView
    private lateinit var approveButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View =  inflater.inflate(R.layout.fragment_person_help, container, false)

        personHelpViewModel.onStart(personHelp)

        initViews(view)
        buildToolbar()
        observeLiveData()

        return view
    }

    private fun initViews(view: View) {
        helpInfoView = view.findViewById(R.id.fragment_person_help_help_info_view)
        approveButton = view.findViewById(R.id.fragment_person_help_approve_button)
    }

    private fun buildToolbar() {
        val toolbar: Toolbar = ToolbarBuilder().setNavigationIcon(
            ContextCompat.getDrawable(APP_ACTIVITY.applicationContext, R.drawable.ic_arrow_back)
        )
        .build()

        toolbar.setNavigationOnClickListener {
            router.exit()
        }
    }

    private fun observeLiveData() {
        personHelpViewModel.personHelpLiveData.observe(viewLifecycleOwner, ::handlePersonHelp)
        personHelpViewModel.navigationLiveData.observe(viewLifecycleOwner, ::handleNavigation)
    }

    private fun handlePersonHelp(
        personHelp: PersonHelp
    ) {
        helpInfoView.setUserName(personHelp.userName)
        helpInfoView.setUserPhoto(personHelp.userPhoto)
        helpInfoView.setTitleTextView(personHelp.helpName)
        helpInfoView.setDec(personHelp.transmissionLetter)

        approveButton.setOnClickListener {
            personHelpViewModel.onApprove(personHelp)
        }
    }

    private fun handleNavigation(navigationState: NavigationState) {
        when (navigationState) {
            is NavigationState.Back -> router.exit()
            else -> {}
        }
    }
}