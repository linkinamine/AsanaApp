package com.mohamedelaminebenallouch.asana.rebel.details

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.burakeregar.easiestgenericrecycleradapter.base.GenericAdapterBuilder
import com.burakeregar.easiestgenericrecycleradapter.base.GenericRecyclerAdapter
import com.mohamedelaminebenallouch.asana.R
import com.mohamedelaminebenallouch.asana.core.BaseActivity
import com.mohamedelaminebenallouch.asana.rebel.di.DaggerDetailsActivityComponent
import com.mohamedelaminebenallouch.asana.rebel.di.DetailsActivityModule
import com.mohamedelaminebenallouch.asana.rebel.models.RepoOwner
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_details.*
import org.greenrobot.eventbus.Subscribe
import javax.inject.Inject


class DetailsActivity : BaseActivity(), DetailsView {

    @Inject lateinit var presenter: DetailsPresenter

    private lateinit var subscribersAdapter: GenericRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        supportPostponeEnterTransition()

        initSubscribersAdapter()
        initDetailsView()
        initListeners()
    }

    //Dagger Injection
    override fun onActivityInject() {
        DaggerDetailsActivityComponent.builder().appComponent(getAppcomponent())
            .detailsActivityModule(DetailsActivityModule())
            .build()
            .inject(this)

        presenter.attachView(this)
    }

    //DetailsView implementation
    override fun onSearchResponse(list: List<RepoOwner>?) {
        subscribersAdapter.setList(list)
    }

    override fun showProgress() {
        subscribers_rv.visibility = View.GONE
        da_octo_cat_iv.visibility = View.VISIBLE
        da_octo_cat_tv.visibility = View.VISIBLE
        da_octo_cat_tv.text = getString(R.string.searching)
    }

    override fun hideProgress() {
        subscribers_rv.visibility = View.VISIBLE
        da_octo_cat_iv.visibility = View.GONE
        da_octo_cat_tv.visibility = View.GONE
    }

    override fun noResult() {
        subscribers_rv.visibility = View.GONE
        da_octo_cat_iv.visibility = View.VISIBLE
        da_octo_cat_tv.visibility = View.VISIBLE
        da_octo_cat_tv.text = getString(R.string.no_results)
    }

    override fun onError() {
        subscribers_rv.visibility = View.GONE
        da_octo_cat_iv.visibility = View.VISIBLE
        da_octo_cat_tv.visibility = View.VISIBLE
        da_octo_cat_tv.text = getString(R.string.error)
    }

    //Private methods
    private fun initListeners() {
        close_iv.setOnClickListener { supportFinishAfterTransition() }
    }

    private fun initDetailsView() {
        val repoName = intent.extras.getString(EXTRA_REPO_ITEM_NAME) ?: getString(R.string.error_value)
        val repoOwnerName = intent.extras.getString(EXTRA_REPO_ITEM_OWNER_NAME) ?: getString(R.string.error_value)
        val repoOwnerAvatar = intent.extras.getString(EXTRA_REPO_ITEM_OWNER_AVATAR) ?: getString(R.string.error_value)
        val subscribersUrl = intent.extras.getString(EXTRA_REPO_ITEM_OWNER_SUBSCRIBERS) ?: getString(R.string.error_value)
        val subscribersCount = intent.extras.getInt(EXTRA_REPO_ITEM_REPO_SUBSCRIBERS_COUNT)

        da_repo_name_tv.text = getString(R.string.repo_name, repoName, subscribersCount)
        da_repo_owner_name_tv.text = repoOwnerName

        presenter.fetchSubscribers(subscribersUrl)

        loadOwnerImage(repoOwnerAvatar)
    }

    private fun loadOwnerImage(repoOwnerAvatar: String?) {
        Picasso.with(this)
            .load(repoOwnerAvatar)
            .fit()
            .noFade()
            .centerCrop()
            .into(da_repo_owner_iv, object : Callback {
                override fun onSuccess() {
                    supportStartPostponedEnterTransition()
                }

                override fun onError() {
                    supportStartPostponedEnterTransition()
                }
            })

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            da_repo_owner_iv.transitionName = TRANSITION_NAME
        }
    }

    private fun initSubscribersAdapter() {
        subscribers_rv.visibility = View.VISIBLE
        subscribersAdapter = GenericAdapterBuilder().addModel(
            R.layout.subscribers_row,
            SubscribersViewHolder::class.java,
            RepoOwner::class.java).execute()

        with(subscribers_rv) {
            layoutManager = LinearLayoutManager(this@DetailsActivity)
            adapter = subscribersAdapter
        }
    }


    @Subscribe
    fun onRowClicked(repoOwnerUrl: String) {
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(repoOwnerUrl)
        startActivity(i)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        supportFinishAfterTransition()
    }

    companion object {
        val EXTRA_REPO_ITEM_OWNER_NAME = "EXTRA_REPO_ITEM_OWNER_NAME"
        val EXTRA_REPO_ITEM_OWNER_AVATAR = "EXTRA_REPO_ITEM_OWNER_AVATAR"
        val EXTRA_REPO_ITEM_OWNER_SUBSCRIBERS = "EXTRA_REPO_ITEM_OWNER_SUBSCRIBERS"
        val EXTRA_REPO_ITEM_REPO_SUBSCRIBERS_COUNT = "EXTRA_REPO_ITEM_REPO_SUBSCRIBERS_COUNT"
        val EXTRA_REPO_ITEM_NAME = "EXTRA_REPO_ITEM_NAME"
        val TRANSITION_NAME = "profile"
    }
}