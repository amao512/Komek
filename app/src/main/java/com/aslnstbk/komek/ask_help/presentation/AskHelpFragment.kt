package com.aslnstbk.komek.ask_help.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.aslnstbk.komek.R
import com.aslnstbk.komek.ask_help.presentation.viewModel.AskHelpViewModel
import com.aslnstbk.komek.common.data.model.ProgressState
import com.aslnstbk.komek.common.view.ToolbarBuilder
import com.aslnstbk.komek.main.presentation.APP_ACTIVITY
import com.aslnstbk.komek.navigation.NavigationState
import com.aslnstbk.komek.utils.hide
import com.aslnstbk.komek.utils.show
import com.aslnstbk.komek.utils.showToast
import com.github.terrakok.cicerone.Router
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class AskHelpFragment : Fragment() {

    private val askHelpViewModel: AskHelpViewModel by viewModel()
    private val router: Router by inject()

    private lateinit var titleEditText: EditText
    private lateinit var descEditText: EditText
    private lateinit var priceEditText: EditText
    private lateinit var createButton: Button
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_ask_help, container, false)

        buildToolbar()
        initViews(view)
        observeLiveData()

        return view
    }

    private fun buildToolbar() {
        val toolbar: Toolbar = ToolbarBuilder()
            .setTitle("Do you need help?")
            .setNavigationIcon(
                ContextCompat.getDrawable(APP_ACTIVITY.applicationContext, R.drawable.ic_arrow_back)
            )
            .build()

        toolbar.setNavigationOnClickListener {
            askHelpViewModel.setNavigation(NavigationState.Back)
        }
    }

    private fun initViews(view: View) {
        titleEditText = view.findViewById(R.id.fragment_ask_help_title_edit_text)
        descEditText = view.findViewById(R.id.fragment_ask_help_desc_edit_text)
        priceEditText = view.findViewById(R.id.fragment_ask_help_price_edit_text)
        createButton = view.findViewById(R.id.fragment_ask_help_create_button)
        progressBar = view.findViewById(R.id.fragment_ask_help_progress_bar)

        createButton.setOnClickListener {
            askHelpViewModel.createAskHelp(
                title = titleEditText.text.toString(),
                desc = descEditText.text.toString(),
                price = priceEditText.text.toString()
            )
        }
    }

    private fun observeLiveData() {
        askHelpViewModel.emptyFieldsLiveData.observe(viewLifecycleOwner, ::handleEmptyFields)
        askHelpViewModel.progressLiveData.observe(viewLifecycleOwner, ::handleProgress)
        askHelpViewModel.navigationLiveData.observe(viewLifecycleOwner, ::handleNavigation)
    }

    private fun handleProgress(progressState: ProgressState) {
        when (progressState) {
            is ProgressState.Loading -> {
                createButton.hide()
                progressBar.show()
            }
            is ProgressState.Done -> {
                progressBar.hide()
                createButton.show()
            }
        }
    }

    private fun handleEmptyFields(isEmptyFields: Boolean) {
        when (isEmptyFields) {
            true -> showToast(getString(R.string.toast_message_empty_fields))
        }
    }

    private fun handleNavigation(navigationState: NavigationState) {
        when (navigationState) {
            is NavigationState.Back -> router.exit()
        }
    }
}