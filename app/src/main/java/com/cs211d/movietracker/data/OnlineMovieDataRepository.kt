package com.cs211d.movietracker.data

import com.cs211d.movietracker.network.OnlineMovieApiService

interface OnlineMovieRepositoryInterface{

    suspend fun getOnlineMovieData(movieTitle: String) : OnlineMovieData
}

class OnlineMovieDataRepository( private val onlineMovieApiService : OnlineMovieApiService)
    : OnlineMovieRepositoryInterface {

    override suspend fun getOnlineMovieData(movieTitle : String) : OnlineMovieData =
       onlineMovieApiService.getOnlineMovieData(movieTitle)
}