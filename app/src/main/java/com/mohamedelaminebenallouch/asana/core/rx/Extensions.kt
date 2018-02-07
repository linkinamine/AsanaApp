package com.mohamedelaminebenallouch.asana.core.rx

import com.mohamedelaminebenallouch.asana.core.schedulers.SchedulerProvider
import io.reactivex.Observable
import io.reactivex.disposables.Disposable


fun <T1> subscribeOnIo(observable: Observable<T1>, onNext: (T1) -> Unit = {}, onError: (Throwable) -> Unit = {}, schedulerProvider: SchedulerProvider): Disposable {
    return observable
        .subscribeOn(schedulerProvider.io())
        .observeOn(schedulerProvider.ui())
        .subscribeWith(object : SafeObserver<T1>() {
            override fun onSafeNext(event: T1) {
                onNext.invoke(event)
            }

            override fun onSafeError(e: Throwable) {
                super.onSafeError(e)
                onError.invoke(e)
            }
        })
}