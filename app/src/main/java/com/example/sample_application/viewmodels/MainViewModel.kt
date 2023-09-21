package com.example.sample_application.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sample_application.api.TweetRepository
import com.example.sample_application.api.entry.TweetBean
import com.example.sample_application.api.entry.UserBean
import kotlinx.coroutines.launch

private const val PAGE_TWEET_COUNT = 5

class MainViewModel(private val repository: TweetRepository) : ViewModel() {

    val userBean: MutableLiveData<UserBean> by lazy {
        MutableLiveData<UserBean>().also { loadUserInfo() }
    }

    val tweets: MutableLiveData<List<TweetBean>> by lazy {
        MutableLiveData<List<TweetBean>>().also { loadTweets() }
    }

    private var allTweets: List<TweetBean>? = null

    private fun loadUserInfo() {
        viewModelScope.launch {
            val result = try {
                repository.fetchUser()
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
            userBean.value = result
        }
    }


    private fun loadTweets() {
        viewModelScope.launch {
            val result = try {
                repository.fetchTweets()
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }

            allTweets = result

            if ((allTweets?.size ?: 0) > PAGE_TWEET_COUNT) {
                tweets.value = allTweets?.subList(0, PAGE_TWEET_COUNT)
            } else {
                tweets.value = allTweets
            }
        }
    }

    fun refreshTweets() {
        loadTweets()
    }
}