package com.mohamedelaminebenallouch.asana.core.di.component

import android.app.Application
import android.content.res.Resources
import com.base.util.AppSchedulerProvider
import com.google.gson.Gson
import com.mohamedelaminebenallouch.asana.core.di.module.ApiModule
import com.mohamedelaminebenallouch.asana.core.di.module.AppModule
import com.mohamedelaminebenallouch.asana.core.di.module.NetworkModule
import com.mohamedelaminebenallouch.asana.core.di.module.RetrofitModule
import com.mohamedelaminebenallouch.asana.rebel.api.GitHubEndpoints
import dagger.Component
import io.reactivex.disposables.CompositeDisposable
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class, RetrofitModule::class, ApiModule::class, NetworkModule::class))
interface AppComponent {
    fun application(): Application
    fun gson(): Gson
    fun resources(): Resources
    fun retrofit(): Retrofit
    fun githubEndpoints(): GitHubEndpoints
    fun cache(): Cache
    fun client(): OkHttpClient
    fun loggingInterceptor(): HttpLoggingInterceptor
    fun compositeDisposable(): CompositeDisposable
    fun schedulerProvider(): AppSchedulerProvider
}