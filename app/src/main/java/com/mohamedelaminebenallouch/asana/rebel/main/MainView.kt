package com.mohamedelaminebenallouch.asana.rebel.main

import com.burakeregar.githubsearch.home.model.RepoItem
import com.mohamedelaminebenallouch.asana.core.mvp.BaseView

interface MainView : BaseView {
    fun onSearchResponse(list: List<RepoItem>?)
    fun showProgress()
    fun hideProgress()
    fun noResult()
}
