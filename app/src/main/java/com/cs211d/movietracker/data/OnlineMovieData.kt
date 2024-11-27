package com.cs211d.movietracker.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OnlineMovieData (
    @SerialName("imdbID") val imdbID: String? = null,
    @SerialName("Title") val title: String,
    @SerialName("Released") val released: String? = null,
    @SerialName("Director") val director: String? = null,
)