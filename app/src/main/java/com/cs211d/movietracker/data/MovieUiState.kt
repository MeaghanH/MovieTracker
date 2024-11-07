package com.cs211d.movietracker.data

data class MovieUiState(
    val isError: Boolean = false,
    val movieList: List<String> = listOf()
)
