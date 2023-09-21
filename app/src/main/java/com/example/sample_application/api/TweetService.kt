package com.example.sample_application.api

import com.example.sample_application.api.entry.TweetBean
import com.example.sample_application.api.entry.UserBean
import retrofit2.http.GET
import retrofit2.http.Path

interface TweetService {

    @GET("user/{name}")
    suspend fun user(@Path("name") user: String): UserBean

    @GET("user/{name}/tweets")
    suspend fun tweets(@Path("name") user: String): List<TweetBean>
}
