package com.example.tmdbclient.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tmdbclient.data.model.local.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TMDBClientDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MovieEntity)

    @Query("SELECT * FROM movie_table")
    fun getAllMovies(): Flow<List<MovieEntity>>
}