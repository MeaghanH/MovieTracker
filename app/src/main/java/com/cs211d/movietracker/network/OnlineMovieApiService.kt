package com.cs211d.movietracker.network


import com.cs211d.movietracker.data.OnlineMovieData
import retrofit2.http.GET
import retrofit2.http.Query

interface OnlineMovieApiService {

    @GET("?apikey=e9b8137d&")
    suspend fun getOnlineMovieData(
        @Query("t") title: String
    ) : OnlineMovieData

}