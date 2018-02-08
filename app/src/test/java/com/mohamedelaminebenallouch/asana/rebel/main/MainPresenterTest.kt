package com.mohamedelaminebenallouch.asana.rebel.main

import com.mohamedelaminebenallouch.asana.rebel.api.GitHubEndpoints
import com.mohamedelaminebenallouch.asana.rebel.models.RepoResponse
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

class MainPresenterTest {

    private val view: MainView = mock()
    private val api: GitHubEndpoints = mock()
    private lateinit var presenter: MainPresenter
    private lateinit var testScheduler: TestScheduler

    @Before
    fun setUp() {
        val compositeDisposable = CompositeDisposable()
        testScheduler = TestScheduler()
        val testSchedulerProvider = TestSchedulerProvider(testScheduler)
        presenter = MainPresenter(api, compositeDisposable, testSchedulerProvider)
        presenter.attachView(view)
    }

    @Test
    fun test_fetchRepos_OnSuccess() {
        val mockedResponse: RepoResponse = mock()
        val searchKey = "test search key"

        doReturn(Observable.just(mockedResponse))
            .`when`(api)
            .search(searchKey)

        presenter.fetchRepos(searchKey)

        testScheduler.triggerActions()

        verify(view).showProgress()
        verify(view).onSearchResponse(mockedResponse.items)
        verify(view).hideProgress()
    }

    @Test
    fun test_fetchRepos_OnNoResult() {
        val mockedResponse: RepoResponse = mock()
        val searchKey = "test search key"

        doReturn(Observable.just(mockedResponse))
            .`when`(api)
            .search(searchKey)

        presenter.fetchRepos(searchKey)

        testScheduler.triggerActions()

        verify(view).noResult()
    }

    @Test
    fun test_fetchRepos_Error() {
        val mockedResponse: Throwable = mock()
        val searchKey = "test"

        doReturn(Observable.just(mockedResponse))
            .`when`(api)
            .search(searchKey)

        presenter.fetchRepos(searchKey)

        testScheduler.triggerActions()

        verify(view).showProgress()
        verify(view).hideProgress()
        verify(view).onError()
    }

    @After
    fun tearDown() {
        presenter.detachView()
    }

}