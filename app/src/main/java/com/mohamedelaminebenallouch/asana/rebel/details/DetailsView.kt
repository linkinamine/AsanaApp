package com.mohamedelaminebenallouch.asana.rebel.details

import com.mohamedelaminebenallouch.asana.core.mvp.BaseView
import com.mohamedelaminebenallouch.asana.rebel.models.RepoOwner

interface DetailsView : BaseView {
    fun onSearchResponse(list: List<RepoOwner>?)
    fun showProgress()
    fun hideProgress()
    fun noResult()
}