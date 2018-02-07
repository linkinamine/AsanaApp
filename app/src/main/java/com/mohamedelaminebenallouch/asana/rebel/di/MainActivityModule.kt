package com.mohamedelaminebenallouch.asana.rebel.di

import com.mohamedelaminebenallouch.asana.core.di.ActivityScope
import com.mohamedelaminebenallouch.asana.core.schedulers.AppSchedulerProvider
import com.mohamedelaminebenallouch.asana.rebel.api.GitHubEndpoints
import com.mohamedelaminebenallouch.asana.rebel.main.MainPresenter
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
class MainActivityModule {

    @Provides
    @ActivityScope
    internal fun providesMainPresenter(api: GitHubEndpoints, disposable: CompositeDisposable, scheduler: AppSchedulerProvider): MainPresenter =
        MainPresenter(api, disposable, scheduler)
}
