package com.humay.movies.presentation.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.humay.movies.R
import com.humay.movies.databinding.FragmentProfileBinding
import com.humay.movies.databinding.FragmentSearchBinding
import com.humay.movies.domain.model.Movie
import com.humay.movies.presentation.adapter.MoviesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile), MoviesAdapter.OnItemClickListener {

    private lateinit var _binding: FragmentProfileBinding
    private val viewModel: ProfileViewModel by viewModels()
    private val mAdapter: MoviesAdapter by lazy { MoviesAdapter(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentProfileBinding.bind(view)

        initRecyclerView()
        observe()
    }

    private fun initRecyclerView() {
        _binding.recyclerView.apply {
            adapter = mAdapter
            layoutManager =
                GridLayoutManager(requireContext(), 3, GridLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
        }
    }

    private fun observe() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.movie.collect {
                    mAdapter.submitList(it)
                }
            }
        }
    }

    override fun onClick(movie: Movie) {
        val action =
            ProfileFragmentDirections.actionProfileFragmentToMovieDetailsFragment(movieId = movie.id!!)
        findNavController().navigate(action)
    }
}