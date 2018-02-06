package com.mohamedelaminebenallouch.asana.core.mvp

interface Presenter<V : BaseView> {

    fun attachView(view: V)

    fun detachView()
}