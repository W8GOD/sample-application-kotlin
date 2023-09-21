package com.example.sample_application

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.sample_application.api.TweetRepository
import com.example.sample_application.viewmodels.MainViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*
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
        //Given
        val tweetRepository = mockk<TweetRepository>()
        coEvery {
            tweetRepository.fetchTweets()
        } returns listOf()

        val mainViewModel = MainViewModel(tweetRepository)

        //When
        mainViewModel.refreshTweets()

        //Then
        Assert.assertEquals(0, mainViewModel.tweets.value?.size)
    }
}