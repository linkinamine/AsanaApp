package com.mohamedelaminebenallouch.asana.rebel.details

import com.mohamedelaminebenallouch.asana.core.mvp.BasePresenter
import com.mohamedelaminebenallouch.asana.core.rx.subscribeOnIo
import com.mohamedelaminebenallouch.asana.core.schedulers.SchedulerProvider
import com.mohamedelaminebenallouch.asana.rebel.api.GitHubEndpoints
import com.mohamedelaminebenallouch.asana.rebel.models.RepoOwner
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject


class DetailsPresenter @Inject constructor(var api: GitHubEndpoints, disposable: CompositeDisposable, scheduler: SchedulerProvider)
    : BasePresenter<DetailsView>(disposable, scheduler) {

    fun fetchSubscribers(subscribersUrl: String) {
        view?.showProgress()
        val observable = api.fetchSubscribers(subscribersUrl)
        val subscription = subscribeOnIo(observable, { onSearchSuccess(it) }, { onSearchFailure() }, scheduler)
        disposable.add(subscription)
    }

    private fun onSearchSuccess(result: List<RepoOwner>) {
        view?.hideProgress()
        view?.onSearchResponse(result)

        if (result == null || result.isEmpty()) {
            view?.noResult()
        }

    }

    private fun onSearchFailure() {
        view?.hideProgress()
    }
}