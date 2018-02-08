package com.mohamedelaminebenallouch.asana.rebel.models

import com.google.gson.annotations.SerializedName

/**
 * Created by Burak Eregar on 17.11.2017.
 * burakeregar@gmail.com
 * https://github.com/burakeregar
 */

data class RepoResponse(
		@SerializedName("total_count") val totalCount: Int,
		@SerializedName("incomplete_results") val incompleteResults: Boolean,
		@SerializedName("items") val items: List<RepoItem>?
)
