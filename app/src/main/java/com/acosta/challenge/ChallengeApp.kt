package com.acosta.challenge

import android.app.Application
import com.acosta.challenge.di.AppModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.ksp.generated.*

class ChallengeApp: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@ChallengeApp)
            modules(AppModule().module)
        }
    }
}