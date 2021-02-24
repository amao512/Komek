package com.aslnstbk.komek.common.view

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.aslnstbk.komek.R
import com.aslnstbk.komek.common.data.DefaultImageLoader
import com.aslnstbk.komek.common.domain.ImageLoader
import com.aslnstbk.komek.utils.hide
import de.hdodenhof.circleimageview.CircleImageView

class HelpInfoView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attributeSet, defStyleAttr) {

    private var imageLoader: ImageLoader

    private var userPhotoImageView: CircleImageView
    private var userNameTextView: TextView
    private var priceTextView: TextView
    private var titleTextView: TextView
    private var descTextView: TextView

    init {
        inflate(context, R.layout.help_info_layout, this)

        imageLoader = DefaultImageLoader()

        userPhotoImageView = findViewById(R.id.help_info_layout_user_photo)
        userNameTextView = findViewById(R.id.help_info_layout_user_name_text_view)
        priceTextView = findViewById(R.id.help_info_layout_price_text_view)
        titleTextView = findViewById(R.id.help_info_layout_title)
        descTextView = findViewById(R.id.help_info_layout_desc_text_view)
    }

    fun setUserPhoto(url: String) {
        imageLoader.load(
            url = url,
            target = userPhotoImageView
        )
    }

    fun setUserName(name: String) {
        userNameTextView.text = name
    }

    fun setPrice(price: String) {
        priceTextView.text = price
    }

    fun hidePrice() {
        priceTextView.hide()
    }

    fun setTitleTextView(title: String) {
        titleTextView.text = title
    }

    fun setDec(desc: String) {
        descTextView.text = desc
    }
}