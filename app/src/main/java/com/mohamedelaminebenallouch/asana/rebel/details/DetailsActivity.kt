package com.mohamedelaminebenallouch.asana.rebel.details

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.mohamedelaminebenallouch.asana.R
import com.mohamedelaminebenallouch.asana.rebel.models.RepoItem

class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val repo = intent.extras.getParcelable<RepoItem>(EXTRA_REPO_ITEM)
        Log.d("DetailsActivity", repo.owner?.avatarUrl + " " + repo.name + " " + repo.watchersCount + " "
            + repo.subscribersUrl)

    }

    companion object {
        val EXTRA_REPO_ITEM = "EXTRA_REPO_ITEM"
    }
}