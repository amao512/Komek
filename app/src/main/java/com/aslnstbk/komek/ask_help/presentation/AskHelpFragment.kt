package com.aslnstbk.komek.ask_help.presentation

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.aslnstbk.komek.R
import com.aslnstbk.komek.ask_help.presentation.viewModel.AskHelpViewModel
import com.aslnstbk.komek.common.data.ProgressState
import com.aslnstbk.komek.common.view.ToolbarBuilder
import com.aslnstbk.komek.main.presentation.APP_ACTIVITY
import com.aslnstbk.komek.utils.hide
import com.aslnstbk.komek.utils.show
import com.aslnstbk.komek.utils.showToast
import com.github.terrakok.cicerone.Router
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class AskHelpFragment : Fragment(R.layout.fragment_ask_help) {

    private val askHelpViewModel: AskHelpViewModel by viewModel()
    private val router: Router by inject()

    private lateinit var titleEditText: EditText
    private lateinit var descEditText: EditText
    private lateinit var priceEditText: EditText
    private lateinit var createButton: Button
    private lateinit var progressBar: ProgressBar

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buildToolbar()
        initViews(view)
        observeLiveData()
    }

    private fun buildToolbar() {
        val toolbar: Toolbar = ToolbarBuilder()
            .setTitle("Do you need help?")
            .setNavigationIcon(
                ContextCompat.getDrawable(APP_ACTIVITY.applicationContext, R.drawable.ic_arrow_back)
            )
            .build()

        toolbar.setNavigationOnClickListener {
            router.exit()
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
        askHelpViewModel.getEmptyFieldsLiveData().observe(viewLifecycleOwner, ::handleEmptyFields)
        askHelpViewModel.getProgressLiveData().observe(viewLifecycleOwner, ::handleProgress)
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
}