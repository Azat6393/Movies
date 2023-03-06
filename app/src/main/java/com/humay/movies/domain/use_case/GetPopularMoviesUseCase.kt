package com.humay.movies.domain.use_case

import com.humay.movies.data.toMoviesEntity
import com.humay.movies.data.toMoviesResponse
import com.humay.movies.domain.model.Movie
import com.humay.movies.domain.repository.MoviesRepository
import com.humay.movies.util.Resource
import kotlinx.coroutines.flow.*
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(
    private val repo: MoviesRepository
) {
    operator fun invoke(page: Int): Flow<Resource<List<Movie>>> = flow {
        try {
            val apiResponse = repo.getPopularMoviesFromApi(page)
            val movies = apiResponse?.results?.map { it.toMoviesEntity() }
            if (movies != null) {
                repo.clearAll()
                repo.insertAll(movies)
            }
            val moviesFromDatabase =
                repo.getMoviesFromDatabase()
            moviesFromDatabase.collect {
                emit(Resource.Success<List<Movie>>(data = it.map { it1 -> it1.toMoviesResponse() }))
            }
        } catch (e: HttpException) {
            val movies = repo.getMoviesFromDatabase().first().map { it.toMoviesResponse() }
            emit(
                Resource.Error<List<Movie>>(
                    message = e.localizedMessage ?: "Something went wrong",
                    data = movies
                )
            )
        } catch (e: IOException) {
            val movies = repo.getMoviesFromDatabase().first().map { it.toMoviesResponse() }
            emit(
                Resource.Error<List<Movie>>(
                    message = e.localizedMessage ?: "Something went wrong",
                    data = movies
                )
            )
        }
    }
}