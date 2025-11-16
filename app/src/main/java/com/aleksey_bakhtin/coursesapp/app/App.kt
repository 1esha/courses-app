package com.aleksey_bakhtin.coursesapp.app

import android.app.Application
import com.aleksey_bakhtin.coursesapp.di.dataModule
import com.aleksey_bakhtin.coursesapp.di.domainModule
import com.aleksey_bakhtin.coursesapp.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class App: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(
                modules = listOf(
                    presentationModule,
                    domainModule,
                    dataModule
                )
            )
        }
    }
}