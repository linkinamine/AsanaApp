package com.mohamedelaminebenallouch.asana.core

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco
import com.mohamedelaminebenallouch.asana.core.di.component.AppComponent
import com.mohamedelaminebenallouch.asana.core.di.module.AppModule

class Application : Application() {

    companion object {
        @JvmStatic lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        initDagger()
        Fresco.initialize(this)
    }

    private fun initDagger() {
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }
}