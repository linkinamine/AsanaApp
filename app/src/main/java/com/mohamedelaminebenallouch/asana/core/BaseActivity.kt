package com.mohamedelaminebenallouch.asana.core

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.mohamedelaminebenallouch.asana.core.di.component.AppComponent
import com.mohamedelaminebenallouch.asana.core.events.BaseEvent
import com.mohamedelaminebenallouch.asana.core.mvp.BasePresenter
import com.mohamedelaminebenallouch.asana.core.mvp.BaseView
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.jetbrains.anko.toast

abstract class BaseActivity : AppCompatActivity(), BaseView {

    private var presenter: BasePresenter<*>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onActivityInject()
    }

    protected abstract fun onActivityInject()

    fun getAppcomponent(): AppComponent = Application.appComponent

    override fun setPresenter(presenter: BasePresenter<*>) {
        this.presenter = presenter
    }

    override fun onError() {
        toast("Something went wrong")
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.detachView()
        presenter = null
    }

    @Subscribe
    fun defaultSubscribe(event: BaseEvent) {
    }
}
