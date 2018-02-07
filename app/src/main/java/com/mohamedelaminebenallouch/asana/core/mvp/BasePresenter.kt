package com.mohamedelaminebenallouch.asana.core.mvp

import com.mohamedelaminebenallouch.asana.core.schedulers.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import java.lang.ref.WeakReference

open class BasePresenter<V : BaseView> constructor(var disposable: CompositeDisposable, var scheduler: SchedulerProvider) : Presenter<V> {

    // We are using WeakReference because the Activity could be destroyed at any time
    // and we don't want to create a memory leaks
    private var weakReference: WeakReference<V>? = null

    override fun attachView(view: V) {
        if (!isViewAttached) {
            weakReference = WeakReference(view)
            view.setPresenter(this)
        }
    }

    override fun detachView() {
        weakReference?.clear()
        weakReference = null
        disposable.clear()
    }

    val view: V?
        get() = weakReference?.get()

    private val isViewAttached: Boolean
        get() = weakReference != null && weakReference!!.get() != null


}
