package com.humay.movies.domain.use_case

import com.humay.movies.data.toMovieDetails
import com.humay.movies.domain.model.MovieDetails
import com.humay.movies.domain.repository.MoviesRepository
import com.humay.movies.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(
    private val repo: MoviesRepository
) {
    operator fun invoke(movieId: String): Flow<Resource<MovieDetails>> = flow {
        try {
            emit(Resource.Loading())
            val apiResponse = repo.getMovieDetails(movieId = movieId)
            emit(Resource.Success(data = apiResponse.toMovieDetails()))
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