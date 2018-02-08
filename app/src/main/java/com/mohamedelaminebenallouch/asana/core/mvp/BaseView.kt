package com.mohamedelaminebenallouch.asana.core.mvp

interface BaseView {
    fun setPresenter(presenter: BasePresenter<*>)
}