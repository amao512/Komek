package com.aslnstbk.komek.common.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aslnstbk.komek.R
import com.aslnstbk.komek.common.domain.ImageLoader
import com.aslnstbk.komek.common.data.models.PersonHelp
import com.aslnstbk.komek.common.view.viewHolders.PersonHelpMeViewHolder

class PeopleHelpMeAdapter(
    private val imageLoader: ImageLoader
) : RecyclerView.Adapter<PersonHelpMeViewHolder>() {

    private val helpList: MutableList<PersonHelp> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonHelpMeViewHolder {
        return createPersonHelpMeViewHolder(parent)
    }

    override fun onBindViewHolder(holder: PersonHelpMeViewHolder, position: Int) {
        holder.onBind(helpList[position])
    }

    override fun getItemCount(): Int = helpList.size

    fun setList(helpList: List<PersonHelp>) {
        this.helpList.clear()
        this.helpList.addAll(helpList)
        notifyDataSetChanged()
    }

    private fun createPersonHelpMeViewHolder(parent: ViewGroup): PersonHelpMeViewHolder {
        return PersonHelpMeViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.person_help_me_item,
                parent,
                false
            ),
            imageLoader
        )
    }
}