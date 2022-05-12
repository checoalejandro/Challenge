package com.acosta.challenge.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.acosta.challenge.R
import com.google.android.material.snackbar.Snackbar
import java.util.concurrent.Executor

abstract class BiometricActivity : AppCompatActivity() {

    /**
     * Biometric
     */
    private lateinit var biometricManager: BiometricManager
    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo
    private val authenticators = BiometricManager.Authenticators.BIOMETRIC_STRONG

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        biometricManager = BiometricManager.from(this)
        // Used for non-fingerprint devices
        if (biometricManager.canAuthenticate(authenticators) != BiometricManager.BIOMETRIC_SUCCESS) {
            onAuthenticationSuccess()
            return
        }
        executor = ContextCompat.getMainExecutor(this)
        biometricPrompt = BiometricPrompt(this, executor, authenticationCallback)

        try {
            promptInfo = BiometricPrompt.PromptInfo.Builder()
                .setTitle(getString(R.string.authentication_title))
                .setSubtitle(getString(R.string.authentication_subtitle))
                .setNegativeButtonText(getString(R.string.authentication_negative))
                .setAllowedAuthenticators(authenticators)
                .build()
        } catch (e: Exception) {
            Log.e("BiometricActivity", "onCreate: Unable to initialize prompt info", e)
            onAuthenticationError("Unable to initialize prompt info")
            return
        }
    }

    /**
     * Controls biometric events.
     */
    private val authenticationCallback = object : BiometricPrompt.AuthenticationCallback() {
        override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
            super.onAuthenticationError(errorCode, errString)
            this@BiometricActivity.onAuthenticationError(errString.toString())
        }

        override fun onAuthenticationFailed() {
            super.onAuthenticationFailed()
            this@BiometricActivity.onAuthenticationFailed()
        }

        override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
            super.onAuthenticationSucceeded(result)
            this@BiometricActivity.onAuthenticationSuccess()
        }
    }

    /**
     * Starts authentication
     */
    protected fun authenticate() {
        biometricPrompt.authenticate(promptInfo)
    }

    /**
     * Called whenever successful authentication.
     */
    abstract fun onAuthenticationSuccess()

    /**
     * Called whenever an error occurred during authentication
     *
     * @param message indicating the error.
     */
    abstract fun onAuthenticationError(message: String)

    /**
     * Fails authenticating.
     */
    abstract fun onAuthenticationFailed()


}