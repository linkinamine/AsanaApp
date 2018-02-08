package com.mohamedelaminebenallouch.asana.rebel.details

import android.view.View
import com.burakeregar.easiestgenericrecycleradapter.base.GenericViewHolder
import com.mohamedelaminebenallouch.asana.rebel.models.RepoOwner
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.subscribers_row.view.*
import org.greenrobot.eventbus.EventBus


class SubscribersViewHolder(itemView: View?) : GenericViewHolder<Any>(itemView) {
    private lateinit var item: RepoOwner
    override fun bindData(repoOwner: Any?) {
        item = repoOwner as RepoOwner
        with(itemView) {
            Picasso.with(context).load(item.avatarUrl).into(sr_repo_owner_iv)
            sr_repo_name_tv?.text = item.login
            sr_repo_url_tv?.text = item.htmlUrl
            sr_row_repo_cv.setOnClickListener {
                EventBus.getDefault().post(item.htmlUrl)
            }
        }
    }
}