package com.aslnstbk.komek.common.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aslnstbk.komek.R
import com.aslnstbk.komek.common.data.models.HelpNeed
import com.aslnstbk.komek.common.domain.ImageLoader
import com.aslnstbk.komek.common.view.OnHelpClickListener
import com.aslnstbk.komek.common.view.viewHolders.HelpNeedPersonViewHolder

class HelpNeedPeopleAdapter(
    private val imageLoader: ImageLoader,
    private val onHelpClickListener: OnHelpClickListener
) : RecyclerView.Adapter<HelpNeedPersonViewHolder>() {

    private val helpNeedPeople: MutableList<HelpNeed> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HelpNeedPersonViewHolder {
        return createHelpNeedPersonViewHolder(parent)
    }

    override fun onBindViewHolder(holder: HelpNeedPersonViewHolder, position: Int) {
        holder.onBind(helpNeedPeople[position])
    }

    override fun getItemCount(): Int = helpNeedPeople.size

    fun setList(helpNeedPeople: List<HelpNeed>) {
        this.helpNeedPeople.clear()
        this.helpNeedPeople.addAll(helpNeedPeople)
        notifyDataSetChanged()
    }

    private fun createHelpNeedPersonViewHolder(parent: ViewGroup): HelpNeedPersonViewHolder {
        return HelpNeedPersonViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.help_need_person_item,
                parent,
                false
            ),
            imageLoader,
            onHelpClickListener
        )
    }
}