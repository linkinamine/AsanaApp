package com.mohamedelaminebenallouch.asana.rebel.di

import com.mohamedelaminebenallouch.asana.core.di.ActivityScope
import com.mohamedelaminebenallouch.asana.core.di.component.AppComponent
import com.mohamedelaminebenallouch.asana.rebel.details.DetailsActivity
import dagger.Component

@ActivityScope
@Component(dependencies = arrayOf(AppComponent::class), modules = arrayOf(DetailsActivityModule::class))
interface DetailsActivityComponent {

    fun inject(detailsActivity: DetailsActivity)
}