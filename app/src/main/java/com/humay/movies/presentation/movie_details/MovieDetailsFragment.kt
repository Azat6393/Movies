package com.humay.movies.presentation.movie_details

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.humay.movies.R
import com.humay.movies.databinding.FragmentMovieDetailsBinding
import com.humay.movies.domain.model.MovieDetails
import com.humay.movies.util.Constants
import com.humay.movies.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieDetailsFragment : Fragment(R.layout.fragment_movie_details) {

    private lateinit var _binding: FragmentMovieDetailsBinding
    private val args: MovieDetailsFragmentArgs by navArgs()
    private val viewModel: MovieDetailsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMovieDetailsBinding.bind(view)

        observe()
        viewModel.getMovieDetails(args.movieId)
    }

    @SuppressLint("SetTextI18n")
    private fun initView(item: MovieDetails) {
        _binding.apply {
            Glide.with(_binding.root)
                .load(Constants.IMAGE_BASE_URL + item.backdrop_path)
                .placeholder(R.drawable.ic_baseline_image_24)
                .centerCrop()
                .into(imageView)
            dateTv.text = item.release_date
            movieNameTv.text = item.original_title
            genreTv.text = "Genre: ${item.genres}"
            timeTv.text = "Time: ${item.runtime}"
            favoriteIg.setOnClickListener { viewModel.onFavoriteMovie(item) }
        }
    }

    private fun initFab(state: Boolean) {
        if (state) {
            _binding.favoriteIg.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_star_24))
        } else {
            _binding.favoriteIg.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_star_border_24))
        }
    }

    private fun observe() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.movie.collect { result ->
                    when (result) {
                        is Resource.Empty -> Unit
                        is Resource.Error -> {
                            loading(false)
                            Toast.makeText(requireContext(), result.message, Toast.LENGTH_LONG)
                                .show()
                        }
                        is Resource.Loading -> loading(true)
                        is Resource.Success -> {
                            loading(false)
                            result.data?.let { initView(it) }
                        }
                    }
                }
            }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isFavorite.collect { state ->
                    initFab(state)
                }
            }
        }
    }


    private fun loading(state: Boolean) {
        _binding.progressBar.isVisible = state
    }
}