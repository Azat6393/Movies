package com.humay.movies.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.humay.movies.domain.model.Movie
import com.humay.movies.domain.use_case.GetPopularMoviesUseCase
import com.humay.movies.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase
) : ViewModel() {

    private val _movies = MutableStateFlow<Resource<List<Movie>>>(Resource.Empty())
    val movies = _movies.asStateFlow()

    init {
        getMovies(1)
    }

    fun getMovies(page: Int) {
        getPopularMoviesUseCase(page).onEach {
            _movies.value = it
        }.launchIn(viewModelScope)
    }
}