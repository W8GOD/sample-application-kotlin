package com.example.sample_application.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sample_application.api.TweetRepository
import com.example.sample_application.api.entry.TweetBean

private const val PAGE_TWEET_COUNT = 5

class MainViewModel(private val repository: TweetRepository) : ViewModel() {

    private var allTweets: List<TweetBean>? = null

    private val _tweets = MutableLiveData<List<TweetBean>?>()
    val tweets: LiveData<List<TweetBean>?> get() = _tweets

    private fun loadTweets() {
        val result = try {
            listOf<TweetBean>()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
        allTweets = result
    }

    fun refreshTweets() {
        loadTweets()
    }
}