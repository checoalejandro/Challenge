package com.acosta.challenge

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.acosta.challenge.databinding.ActivityMainBinding
import com.acosta.challenge.ui.activities.BiometricActivity
import com.acosta.challenge.ui.viewmodels.HomeViewModel
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Main activity class.
 *
 * @author @seacosta
 */
class MainActivity : BiometricActivity() {

    private lateinit var binding: ActivityMainBinding
    private val navController: NavController by lazy { findNavController(R.id.nav_host_fragment) }
    private val viewModel: HomeViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    override fun onResume() {
        super.onResume()
        authenticate()
    }

    override fun onAuthenticationSuccess() {
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)
        // Communicate all user is authenticated.
        viewModel.authenticated.postValue(true)
    }

    override fun onAuthenticationError(message: String) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT)
            .show()
        finish()
    }

    override fun onAuthenticationFailed() {
        Snackbar.make(
            findViewById(android.R.id.content),
            getString(R.string.authentication_failure),
            Snackbar.LENGTH_SHORT
        ).show()
    }
}