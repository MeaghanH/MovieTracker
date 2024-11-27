package com.cs211d.movietracker.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovie(movie: Movie)

    @Query("SELECT * from movies ORDER BY movie_name ASC")
    fun getAllMovies(): Flow<List<Movie>>

    @Query("SELECT * from movies ORDER BY RANDOM() LIMIT 1")
    fun getRandomMovie(): Flow<Movie?>
}