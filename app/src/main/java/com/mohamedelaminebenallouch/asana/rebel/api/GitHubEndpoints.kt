package com.mohamedelaminebenallouch.asana.rebel.api

import com.mohamedelaminebenallouch.asana.rebel.models.RepoOwner
import com.mohamedelaminebenallouch.asana.rebel.models.RepoResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface GitHubEndpoints {

    @GET("/search/repositories?sort=forks&order=desc")
    fun search(@Query("q") key: String): Observable<RepoResponse>

    @GET
    fun fetchSubscribers(@Url url: String): Observable<List<RepoOwner>>
}