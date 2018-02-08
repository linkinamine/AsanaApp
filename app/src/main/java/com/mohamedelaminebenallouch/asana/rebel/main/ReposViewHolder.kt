package com.mohamedelaminebenallouch.asana.rebel.main

import android.view.View
import com.burakeregar.easiestgenericrecycleradapter.base.GenericViewHolder
import com.mohamedelaminebenallouch.asana.rebel.models.RepoItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.repo_row.view.*
import org.greenrobot.eventbus.EventBus


class ReposViewHolder(itemView: View?) : GenericViewHolder<Any>(itemView) {
    private lateinit var item: RepoItem
    override fun bindData(repoItem: Any?) {
        item = repoItem as RepoItem
        with(itemView) {
            Picasso.with(context).load(item.owner?.avatarUrl).into(repo_owner_iv)
            repo_name_tv?.text = item.name
            repo_description_tv?.text = item.description
            repo_forks_tv?.text = item.forks.toString()
            repo_stars_tv?.text = item.stargazersCount.toString()
            row_repo_cv.setOnClickListener {
                EventBus.getDefault().post(Pair(item, itemView.repo_owner_iv))
            }
        }
    }
}