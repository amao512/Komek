package com.aslnstbk.komek.profile.presentation

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.aslnstbk.komek.R
import com.aslnstbk.komek.common.domain.ImageLoader
import com.aslnstbk.komek.navigation.NavigationState
import com.aslnstbk.komek.navigation.Screens
import com.aslnstbk.komek.profile.presentation.viewModel.ProfileViewModel
import com.github.terrakok.cicerone.Router
import com.google.firebase.auth.FirebaseUser
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val profileViewModel: ProfileViewModel by viewModel()
    private val router: Router by inject()
    private val imageLoader: ImageLoader by inject()

    private lateinit var userPhotoImageView: ImageView
    private lateinit var userFullNameTextView: TextView
    private lateinit var userEmailTextView: TextView
    private lateinit var userIdTextView: TextView
    private lateinit var exitButton: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profileViewModel.onStart()

        initViews(view)
        observeLiveData()
    }

    private fun initViews(view: View) {
        userPhotoImageView = view.findViewById(R.id.fragment_profile_user_photo)
        userFullNameTextView = view.findViewById(R.id.fragment_profile_user_full_name)
        userEmailTextView = view.findViewById(R.id.fragment_profile_user_email)
        userIdTextView = view.findViewById(R.id.fragment_profile_user_id)
        exitButton = view.findViewById(R.id.fragment_profile_exit_button)

        exitButton.setOnClickListener {
            profileViewModel.signOut()
        }
    }

    private fun observeLiveData() {
        profileViewModel.profileLiveData.observe(viewLifecycleOwner, ::handleProfile)
        profileViewModel.navigationLiveData.observe(viewLifecycleOwner, ::handleNavigation)
    }

    private fun handleProfile(firebaseUser: FirebaseUser) {
        userFullNameTextView.text = firebaseUser.displayName
        userEmailTextView.text = firebaseUser.email
        userIdTextView.text = firebaseUser.tenantId

        imageLoader.load(
            url = firebaseUser.photoUrl.toString(),
            target = userPhotoImageView
        )
    }

    private fun handleNavigation(navigationState: NavigationState) {
        when (navigationState) {
            is NavigationState.Auth -> router.replaceScreen(Screens.Auth())
            else -> {}
        }
    }
}