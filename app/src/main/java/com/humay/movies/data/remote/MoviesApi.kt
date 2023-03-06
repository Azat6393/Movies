package com.humay.movies.data.remote

import com.humay.movies.data.remote.dto.GenreDto
import com.humay.movies.data.remote.dto.MovieDetailsDto
import com.humay.movies.data.remote.dto.MoviesDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApi {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): MoviesDto

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int,
        @Query("query") query: String
    ): MoviesDto

    @GET("movie/{id}")
    suspend fun getMovieDetails(
        @Path("id") movieId: String,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): MovieDetailsDto

    @GET("genre/movie/list")
    suspend fun getGenreList(
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): List<GenreDto>
}