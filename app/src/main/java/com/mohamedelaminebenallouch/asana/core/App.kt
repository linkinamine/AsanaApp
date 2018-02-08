package com.mohamedelaminebenallouch.asana.core

import android.app.Application
import com.facebook.stetho.Stetho
import com.mohamedelaminebenallouch.asana.core.di.component.AppComponent
import com.mohamedelaminebenallouch.asana.core.di.component.DaggerAppComponent
import com.mohamedelaminebenallouch.asana.core.di.module.AppModule

class App : Application() {

    companion object {
        @JvmStatic lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        initDagger()
        Stetho.initializeWithDefaults(this)
    }

    private fun initDagger() {
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }
}