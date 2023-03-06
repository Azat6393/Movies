package com.humay.movies.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_movie")
data class MovieDetailsEntity(
    @PrimaryKey(autoGenerate = true)
    val primary_id: Int? = null,
    val adult: Boolean,
    val backdrop_path: String,
    val budget: Int,
    val genres: String,
    val homepage: String,
    val id: Int,
    val imdb_id: String,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val poster_path: String,
    val release_date: String,
    val revenue: Int,
    val runtime: Int,
    val status: String,
    val tagline: String,
    val title: String,
)