package com.aslnstbk.komek.common.view.viewHolders

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aslnstbk.komek.R
import com.aslnstbk.komek.common.data.models.HelpNeed
import com.aslnstbk.komek.common.domain.ImageLoader
import com.aslnstbk.komek.common.view.OnHelpClickListener
import de.hdodenhof.circleimageview.CircleImageView

class HelpNeedPersonViewHolder(
    itemView: View,
    private val imageLoader: ImageLoader,
    private val onHelpClickListener: OnHelpClickListener
) : RecyclerView.ViewHolder(itemView) {

    private val personPhotoImageView: CircleImageView = itemView.findViewById(R.id.help_need_person_item_user_photo_image_view)
    private val personNameTextView: TextView = itemView.findViewById(R.id.help_need_person_item_user_name_text_view)
    private val helpTitleTextView: TextView = itemView.findViewById(R.id.help_need_person_item_help_name)
    private val descTextView: TextView = itemView.findViewById(R.id.help_need_person_item_desc_text_view)
    private val priceTextView: TextView = itemView.findViewById(R.id.help_need_person_item_price)
    private val helpButton: Button = itemView.findViewById(R.id.help_need_person_item_help_button)

    fun onBind(helpNeed: HelpNeed) {
        personNameTextView.text = helpNeed.ownerName
        helpTitleTextView.text = helpNeed.title
        descTextView.text = helpNeed.desc

        if (helpNeed.price == ""){
            priceTextView.text = FREE_PRICE_TEXT
        } else {
            priceTextView.text = PRICE_TEXT_FORMAT.format(helpNeed.price)
        }

        imageLoader.load(
            url = helpNeed.ownerPhotoUrl,
            personPhotoImageView
        )

        initListeners(helpNeed = helpNeed)
    }

    private fun initListeners(helpNeed: HelpNeed) {
        itemView.setOnClickListener {
            onHelpClickListener.onClick(helpNeedId = helpNeed.id)
        }

        helpButton.setOnClickListener {
            onHelpClickListener.onHelpClick(helpNeedId = helpNeed.id)
        }
    }
}