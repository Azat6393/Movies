package com.humay.movies.domain.repository

import com.humay.movies.data.local.entity.MovieEntity
import com.humay.movies.data.remote.dto.MoviesDto
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

    suspend fun insertAll(movies: List<MovieEntity>)

    fun getMoviesFromDatabase(): Flow<List<MovieEntity>>

    suspend fun clearAll()

    suspend fun searchMoviesFromApi(query: String, page: Int): MoviesDto

    suspend fun getPopularMoviesFromApi(page: Int): MoviesDto?

}