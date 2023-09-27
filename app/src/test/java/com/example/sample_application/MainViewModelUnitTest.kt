package com.example.sample_application

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.sample_application.api.TweetRepository
import com.example.sample_application.api.entry.TweetBean
import com.example.sample_application.viewmodels.MainViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import java.util.concurrent.Executors


@ExperimentalCoroutinesApi
class MainViewModelUnitTest {

    @Rule
    @JvmField
    var rule: TestRule = InstantTaskExecutorRule()

    private val mainThreadSurrogate = Executors.newSingleThreadExecutor().asCoroutineDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun test_fetch_tweets() {
        // Given
        val tweetRepository = mockk<TweetRepository>()
        val testDispatcher = TestCoroutineDispatcher()

        coEvery {
            tweetRepository.fetchTweets()
        } returns listOf(TweetBean("Test Tweet"))

        Dispatchers.setMain(testDispatcher)

        val mainViewModel = MainViewModel(tweetRepository)
        val observer = mockk<Observer<List<TweetBean>?>>(relaxed = true)
        mainViewModel.tweets.observeForever(observer)

        // When
        mainViewModel.refreshTweets()
        testDispatcher.advanceUntilIdle()

        // Then
        val expectedTweets = listOf(TweetBean("Test Tweet"))
        coVerify {
            observer.onChanged(expectedTweets)
        }
        coVerify(exactly = 1) {
            tweetRepository.fetchTweets()
        }
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }
}