package com.humay.movies.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.humay.movies.data.local.entity.MovieDetailsEntity
import com.humay.movies.data.local.entity.MovieEntity

@Database(entities = [MovieEntity::class, MovieDetailsEntity::class], version = 1)
abstract class MoviesDatabase: RoomDatabase() {
    abstract fun moviesDao(): MoviesDao
}