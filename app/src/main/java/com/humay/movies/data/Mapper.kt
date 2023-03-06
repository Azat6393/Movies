package com.humay.movies.data

import com.humay.movies.data.local.entity.MovieDetailsEntity
import com.humay.movies.data.local.entity.MovieEntity
import com.humay.movies.data.remote.dto.MovieDetailsDto
import com.humay.movies.data.remote.dto.MovieDto
import com.humay.movies.domain.model.Movie
import com.humay.movies.domain.model.MovieDetails

fun MovieEntity.toMoviesResponse(): Movie {
    return Movie(
        adult = adult,
        backdrop_path = backdrop_path,
        id = id,
        original_language = original_language,
        original_title = original_title,
        overview = overview,
        popularity = popularity,
        poster_path = poster_path,
        release_date = release_date,
        title = title,
        video = video,
        vote_average = vote_average,
        vote_count = vote_count,
    )
}

fun MovieDto.toMoviesResponse(): Movie {
    return Movie(
        adult = adult,
        backdrop_path = backdrop_path,
        id = id,
        original_language = original_language,
        original_title = original_title,
        overview = overview,
        popularity = popularity,
        poster_path = poster_path,
        release_date = release_date,
        title = title,
        video = video,
        vote_average = vote_average,
        vote_count = vote_count,
    )
}

fun MovieDto.toMoviesEntity(): MovieEntity {
    return MovieEntity(
        adult = adult,
        backdrop_path = backdrop_path,
        id = id,
        original_language = original_language,
        original_title = original_title,
        overview = overview,
        popularity = popularity,
        poster_path = poster_path,
        release_date = release_date,
        title = title,
        video = video,
        vote_average = vote_average,
        vote_count = vote_count,
    )
}

fun MovieDetailsDto.toMovieDetails(): MovieDetails {
    val genres = this.genres.joinToString { it.name }
    return MovieDetails(
        adult = adult,
        backdrop_path = backdrop_path,
        budget = budget,
        genres = genres,
        homepage = homepage,
        id = id,
        imdb_id = imdb_id,
        original_language = original_language,
        original_title = original_title,
        overview = overview,
        poster_path = poster_path,
        release_date = release_date,
        revenue = revenue,
        runtime = runtime,
        status = status,
        tagline = tagline,
        title = title
    )
}

fun MovieDetails.toEntity(): MovieDetailsEntity {
    return MovieDetailsEntity(
        adult = adult,
        backdrop_path = backdrop_path,
        budget = budget,
        genres = genres,
        homepage = homepage,
        id = id,
        imdb_id = imdb_id,
        original_language = original_language,
        original_title = original_title,
        overview = overview,
        poster_path = poster_path,
        release_date = release_date,
        revenue = revenue,
        runtime = runtime,
        status = status,
        tagline = tagline,
        title = title
    )
}

fun MovieDetailsEntity.toMovie(): Movie{
    return Movie(
        adult = adult,
        backdrop_path = backdrop_path,
        id = id,
        original_language = original_language,
        original_title = original_title,
        overview = overview,
        poster_path = poster_path,
        release_date = release_date,
        title = title,
    )
}