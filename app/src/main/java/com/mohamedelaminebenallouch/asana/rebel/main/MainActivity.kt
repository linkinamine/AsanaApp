package com.mohamedelaminebenallouch.asana.rebel.main

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
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

    //MaterialSearchBar.OnSearchActionListener
    override fun onButtonClicked(buttonCode: Int) {}

    override fun onSearchStateChanged(enabled: Boolean) {}

    override fun onSearchConfirmed(text: CharSequence?) {
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

    private fun initSearchView() {
        with(search_view_msb) {
            setOnSearchActionListener(this@MainActivity)
            inflateMenu(R.menu.search_menu)
            menu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.stars -> {
                        //Add filtering, orderinghere
                        presenter.fetchRepos(text?.toString() ?: "")
                    }
                    R.id.forks -> {
                        //Add filtering, orderinghere
                        presenter.fetchRepos(text?.toString() ?: "")
                    }
                }
                false
            }
        }

    }

    @Subscribe
    fun onRowClicked(item: RepoItem) {
        val intent = Intent(this, DetailsActivity::class.java)
        // Pass data object in the bundle and populate details activity.
        intent.putExtra(DetailsActivity.EXTRA_REPO_ITEM, item)
        startActivity(intent)
        finish()
    }
}
