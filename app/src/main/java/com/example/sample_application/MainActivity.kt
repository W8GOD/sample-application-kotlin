package com.example.sample_application

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.sample_application.api.TweetRepository
import com.example.sample_application.ui.theme.SampleapplicationTheme
import com.example.sample_application.viewmodels.MainViewModel
import com.example.sample_application.viewmodels.MainViewModelFactory

class MainActivity : ComponentActivity() {

    //TODO Using data retrieved from the ViewModel to display in the views
    private val viewModel: MainViewModel by viewModels {
        val repository = TweetRepository()
        MainViewModelFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SampleapplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SampleapplicationTheme {
        Greeting("Android")
    }
}