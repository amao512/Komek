package com.aslnstbk.komek.help_need.presentation

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.aslnstbk.komek.R
import com.aslnstbk.komek.common.data.EMPTY_STRING
import com.aslnstbk.komek.common.data.models.HelpNeed
import com.aslnstbk.komek.common.domain.ImageLoader
import com.aslnstbk.komek.common.view.ToolbarBuilder
import com.aslnstbk.komek.common.view.viewHolders.FREE_PRICE_TEXT
import com.aslnstbk.komek.common.view.viewHolders.PRICE_TEXT_FORMAT
import com.aslnstbk.komek.help_need.presentation.viewModel.HelpNeedViewModel
import com.aslnstbk.komek.main.presentation.APP_ACTIVITY
import com.github.terrakok.cicerone.Router
import de.hdodenhof.circleimageview.CircleImageView
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class HelpNeedFragment(
    private val helpNeedId: String
) : Fragment(R.layout.fragment_help_need) {

    private val helpNeedViewModel: HelpNeedViewModel by viewModel()
    private val router: Router by inject()
    private val imageLoader: ImageLoader by inject()

    private lateinit var userPhotoImageView: CircleImageView
    private lateinit var userNameTextView: TextView
    private lateinit var priceTextView: TextView
    private lateinit var titleTextView: TextView
    private lateinit var descTextView: TextView
    private lateinit var transmissionLetterEditText: EditText
    private lateinit var sendButton: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        helpNeedViewModel.onStart(helpNeedId = helpNeedId)

        buildToolbar()
        initViews(view)
        initListeners()
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
            router.exit()
        }
    }

    private fun initViews(view: View) {
        userPhotoImageView = view.findViewById(R.id.fragment_help_need_user_photo)
        userNameTextView = view.findViewById(R.id.fragment_help_need_user_name_text_view)
        priceTextView = view.findViewById(R.id.fragment_help_need_price_text_view)
        titleTextView = view.findViewById(R.id.fragment_help_need_title)
        descTextView = view.findViewById(R.id.fragment_help_need_desc_text_view)
        transmissionLetterEditText = view.findViewById(R.id.fragment_help_need_transmission_letter_edit_text)
        sendButton = view.findViewById(R.id.fragment_help_need_send_button)
    }

    private fun initListeners() {
        sendButton.setOnClickListener {
            helpNeedViewModel.onHelp(
                helpNeedId = helpNeedId,
                transmissionLetter = transmissionLetterEditText.text.toString()
            )
        }
    }

    private fun observeLiveData() {
        helpNeedViewModel.helpNeedLiveData.observe(viewLifecycleOwner, ::handleHelpNeed)
    }

    private fun handleHelpNeed(helpNeed: HelpNeed) {
        userNameTextView.text = helpNeed.ownerName
        titleTextView.text = helpNeed.title
        descTextView.text = helpNeed.desc

        when (helpNeed.price == EMPTY_STRING) {
            true -> priceTextView.text = FREE_PRICE_TEXT
            false -> priceTextView.text = PRICE_TEXT_FORMAT.format(helpNeed.price)
        }

        imageLoader.load(
            url = helpNeed.ownerPhotoUrl,
            target = userPhotoImageView
        )
    }
}