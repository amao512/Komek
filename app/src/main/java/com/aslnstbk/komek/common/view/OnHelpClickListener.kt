package com.aslnstbk.komek.common.view

import com.aslnstbk.komek.common.data.models.PersonHelp

interface OnHelpClickListener {

    fun onClick(helpNeedId: String)

    fun onHelpClick(helpNeedId: String)

    fun onApproveClick(personHelp: PersonHelp)

    fun onRefuseClick(personHelp: PersonHelp)
}