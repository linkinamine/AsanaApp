package com.mohamedelaminebenallouch.asana.rebel.main

import com.mohamedelaminebenallouch.asana.core.mvp.BaseView
import com.mohamedelaminebenallouch.asana.rebel.models.RepoItem

interface MainView : BaseView {
    fun onSearchResponse(list: List<RepoItem>?)
    fun showProgress()
    fun hideProgress()
    fun noResult()
}
