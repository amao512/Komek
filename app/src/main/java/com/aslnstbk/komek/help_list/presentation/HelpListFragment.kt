package com.aslnstbk.komek.help_list.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aslnstbk.komek.R
import com.aslnstbk.komek.common.data.models.HelpNeed
import com.aslnstbk.komek.common.data.models.PersonHelp
import com.aslnstbk.komek.common.domain.ImageLoader
import com.aslnstbk.komek.common.view.OnHelpClickListener
import com.aslnstbk.komek.common.view.PersonHelpView
import com.aslnstbk.komek.common.view.ToolbarBuilder
import com.aslnstbk.komek.common.view.adapters.HelpNeedPeopleAdapter
import com.aslnstbk.komek.common.view.adapters.PeopleHelpMeAdapter
import com.aslnstbk.komek.help_list.presentation.viewModel.HelpListViewModel
import com.aslnstbk.komek.main.presentation.APP_ACTIVITY
import com.aslnstbk.komek.navigation.NavigationState
import com.aslnstbk.komek.navigation.Screens
import com.aslnstbk.komek.utils.hide
import com.aslnstbk.komek.utils.show
import com.github.terrakok.cicerone.Router
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class HelpListFragment(
    private val isHorizontal: Boolean,
) : Fragment(), OnHelpClickListener {

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
        PeopleHelpMeAdapter(
            imageLoader = imageLoader,
            onHelpClickListener = this
        )
    }

    private lateinit var toolbar: Toolbar
    private lateinit var helpListRecyclerView: RecyclerView
    private lateinit var titleAndArrowView: LinearLayout
    private lateinit var listTitleTextView: TextView
    private lateinit var personHelpView: PersonHelpView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_help_list, container, false)

        helpListViewModel.onStart()

        buildToolbar()
        initViews(view)
        initListeners()
        observeLiveData()

        return view
    }

    override fun onClick(helpNeedId: String) {
        helpListViewModel.setNavigation(
            NavigationState.HelpNeed(helpNeedId)
        )
    }

    override fun onHelpClick(helpNeedId: String) {
        router.navigateTo(Screens.HelpNeed(helpNeedId))
    }

    override fun onApproveClick(personHelp: PersonHelp) {
        router.navigateTo(Screens.PersonHelp(personHelp))
    }

    override fun onRefuseClick(personHelp: PersonHelp) {
        helpListViewModel.onRefuseHelp(personHelp)
    }

    override fun onDoneClick(personHelp: PersonHelp) {
        helpListViewModel.onDoneHelp(personHelp)
    }

    private fun buildToolbar() {
        if (!isHorizontal) {
            toolbar = ToolbarBuilder()
                .setNavigationIcon(
                    ContextCompat.getDrawable(
                        APP_ACTIVITY.applicationContext,
                        R.drawable.ic_arrow_back
                    )
                )
                .build()

            toolbar.setNavigationOnClickListener {
                helpListViewModel.setNavigation(NavigationState.Back)
            }
        }
    }

    private fun initViews(view: View) {
        personHelpView = view.findViewById(R.id.fragment_help_list_person_help_view)
        helpListRecyclerView = view.findViewById(R.id.fragment_help_list_recycler_view)
        titleAndArrowView = view.findViewById(R.id.fragment_help_list_title_and_arrow)
        listTitleTextView = view.findViewById(R.id.fragment_help_list_title)

        when (isHorizontal) {
            true -> titleAndArrowView.show()
            false -> titleAndArrowView.hide()
        }

        setRecyclerView()
    }

    private fun setRecyclerView() {
        val orientation: Int = when (isHorizontal) {
            true -> LinearLayoutManager.HORIZONTAL
            false -> LinearLayoutManager.VERTICAL
        }

        helpListRecyclerView.layoutManager = LinearLayoutManager(activity, orientation, false)
        helpListRecyclerView.adapter = helpNeedPeopleAdapter
    }

    private fun initListeners() {
        titleAndArrowView.setOnClickListener {
            helpListViewModel.setNavigation(NavigationState.HelpList)
        }
    }

    private fun observeLiveData() {
        helpListViewModel.peopleHelpLiveData.observe(viewLifecycleOwner, ::handlePeopleHelpMe)
        helpListViewModel.helpNeedPeopleLiveData.observe(viewLifecycleOwner, ::handleHelpNeedPeople)
        helpListViewModel.approvedPersonHelpLiveData.observe(viewLifecycleOwner, ::handleApprovedPersonHelp)
        helpListViewModel.navigationLiveData.observe(viewLifecycleOwner, ::handleNavigation)
    }

    private fun handlePeopleHelpMe(peopleHelpMe: List<PersonHelp>) {
        helpListRecyclerView.adapter = peopleHelpMeAdapter
        peopleHelpMeAdapter.setList(peopleHelpMe)

        if (!isHorizontal) {
            toolbar.title = getString(R.string.people_help_you)
        } else {
            listTitleTextView.text = getString(R.string.people_help_you)
        }

        Toast.makeText(APP_ACTIVITY.applicationContext, "People help me", Toast.LENGTH_SHORT).show()
    }

    private fun handleHelpNeedPeople(helpNeedPeople: List<HelpNeed>) {
        helpNeedPeopleAdapter.setList(helpNeedPeople)

        if (!isHorizontal) {
            toolbar.title = getString(R.string.help_need_people)
        } else {
            listTitleTextView.text = getString(R.string.help_need_people)
        }

        Toast.makeText(APP_ACTIVITY.applicationContext, "Help need people", Toast.LENGTH_SHORT).show()
    }

    private fun handleApprovedPersonHelp(personHelp: PersonHelp) {
        personHelpView.fillData(personHelp)
        personHelpView.setOnClickListener(personHelp, onHelpClickListener = this)
        personHelpView.show()
        helpListRecyclerView.hide()

        Toast.makeText(APP_ACTIVITY.applicationContext, "Approve person help", Toast.LENGTH_SHORT).show()
    }

    private fun handleNavigation(navigationState: NavigationState) {
        when (navigationState) {
            is NavigationState.Back -> router.exit()
            is NavigationState.HelpList -> router.navigateTo(
                Screens.HelpList(isHorizontal = false)
            )
            else -> {}
        }
    }
}