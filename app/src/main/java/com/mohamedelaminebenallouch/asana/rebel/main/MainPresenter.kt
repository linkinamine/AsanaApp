package com.mohamedelaminebenallouch.asana.rebel.main

import com.base.util.SchedulerProvider
import com.mohamedelaminebenallouch.asana.core.mvp.BasePresenter
import com.mohamedelaminebenallouch.asana.rebel.api.GitHubEndpoints
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class MainPresenter @Inject constructor(var api: GitHubEndpoints, disposable: CompositeDisposable, scheduler: SchedulerProvider) : BasePresenter<MainView>(disposable, scheduler) {
    //We can Use subscribeonIO
    fun getRepos(searchKey: String) {
        view?.showProgress()
        disposable.add(api.search(searchKey)
            .subscribeOn(scheduler.io())
            .observeOn(scheduler.ui())
            .subscribe(
                { result ->
                    view?.hideProgress()
                    view?.onSearchResponse(result?.items)

                    if (result.items == null || result.items.isEmpty()) {
                        view?.noResult()
                    }
                },
                { _ ->
                    view?.hideProgress()
                    view?.onError()
                })
        )
    }
}