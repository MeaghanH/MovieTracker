package com.cs211d.movietracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.cs211d.movietracker.ui.theme.theme.MovieTrackerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieTrackerTheme {
                MovieScreenApp()
            }
        }
    }
}
