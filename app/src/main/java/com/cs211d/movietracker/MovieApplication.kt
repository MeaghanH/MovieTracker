package com.cs211d.movietracker

import android.app.Application
import com.cs211d.movietracker.data.MovieRepository
import com.cs211d.movietracker.data.MovieRepositoryInterface
import com.cs211d.movietracker.data.OnlineMovieDataRepository
import com.cs211d.movietracker.network.OnlineMovieApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

class MovieApplication : Application() {
    lateinit var movieRepository: MovieRepositoryInterface

    override fun onCreate() {
        super.onCreate()
        movieRepository = MovieRepository(this.applicationContext)
    }

    private val BASE_URL = "https://www.omdbapi.com/"

    private val json = Json { ignoreUnknownKeys = true }

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(BASE_URL)
        .build()

    private val retrofitService : OnlineMovieApiService by lazy {
        retrofit.create(OnlineMovieApiService::class.java)
    }

    val onlineMovieDataRepository : OnlineMovieDataRepository by lazy {
        OnlineMovieDataRepository(retrofitService)
    }
}