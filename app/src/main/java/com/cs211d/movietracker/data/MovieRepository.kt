package com.cs211d.movietracker.data

import android.content.Context
import kotlinx.coroutines.flow.Flow

interface MovieRepositoryInterface {
    suspend fun insertMovie(movie: Movie)
    fun getAllMovies(): Flow<List<Movie>>
    fun getRandomMovie(): Flow<Movie?>
}

class MovieRepository(
    context: Context //where the connection to the database comes from
) : MovieRepositoryInterface{

    private val database = MovieDatabase.getDatabase(context)
    private val movieDoa = database.movieDao()

    override suspend fun insertMovie(movie: Movie) = movieDoa.insertMovie(movie)

    override fun getAllMovies(): Flow<List<Movie>> = movieDoa.getAllMovies()
    override fun getRandomMovie(): Flow<Movie?> = movieDoa.getRandomMovie()
}