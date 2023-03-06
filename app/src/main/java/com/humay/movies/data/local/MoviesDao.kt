package com.humay.movies.data.local

import androidx.room.*
import com.humay.movies.data.local.entity.MovieDetailsEntity
import com.humay.movies.data.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<MovieEntity>)

    @Query("SELECT * FROM movie")
    fun getMovies(): Flow<List<MovieEntity>>

    @Query("DELETE FROM movie")
    suspend fun clearAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MovieDetailsEntity)

    @Query("DELETE FROM favorite_movie WHERE id=:id")
    suspend fun deleteMovie(id: Int)

    @Query("SELECT * FROM favorite_movie")
    fun getFavoriteMovies(): Flow<List<MovieDetailsEntity>>

    @Query("SELECT * FROM favorite_movie WHERE id=:id")
    suspend fun getMovieDetailsById(id: Int): MovieDetailsEntity?
}