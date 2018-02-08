package com.mohamedelaminebenallouch.asana.rebel.di

import com.mohamedelaminebenallouch.asana.core.di.ActivityScope
import com.mohamedelaminebenallouch.asana.core.di.component.AppComponent
import com.mohamedelaminebenallouch.asana.rebel.main.MainActivity
import dagger.Component

@ActivityScope
@Component(dependencies = arrayOf(AppComponent::class), modules = arrayOf(MainActivityModule::class))
interface MainActivityComponent {

    fun inject(mainActivity: MainActivity)
}
