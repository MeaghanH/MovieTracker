package com.cs211d.movietracker.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.cs211d.movietracker.MovieApplication
import com.cs211d.movietracker.data.Movie
import com.cs211d.movietracker.data.MovieRepositoryInterface
import com.cs211d.movietracker.data.MovieUiState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MovieViewModel(
    private  val movieRepository : MovieRepositoryInterface
): ViewModel() {

    val movieUiState: StateFlow<MovieUiState> =
        movieRepository.getAllMovies().map {
            MovieUiState(it)
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = MovieUiState()
            )

    var currentMovie by mutableStateOf("")
        private set

    var isError by mutableStateOf(false)
        private set

    fun userAddMovieTextField(movieName: String) {
        currentMovie = movieName
        isError = false
    }

    fun userAddMovieButton() {
        if (currentMovie.isNotEmpty()) {
           addMovie(Movie(movieName = currentMovie))
            isError = false
            resetMovie()
        } else {
            isError = true
        }
    }

private fun addMovie(movie: Movie) {
    viewModelScope.launch {
        movieRepository.insertMovie(movie)
    }
}

    private fun resetMovie() {
        currentMovie = ""
        isError = false
    }

    companion object {
        val Factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as MovieApplication)
                MovieViewModel(application.movieRepository)
            }
        }
    }
}
