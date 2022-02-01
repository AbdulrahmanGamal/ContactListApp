package com.abdulrahman.contactlistapp.core

import android.app.Application
import com.abdulrahman.contactlistapp.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ContactListApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin()
    }

    private fun startKoin() {
        startKoin {
            androidContext(this@ContactListApp)
            // declare modules to use
            modules(listOf(
                WebservicesModule.get,
                dbModule,
                repositoryModule,
                useCasesModule,
                mappersModule,
                viewModelsModule,
            ))
        }
    }
}