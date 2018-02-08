package com.mohamedelaminebenallouch.asana.core.rx

import android.util.Log
import io.reactivex.observers.DisposableObserver

open class SafeObserver<T> : DisposableObserver<T>() {

    open fun onSafeNext(value: T) {}

    open fun onSafeError(e: Throwable) {}

    open fun onSafeCompleted() {}

    override fun onNext(value: T) {
        onSafeNext(value)
    }

    override fun onError(error: Throwable) {
        Log.e(LOG_TAG, "Observer error")
        onSafeError(error)
    }

    override fun onComplete() {
        onSafeCompleted()
    }

    companion object {
        private val LOG_TAG = "SafeObserver"
    }

}