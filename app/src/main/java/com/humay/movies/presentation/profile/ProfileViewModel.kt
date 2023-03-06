package com.humay.movies.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.humay.movies.data.toMovie
import com.humay.movies.domain.model.Movie
import com.humay.movies.domain.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repo: MoviesRepository
) : ViewModel() {

    private val _movies = MutableStateFlow<List<Movie>>(emptyList())
    val movie = _movies.asStateFlow()

    init {
        getFavoriteMovies()
    }

    private fun getFavoriteMovies() {
        repo.getFavoriteMovies().onEach { list ->
            _movies.value = list.map { it.toMovie() }
        }.launchIn(viewModelScope)
    }
}