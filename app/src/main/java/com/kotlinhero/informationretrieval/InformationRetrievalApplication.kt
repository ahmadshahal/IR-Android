package com.kotlinhero.informationretrieval

import android.app.Application
import com.kotlinhero.informationretrieval.di.MainModule
import com.kotlinhero.informationretrieval.di.NetworkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import timber.log.Timber

class InformationRetrievalApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        startKoin {
            androidLogger()
            androidContext(this@InformationRetrievalApplication)
            modules(
                NetworkModule,
                MainModule
            )
        }
    }
}