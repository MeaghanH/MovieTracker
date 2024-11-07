package com.cs211d.movietracker.ui.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.cs211d.movietracker.data.MovieUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MovieViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(MovieUiState())
    val uiState : StateFlow<MovieUiState> = _uiState.asStateFlow()

    private val movieList : MutableList<String> = mutableListOf()
    var currentMovie by mutableStateOf("")
        private set

    private var isError by mutableStateOf(false)


   fun userAddMovieTextField(movieName: String){
        currentMovie = movieName
        isError = false
    }

    fun userAddMovieButton() {
        if(currentMovie.isNotEmpty()) {
            movieList.add(currentMovie)
            isError = false
            resetMovie()
        }else {
            isError = true
        }
        updateUiState()
    }

   private fun updateUiState() {
        _uiState.update { currentState ->
            currentState.copy(
                isError = isError,
                movieList = movieList.toList()
            )
        }
    }

    private fun resetMovie() {
        currentMovie = ""
        isError = false
    }

    fun updateRecommendMovie(movie: String) {
        currentMovie = movie
    }
}