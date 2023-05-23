package com.example.tmdbclient.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tmdbclient.data.local.TMDBClientDao
import com.example.tmdbclient.data.model.local.MovieEntity

@Database(entities = [MovieEntity::class], version = 1, exportSchema = false)
abstract class TMDBDataBase: RoomDatabase() {

    abstract fun tmdbDao(): TMDBClientDao

}