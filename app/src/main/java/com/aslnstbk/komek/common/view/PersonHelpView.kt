package com.aslnstbk.komek.common.view

import android.content.Context
import android.util.AttributeSet
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.aslnstbk.komek.R
import com.aslnstbk.komek.common.data.DefaultImageLoader
import com.aslnstbk.komek.common.data.models.PersonHelp
import com.aslnstbk.komek.common.domain.ImageLoader
import com.aslnstbk.komek.utils.hide
import com.aslnstbk.komek.utils.show
import de.hdodenhof.circleimageview.CircleImageView

class PersonHelpView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defAttrStyle: Int = 0
) : ConstraintLayout(context, attrs, defAttrStyle) {

    private var imageLoader: ImageLoader

    private var photoImageView: CircleImageView
    private var userNameTextView: TextView
    private var helpTitleTextView: TextView
    private var transmissionTextView: TextView
    private var refuseButton: Button
    private var approveButton: Button
    private var doneButton: Button
    private var refusedTextView: TextView

    init {
        inflate(context, R.layout.person_help_me_item, this)

        imageLoader = DefaultImageLoader()

        photoImageView = findViewById(R.id.person_help_me_item_user_photo_image_view)
        userNameTextView = findViewById(R.id.person_help_me_item_user_name_text_view)
        helpTitleTextView = findViewById(R.id.person_help_me_item_help_name)
        transmissionTextView = findViewById(R.id.person_help_me_item_transmission_letter)
        refuseButton = findViewById(R.id.person_help_me_item_refuse_button)
        approveButton = findViewById(R.id.person_help_me_item_approve_button)
        doneButton = findViewById(R.id.person_help_me_item_done_button)
        refusedTextView = findViewById(R.id.person_help_me_item_refused_text_view)
    }

    fun fillData(personHelp: PersonHelp) {
        imageLoader.load(url = personHelp.userPhoto, target = photoImageView)
        userNameTextView.text = personHelp.userName
        helpTitleTextView.text = personHelp.helpName
        transmissionTextView.text = personHelp.transmissionLetter

        if (personHelp.isRefuse) {
            this.setBackgroundResource(R.drawable.bg_grey_card)
            approveButton.hide()
            refuseButton.hide()
            doneButton.hide()
            refusedTextView.show()
        }

        if (personHelp.isHelp) {
            approveButton.hide()
            refusedTextView.hide()
            refuseButton.show()
            doneButton.show()
        }
    }

    fun setOnClickListener(
        personHelp: PersonHelp,
        onHelpClickListener: OnHelpClickListener
    ) {
        refuseButton.setOnClickListener {
            onHelpClickListener.onRefuseClick(personHelp = personHelp)
        }

        approveButton.setOnClickListener {
            onHelpClickListener.onApproveClick(personHelp = personHelp)
        }

        doneButton.setOnClickListener {
            onHelpClickListener.onDoneClick(personHelp = personHelp)
        }
    }
}