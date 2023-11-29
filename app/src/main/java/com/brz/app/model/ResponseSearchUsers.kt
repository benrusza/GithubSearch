package com.brz.app.model

data class ResponseSearchUsers(
    val incomplete_results: Boolean = false,
    val items: List<GitHubUser> = emptyList(),
    val total_count: Int = 0
)