package com.aslnstbk.komek.home.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.aslnstbk.komek.R
import com.aslnstbk.komek.help_list.presentation.HelpListFragment
import com.aslnstbk.komek.home.presentation.viewModel.HomeViewModel
import com.aslnstbk.komek.navigation.Screens
import com.github.terrakok.cicerone.Router
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModel()
    private val router: Router by inject()

    private lateinit var askHelpButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)

        initViews(view)
        initListeners()
        observeLiveData()

        childFragmentManager.beginTransaction()
            .replace(R.id.fragment_home_help_list_fragment_container, HelpListFragment(true))
            .commit()

        return view
    }

    private fun initViews(view: View) {
        askHelpButton = view.findViewById(R.id.fragment_home_ask_help_button)
    }

    private fun initListeners() {
        askHelpButton.setOnClickListener {
            router.navigateTo(Screens.AskHelp())
        }
    }

    private fun observeLiveData() {
    }
}