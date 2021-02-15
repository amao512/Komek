package com.aslnstbk.komek.auth.presentation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.aslnstbk.komek.R
import com.aslnstbk.komek.auth.presentation.viewModel.AuthViewModel
import com.aslnstbk.komek.navigation.NavigationState
import com.aslnstbk.komek.navigation.Screens
import com.github.terrakok.cicerone.Router
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.SignInButton
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

const val RC_SIGN_IN = 0

class AuthFragment : Fragment() {

    private val authViewModel: AuthViewModel by viewModel()
    private val router: Router by inject()
    private val googleSignInClient: GoogleSignInClient by inject()

    private lateinit var signInButton: SignInButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_auth, container, false)

        initViews(view)
        observeLiveData()

        return view
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN && data != null) {
            authViewModel.signIn(data)
        }
    }

    private fun initViews(view: View) {
        signInButton = view.findViewById(R.id.fragment_auth_sign_in_button)
        signInButton.setSize(SignInButton.SIZE_STANDARD)

        signInButton.setOnClickListener {
            startActivityForResult(googleSignInClient.signInIntent, RC_SIGN_IN)
        }
    }

    private fun observeLiveData() {
        authViewModel.navigationLiveData.observe(viewLifecycleOwner, ::handleNavigation)
    }

    private fun handleNavigation(navigationState: NavigationState) {
        when (navigationState) {
            is NavigationState.BaseFlow -> router.replaceScreen(Screens.BaseFlow())
            else -> {}
        }
    }
}