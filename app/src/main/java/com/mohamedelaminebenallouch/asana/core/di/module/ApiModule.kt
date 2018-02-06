package com.mohamedelaminebenallouch.asana.core.di.module

import com.mohamedelaminebenallouch.asana.rebel.api.GitHubEndpoints
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class ApiModule {
    @Provides
    @Singleton
    fun providesEndpoints(retrofit: Retrofit): GitHubEndpoints = retrofit.create(GitHubEndpoints::class.java)
}