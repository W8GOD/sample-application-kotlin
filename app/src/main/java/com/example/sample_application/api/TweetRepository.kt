package com.example.sample_application.api

import com.example.sample_application.api.entry.TweetBean
import com.example.sample_application.api.entry.UserBean

class TweetRepository {
    suspend fun fetchUser(): UserBean {
        return reqApi.user("James")
    }

    suspend fun fetchTweets(): List<TweetBean> {
        return reqApi.tweets("James")
    }
}
