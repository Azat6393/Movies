package com.humay.movies.domain.use_case

import com.humay.movies.data.toMoviesResponse
import com.humay.movies.domain.model.Movie
import com.humay.movies.domain.repository.MoviesRepository
import com.humay.movies.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SearchMoviesUseCase @Inject constructor(
    private val repo: MoviesRepository
) {
    operator fun invoke(query: String, page: Int): Flow<Resource<List<Movie>>> = flow {
        try {
            val apiResponse = repo.searchMoviesFromApi(query, page)
            val movies = apiResponse.results.map { it.toMoviesResponse() }
            emit(Resource.Success(data = movies))
        } catch (e: HttpException) {
            emit(
                Resource.Error(
                    message = e.localizedMessage ?: "Something went wrong"
                )
            )
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    message = e.localizedMessage ?: "Something went wrong"
                )
            )
        }
    }
}