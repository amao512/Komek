package com.aslnstbk.komek.common.view.viewHolders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aslnstbk.komek.R
import com.aslnstbk.komek.common.data.models.PersonHelp
import com.aslnstbk.komek.common.domain.ImageLoader
import de.hdodenhof.circleimageview.CircleImageView

const val HELP_ME_USER_NAME_TEXT_FORMAT = "%s is helping you"
const val PRICE_TEXT_FORMAT = "%s KZT"
const val FREE_PRICE_TEXT = "free"

class PersonHelpMeViewHolder(
    itemView: View,
    private val imageLoader: ImageLoader
) : RecyclerView.ViewHolder(itemView) {

    private val userPhotoImageView: CircleImageView = itemView.findViewById(R.id.person_help_me_item_user_photo_image_view)
    private val userNameTextView: TextView = itemView.findViewById(R.id.person_help_me_item_user_name_text_view)
    private val helpNameTextView: TextView = itemView.findViewById(R.id.person_help_me_item_help_name)
    private val transmissionLetterTextView: TextView = itemView.findViewById(R.id.person_help_me_item_transmission_letter)

    fun onBind(help: PersonHelp) {
        userNameTextView.text = HELP_ME_USER_NAME_TEXT_FORMAT.format(help.userName)
        helpNameTextView.text = help.helpName
        transmissionLetterTextView.text = help.transmissionLetter

        imageLoader.load(
            url = help.userPhoto,
            target = userPhotoImageView
        )
    }
}