package com.mohamedelaminebenallouch.asana.core.mvp

interface BaseView {
    fun onError()
    fun setPresenter(presenter: BasePresenter<*>)
}