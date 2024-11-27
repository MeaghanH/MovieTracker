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
import com.cs211d.movietracker.data.OnlineMovieData
import com.cs211d.movietracker.data.OnlineMovieDataRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

enum class OnlineDataStatusState {
    SUCCESS, LOADING, ERROR
}

data class OnlineMovieUiState(
    val recommendedMovie: Movie = Movie(movieName = ""),
    val onlineMovieData: OnlineMovieData? = null
)

class RecViewModel(
    private val onlineMovieDataRepository: OnlineMovieDataRepository
) : ViewModel() {

    private val _onlineMovieUiState = MutableStateFlow(OnlineMovieUiState())
    var onlineMovieUiState : StateFlow<OnlineMovieUiState> = _onlineMovieUiState.asStateFlow()

    private var currentMovie by mutableStateOf("")

    var onlineDataStatusState : OnlineDataStatusState by mutableStateOf(OnlineDataStatusState.LOADING)


    fun updateRecommendMovie(movie: String) {
        currentMovie = movie
    }

    fun getRecommendedMovie() : String {
        return currentMovie
    }

    fun getOnlineMovieData() {
        viewModelScope.launch {
            try{
                val title = currentMovie
                val onlineMovieData = onlineMovieDataRepository.getOnlineMovieData(title)
                if(onlineMovieData == null) {
                    updateUiState(onlineMovieData = null)
                    onlineDataStatusState = OnlineDataStatusState.ERROR
                }else {
                    updateUiState(onlineMovieData = onlineMovieData)
                    onlineDataStatusState = OnlineDataStatusState.SUCCESS
                }
            } catch (e : Exception) {
                onlineDataStatusState = OnlineDataStatusState.ERROR
            }
        }
    }

    private fun updateUiState(
        onlineMovieData : OnlineMovieData? = _onlineMovieUiState.value.onlineMovieData
    ) {
        _onlineMovieUiState.update { currentState ->
            currentState.copy(
                onlineMovieData = onlineMovieData
            )
        }
    }

    companion object {
        val Factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as MovieApplication)
                val onlineMovieDataRepository = application.onlineMovieDataRepository
                RecViewModel(onlineMovieDataRepository)
            }
        }
    }
}