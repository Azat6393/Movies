package com.humay.movies.presentation.movie_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.humay.movies.data.local.entity.MovieDetailsEntity
import com.humay.movies.data.toEntity
import com.humay.movies.domain.model.MovieDetails
import com.humay.movies.domain.repository.MoviesRepository
import com.humay.movies.domain.use_case.GetMovieDetailsUseCase
import com.humay.movies.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val repo: MoviesRepository,
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase
) : ViewModel() {

    private val _movie = MutableStateFlow<Resource<MovieDetails>>(Resource.Empty())
    val movie = _movie.asStateFlow()

    private val _isFavorite = MutableStateFlow<Boolean>(false)
    val isFavorite = _isFavorite.asStateFlow()

    fun getMovieDetails(id: Int) {
        getMovieDetailsUseCase(id.toString()).onEach {
            _movie.value = it
            checkForExist(id)
        }.launchIn(viewModelScope)
    }

    private fun checkForExist(id: Int) = viewModelScope.launch {
        val movieDetails = repo.getMovieDetailsById(id)
        if (movieDetails != null) {
            _isFavorite.value = true
        }
    }

    fun onFavoriteMovie(movieDetails: MovieDetails) = viewModelScope.launch {
        _isFavorite.value = !_isFavorite.value
        if (_isFavorite.value){
            repo.insertMovie(movieDetails.toEntity())
        }else {
            repo.deleteMovie(movieDetails.toEntity())
        }
    }
}