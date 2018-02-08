package com.mohamedelaminebenallouch.asana.rebel.main

import com.mohamedelaminebenallouch.asana.core.mvp.BasePresenter
import com.mohamedelaminebenallouch.asana.core.schedulers.SchedulerProvider
import com.mohamedelaminebenallouch.asana.rebel.api.GitHubEndpoints
import com.mohamedelaminebenallouch.asana.rebel.models.RepoResponse
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class MainPresenter @Inject constructor(var api: GitHubEndpoints, disposable: CompositeDisposable, scheduler: SchedulerProvider) : BasePresenter<MainView>(disposable, scheduler) {
    
    fun fetchRepos(searchTerm: String) {
        view?.showProgress()
        val observable = api.search(searchTerm)

        val subscription = observable.subscribeOn(scheduler.io()).
            observeOn(scheduler.ui()).
            subscribe({ onSearchSuccess(it) }, { onSearchFailure() })

        disposable.add(subscription)
    }

    private fun onSearchSuccess(result: RepoResponse) {
        view?.hideProgress()
        view?.onSearchResponse(result.items)

        if (result.items == null || result.items.isEmpty()) {
            view?.noResult()
        }

    }

    private fun onSearchFailure() {
        view?.hideProgress()
        view?.onError()
    }
}