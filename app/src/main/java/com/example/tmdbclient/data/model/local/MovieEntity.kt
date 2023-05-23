package com.example.tmdbclient.data.model.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_table")
data class MovieEntity(

    @PrimaryKey
    val movieId: Int,

    @ColumnInfo
    val movieTitle: String,

    @ColumnInfo
    val posterPath: String,

    @ColumnInfo
    val releaseDate: String,

    @ColumnInfo
    val voteCount: Int,

    @ColumnInfo
    val voteAverage: Float
)