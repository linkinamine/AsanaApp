package com.mohamedelaminebenallouch.asana.rebel.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import com.burakeregar.easiestgenericrecycleradapter.base.GenericAdapterBuilder
import com.burakeregar.easiestgenericrecycleradapter.base.GenericRecyclerAdapter
import com.mancj.materialsearchbar.MaterialSearchBar
import com.mohamedelaminebenallouch.asana.R
import com.mohamedelaminebenallouch.asana.core.BaseActivity
import com.mohamedelaminebenallouch.asana.rebel.details.DetailsActivity
import com.mohamedelaminebenallouch.asana.rebel.di.DaggerMainActivityComponent
import com.mohamedelaminebenallouch.asana.rebel.di.MainActivityModule
import com.mohamedelaminebenallouch.asana.rebel.models.RepoItem
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.Subscribe
import javax.inject.Inject


class MainActivity : BaseActivity(), MainView, MaterialSearchBar.OnSearchActionListener {


    @Inject lateinit var presenter: MainPresenter

    private lateinit var reposAdapter: GenericRecyclerAdapter

    private var repositories: List<RepoItem>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initReposAdapter()
        initSearchView()
    }

    //Dagger Injection
    override fun onActivityInject() {
        DaggerMainActivityComponent.builder().appComponent(getAppcomponent())
            .mainActivityModule(MainActivityModule())
            .build()
            .inject(this)

        presenter.attachView(this)
    }

    //MainView implementation
    override fun onSearchResponse(repos: List<RepoItem>?) {
        reposAdapter.setList(repos)
        repositories = repos
    }

    override fun showProgress() {
        repos_rv.visibility = View.GONE
        octo_cat_iv.visibility = View.VISIBLE
        octo_cat_tv.visibility = View.VISIBLE
        octo_cat_tv.text = getString(R.string.searching)

    }

    override fun hideProgress() {
        repos_rv.visibility = View.VISIBLE
        octo_cat_iv.visibility = View.GONE
        octo_cat_tv.visibility = View.GONE
    }

    override fun noResult() {
        repos_rv.visibility = View.GONE
        octo_cat_iv.visibility = View.VISIBLE
        octo_cat_tv.visibility = View.VISIBLE
        octo_cat_tv.text = getString(R.string.no_results)
    }


    override fun onError() {
        repos_rv.visibility = View.GONE
        octo_cat_iv.visibility = View.VISIBLE
        octo_cat_tv.visibility = View.VISIBLE
        octo_cat_tv.text = getString(R.string.error)
    }

    //MaterialSearchBar.OnSearchActionListener
    override fun onButtonClicked(buttonCode: Int) {}

    override fun onSearchStateChanged(enabled: Boolean) {}

    override fun onSearchConfirmed(text: CharSequence?) {
        hideKeyboard()
        presenter.fetchRepos(text?.toString() ?: "")
    }

    //Private methods
    private fun initReposAdapter() {
        repos_rv.visibility = View.VISIBLE
        reposAdapter = GenericAdapterBuilder().addModel(
            R.layout.repo_row,
            ReposViewHolder::class.java,
            RepoItem::class.java).execute()

        with(repos_rv) {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = reposAdapter
        }
    }

    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(search_view_msb.windowToken, 0)
    }

    private fun initSearchView() {
        with(search_view_msb) {
            setOnSearchActionListener(this@MainActivity)
            inflateMenu(R.menu.search_menu)
            menu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.stars -> {
                        val sortedRepos = repositories?.let { it.sortedByDescending { it.stargazersCount } }
                        reposAdapter.setList(sortedRepos)
                    }
                    R.id.forks -> {
                        val sortedRepos = repositories?.let { it.sortedByDescending { it.forksCount } }
                        reposAdapter.setList(sortedRepos)
                    }
                }
                false
            }
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    @Subscribe
    fun onRowClicked(repoItemView: Pair<RepoItem, ImageView>) {
        val intent = Intent(this@MainActivity, DetailsActivity::class.java)
        // Pass data object in the bundle and populate details activity.
        intent.putExtra(DetailsActivity.EXTRA_REPO_ITEM_NAME, repoItemView.first.name)
        intent.putExtra(DetailsActivity.EXTRA_REPO_ITEM_OWNER_NAME, repoItemView.first.owner?.login)
        intent.putExtra(DetailsActivity.EXTRA_REPO_ITEM_OWNER_AVATAR, repoItemView.first.owner?.avatarUrl)
        intent.putExtra(DetailsActivity.EXTRA_REPO_ITEM_OWNER_SUBSCRIBERS, repoItemView.first.subscribersUrl)
        intent.putExtra(DetailsActivity.EXTRA_REPO_ITEM_REPO_SUBSCRIBERS_COUNT, repoItemView.first.watchersCount)

        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this@MainActivity, repoItemView.second,
            DetailsActivity.TRANSITION_NAME)

        startActivity(intent, options.toBundle())
    }

    companion object {
        val FETCH_BY_STARS = "FETCH_BY_STARS"
        val FETCH_BY_FORKS = "FETCH_BY_FORKS"

    }
}
