package com.humay.movies.data.repository

import com.humay.movies.data.local.MoviesDao
import com.humay.movies.data.local.entity.MovieEntity
import com.humay.movies.data.remote.MoviesApi
import com.humay.movies.data.remote.dto.MoviesDto
import com.humay.movies.domain.repository.MoviesRepository
import com.humay.movies.util.Constants
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val dao: MoviesDao,
    private val api: MoviesApi
) : MoviesRepository {

    override suspend fun insertAll(movies: List<MovieEntity>) {
        dao.insertAll(movies)
    }

    override fun getMoviesFromDatabase(): Flow<List<MovieEntity>> {
        return dao.getMovies()
    }

    override suspend fun clearAll() {
        dao.clearAll()
    }

    override suspend fun searchMoviesFromApi(query: String, page: Int): MoviesDto {
        return api.searchMovies(
            apiKey = Constants.API_KEY,
            language = "en-US,",
            page = page,
            query = query
        )
    }

    override suspend fun getPopularMoviesFromApi(page: Int): MoviesDto? {
        return api.getPopularMovies(
            apiKey = Constants.API_KEY,
            language = "en-US,",
            page = page
        )
    }
}