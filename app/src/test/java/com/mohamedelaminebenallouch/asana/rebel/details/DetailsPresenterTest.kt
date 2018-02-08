package com.mohamedelaminebenallouch.asana.rebel.details

import com.mohamedelaminebenallouch.asana.rebel.api.GitHubEndpoints
import com.mohamedelaminebenallouch.asana.rebel.models.RepoOwner
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.util.TestSchedulerProvider
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.TestScheduler
import org.junit.After
import org.junit.Before
import org.junit.Test


class DetailsPresenterTest {
    private val view: DetailsView = mock()
    private val api: GitHubEndpoints = mock()
    private lateinit var presenter: DetailsPresenter
    private lateinit var testScheduler: TestScheduler

    @Before
    fun setUp() {
        val compositeDisposable = CompositeDisposable()
        testScheduler = TestScheduler()
        val testSchedulerProvider = TestSchedulerProvider(testScheduler)
        presenter = DetailsPresenter(api, compositeDisposable, testSchedulerProvider)
        presenter.attachView(view)
    }

    @Test
    fun test_fetchSubscribers_OnSuccess() {
        val mockedResponse: List<RepoOwner> = mock()
        val url = "url"

        doReturn(Observable.just(mockedResponse))
            .`when`(api)
            .fetchSubscribers(url)

        presenter.fetchSubscribers(url)

        testScheduler.triggerActions()

        verify(view).showProgress()
        verify(view).onSearchResponse(mockedResponse)
        verify(view).hideProgress()
    }

    @Test
    fun test_fetchSubscribers_onError() {
        val mockedResponse: Throwable = mock()
        val url = "url"

        doReturn(Observable.just(mockedResponse))
            .`when`(api)
            .fetchSubscribers(url)

        presenter.fetchSubscribers(url)

        testScheduler.triggerActions()

        verify(view).showProgress()
        verify(view).hideProgress()
        verify(view).onError()
    }

    @After
    fun tearDown() {
        presenter?.detachView()
    }

}