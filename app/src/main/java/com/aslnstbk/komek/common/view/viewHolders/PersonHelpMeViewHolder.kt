package com.aslnstbk.komek.common.view.viewHolders

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aslnstbk.komek.R
import com.aslnstbk.komek.common.data.models.PersonHelp
import com.aslnstbk.komek.common.domain.ImageLoader
import com.aslnstbk.komek.common.view.OnHelpClickListener
import com.aslnstbk.komek.utils.hide
import com.aslnstbk.komek.utils.show
import de.hdodenhof.circleimageview.CircleImageView

const val HELP_ME_USER_NAME_TEXT_FORMAT = "%s is helping you"
const val PRICE_TEXT_FORMAT = "%s KZT"
const val FREE_PRICE_TEXT = "free"

class PersonHelpMeViewHolder(
    itemView: View,
    private val imageLoader: ImageLoader,
    private val onHelpClickListener: OnHelpClickListener
) : RecyclerView.ViewHolder(itemView) {

    private val userPhotoImageView: CircleImageView = itemView.findViewById(R.id.person_help_me_item_user_photo_image_view)
    private val userNameTextView: TextView = itemView.findViewById(R.id.person_help_me_item_user_name_text_view)
    private val helpNameTextView: TextView = itemView.findViewById(R.id.person_help_me_item_help_name)
    private val transmissionLetterTextView: TextView = itemView.findViewById(R.id.person_help_me_item_transmission_letter)
    private val refuseButton: Button = itemView.findViewById(R.id.person_help_me_item_refuse_button)
    private val approveButton: Button = itemView.findViewById(R.id.person_help_me_item_approve_button)
    private val refusedTextView: TextView = itemView.findViewById(R.id.person_help_me_item_refused_text_view)

    fun onBind(help: PersonHelp) {
        userNameTextView.text = HELP_ME_USER_NAME_TEXT_FORMAT.format(help.userName)
        helpNameTextView.text = help.helpName
        transmissionLetterTextView.text = help.transmissionLetter

        imageLoader.load(
            url = help.userPhoto,
            target = userPhotoImageView
        )

        if (help.isRefuse) {
            itemView.setBackgroundResource(R.drawable.bg_grey_card)
            refuseButton.hide()
            approveButton.hide()
            refusedTextView.show()
        }

        initListeners(help)
    }

    private fun initListeners(help: PersonHelp) {
        refuseButton.setOnClickListener {
            onHelpClickListener.onRefuseClick(personHelp = help)
        }

        approveButton.setOnClickListener {
            onHelpClickListener.onApproveClick(personHelp = help)
        }
    }
}