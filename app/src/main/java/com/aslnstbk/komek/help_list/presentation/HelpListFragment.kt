package com.aslnstbk.komek.help_list.presentation

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.aslnstbk.komek.R
import com.aslnstbk.komek.common.data.models.HelpNeed
import com.aslnstbk.komek.common.data.models.PersonHelp
import com.aslnstbk.komek.common.domain.ImageLoader
import com.aslnstbk.komek.common.view.OnHelpClickListener
import com.aslnstbk.komek.common.view.ToolbarBuilder
import com.aslnstbk.komek.common.view.adapters.HelpNeedPeopleAdapter
import com.aslnstbk.komek.common.view.adapters.PeopleHelpMeAdapter
import com.aslnstbk.komek.help_list.presentation.viewModel.HelpListViewModel
import com.aslnstbk.komek.main.presentation.APP_ACTIVITY
import com.aslnstbk.komek.navigation.Screens
import com.github.terrakok.cicerone.Router
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class HelpListFragment(
    private val isHelpMeList: Boolean
) : Fragment(R.layout.fragment_help_list), OnHelpClickListener {

    private val helpListViewModel: HelpListViewModel by viewModel()
    private val router: Router by inject()
    private val imageLoader: ImageLoader by inject()

    private val helpNeedPeopleAdapter: HelpNeedPeopleAdapter by lazy {
        HelpNeedPeopleAdapter(
            imageLoader = imageLoader,
            onHelpClickListener = this
        )
    }

    private val peopleHelpMeAdapter: PeopleHelpMeAdapter by lazy {
        PeopleHelpMeAdapter(imageLoader = imageLoader)
    }

    private lateinit var helpListRecyclerView: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        helpListViewModel.onStart()

        buildToolbar()
        initViews(view)
        observeLiveData()
    }

    override fun onClick(helpNeedId: String) {
        router.navigateTo(Screens.HelpNeed(helpNeedId))
    }

    override fun onHelpClick(helpNeedId: String) {
        router.navigateTo(Screens.HelpNeed(helpNeedId))
    }

    private fun buildToolbar() {
        val title: String = when (isHelpMeList) {
            true -> getString(R.string.people_help_you)
            false -> getString(R.string.help_need_people)
        }

        val toolbar: Toolbar = ToolbarBuilder()
            .setTitle(title)
            .setNavigationIcon(
                ContextCompat.getDrawable(APP_ACTIVITY.applicationContext, R.drawable.ic_arrow_back)
            )
            .build()

        toolbar.setNavigationOnClickListener {
            router.exit()
        }
    }

    private fun initViews(view: View) {
        helpListRecyclerView = view.findViewById(R.id.fragment_help_list_recycler_view)

        when (isHelpMeList) {
            true -> helpListRecyclerView.adapter = peopleHelpMeAdapter
            false -> helpListRecyclerView.adapter = helpNeedPeopleAdapter
        }
    }

    private fun observeLiveData() {
        helpListViewModel.getPeopleHelpMeLiveData().observe(viewLifecycleOwner, ::handlePeopleHelpMe)
        helpListViewModel.getHelpNeedPeopleLiveData().observe(viewLifecycleOwner, ::handleHelpNeedPeople)
    }

    private fun handlePeopleHelpMe(peopleHelpMe: List<PersonHelp>) {
        peopleHelpMeAdapter.setList(peopleHelpMe)
    }

    private fun handleHelpNeedPeople(helpNeedPeople: List<HelpNeed>) {
        helpNeedPeopleAdapter.setList(helpNeedPeople)
    }
}