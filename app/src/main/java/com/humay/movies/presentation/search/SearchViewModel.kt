package com.humay.movies.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.humay.movies.domain.model.Movie
import com.humay.movies.domain.use_case.SearchMoviesUseCase
import com.humay.movies.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchMoviesUseCase: SearchMoviesUseCase
) : ViewModel() {

    private val _movies = MutableStateFlow<Resource<List<Movie>>>(Resource.Empty())
    val movies = _movies.asStateFlow()

    fun searchMovies(query: String, page: Int) = viewModelScope.launch{
        delay(400L)
        searchMoviesUseCase(query, page).onEach {
            _movies.value = it
        }.launchIn(viewModelScope)
    }
}