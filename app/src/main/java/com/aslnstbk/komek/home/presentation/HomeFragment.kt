package com.aslnstbk.komek.home.presentation

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
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
import com.aslnstbk.komek.home.presentation.viewModel.HomeViewModel
import com.aslnstbk.komek.main.presentation.APP_ACTIVITY
import com.aslnstbk.komek.navigation.Screens
import com.aslnstbk.komek.utils.show
import com.github.terrakok.cicerone.Router
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment(R.layout.fragment_home), OnHelpClickListener {

    private val homeViewModel: HomeViewModel by viewModel()
    private val router: Router by inject()
    private val imageLoader: ImageLoader by inject()

    private val peopleHelpMeAdapter: PeopleHelpMeAdapter by lazy {
        PeopleHelpMeAdapter(imageLoader = imageLoader)
    }

    private val helpNeedPeopleAdapter: HelpNeedPeopleAdapter by lazy {
        HelpNeedPeopleAdapter(
            imageLoader = imageLoader,
            onHelpClickListener = this
        )
    }

    private lateinit var horizontalHelpListView: LinearLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var titleAndArrowView: LinearLayout
    private lateinit var listTitleTextView: TextView
    private lateinit var askHelpButton: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel.onStart()

        initViews(view)
        initListeners()
        observeLiveData()
    }

    override fun onClick(helpNeedId: String) {
        router.navigateTo(Screens.HelpNeed(helpNeedId = helpNeedId))
    }

    override fun onHelpClick(helpNeedId: String) {
        router.navigateTo(Screens.HelpNeed(helpNeedId = helpNeedId))
    }

    private fun initViews(view: View) {
        ToolbarBuilder().hide()
        val bottomNavigationView: BottomNavigationView = APP_ACTIVITY.findViewById(R.id.activity_main_bottom_navigation_view)
        bottomNavigationView.show()

        askHelpButton = view.findViewById(R.id.fragment_home_ask_help_button)
        horizontalHelpListView = view.findViewById(R.id.fragment_home_horizontal_help_list)
        recyclerView = horizontalHelpListView.findViewById(R.id.horizontal_help_list_recycler_view)
        titleAndArrowView = horizontalHelpListView.findViewById(R.id.horizontal_help_list_layout_title_and_arrow)
        listTitleTextView = horizontalHelpListView.findViewById(R.id.horizontal_help_list_layout_title)

        recyclerView.adapter = helpNeedPeopleAdapter
    }

    private fun initListeners() {
        askHelpButton.setOnClickListener {
            router.navigateTo(Screens.AskHelp())
        }
    }

    private fun observeLiveData() {
        homeViewModel.getHelpNeedPeopleLiveData().observe(viewLifecycleOwner, ::handleHelpNeedPeople)
        homeViewModel.getPeopleHelpMeLiveData().observe(viewLifecycleOwner, ::handlePeopleHelpMe)
    }

    private fun handlePeopleHelpMe(peopleHelpMe: List<PersonHelp>) {
        listTitleTextView.text = getString(R.string.people_help_you)
        recyclerView.adapter = peopleHelpMeAdapter
        peopleHelpMeAdapter.setList(peopleHelpMe)

        titleAndArrowView.setOnClickListener {
            router.navigateTo(Screens.HelpList(isHelpMeList = true))
        }
    }

    private fun handleHelpNeedPeople(helpNeedList: List<HelpNeed>) {
        listTitleTextView.text = getString(R.string.help_need_people)
        helpNeedPeopleAdapter.setList(helpNeedList)

        titleAndArrowView.setOnClickListener {
            router.navigateTo(Screens.HelpList(isHelpMeList = false))
        }
    }
}