package com.humay.movies.domain.repository

import com.humay.movies.data.local.entity.MovieDetailsEntity
import com.humay.movies.data.local.entity.MovieEntity
import com.humay.movies.data.remote.dto.MovieDetailsDto
import com.humay.movies.data.remote.dto.MoviesDto
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesRepository {

    suspend fun insertAll(movies: List<MovieEntity>)

    fun getMoviesFromDatabase(): Flow<List<MovieEntity>>

    suspend fun clearAll()

    suspend fun searchMoviesFromApi(query: String, page: Int): MoviesDto

    suspend fun getPopularMoviesFromApi(page: Int): MoviesDto?

    suspend fun getMovieDetails(
        movieId: String
    ): MovieDetailsDto

    suspend fun insertMovie(movie: MovieDetailsEntity)

    suspend fun deleteMovie(movie: MovieDetailsEntity)

    fun getFavoriteMovies(): Flow<List<MovieDetailsEntity>>

    suspend fun getMovieDetailsById(id: Int): MovieDetailsEntity?

}