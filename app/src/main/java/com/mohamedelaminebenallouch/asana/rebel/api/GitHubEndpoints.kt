package com.mohamedelaminebenallouch.asana.rebel.api

import com.mohamedelaminebenallouch.asana.rebel.models.RepoResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubEndpoints {
    @GET("/search/repositories?sort=stars&order=desc")
    fun search(@Query("q") key: String): Observable<RepoResponse>
}