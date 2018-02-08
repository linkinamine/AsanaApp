package com.mohamedelaminebenallouch.asana.rebel.di

import com.mohamedelaminebenallouch.asana.core.di.ActivityScope
import com.mohamedelaminebenallouch.asana.core.schedulers.AppSchedulerProvider
import com.mohamedelaminebenallouch.asana.rebel.api.GitHubEndpoints
import com.mohamedelaminebenallouch.asana.rebel.details.DetailsPresenter
import com.mohamedelaminebenallouch.asana.rebel.main.MainPresenter
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
class DetailsActivityModule {

    @Provides
    @ActivityScope
    internal fun providesDetailsPresenter(api: GitHubEndpoints, disposable: CompositeDisposable, scheduler: AppSchedulerProvider): DetailsPresenter =
        DetailsPresenter(api, disposable, scheduler)
}