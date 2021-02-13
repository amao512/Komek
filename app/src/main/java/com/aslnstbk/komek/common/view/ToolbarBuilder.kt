package com.aslnstbk.komek.common.view

import android.graphics.drawable.Drawable
import androidx.appcompat.widget.Toolbar
import com.aslnstbk.komek.R
import com.aslnstbk.komek.main.presentation.APP_ACTIVITY
import com.aslnstbk.komek.utils.hide
import com.aslnstbk.komek.utils.show
import com.google.android.material.appbar.AppBarLayout

class ToolbarBuilder {

    private var title: String = ""
    private var menu: Int = 0
    private var navigationId: Int = 0
    private var navigationIcon: Drawable? = null

    fun setTitle(title: String): ToolbarBuilder {
        this.title = title

        return this
    }

    fun setMenu(menu: Int): ToolbarBuilder {
        this.menu = menu

        return this
    }

    fun setNavigationIcon(navigationIcon: Drawable?): ToolbarBuilder {
        this.navigationId = 1
        this.navigationIcon = navigationIcon

        return this
    }

    fun build(): Toolbar {
        val appBarLayout: AppBarLayout = APP_ACTIVITY.findViewById(R.id.activity_main_app_bar)
        val toolbar: Toolbar = APP_ACTIVITY.findViewById(R.id.activity_main_toolbar)

        appBarLayout.show()
        toolbar.title = this.title
        toolbar.menu.clear()

        if (navigationId != 0) {
            toolbar.navigationIcon = this.navigationIcon
        }

        if (menu != 0) {
            toolbar.inflateMenu(this.menu)
        }

        return toolbar
    }

    fun hide() {
        val appBarLayout: AppBarLayout = APP_ACTIVITY.findViewById(R.id.activity_main_app_bar)
        appBarLayout.hide()
    }
}