package com.humay.movies.presentation.search

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.humay.movies.R
import com.humay.movies.databinding.FragmentSearchBinding
import com.humay.movies.domain.model.Movie
import com.humay.movies.presentation.adapter.SearchMovieAdapter
import com.humay.movies.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search), SearchMovieAdapter.OnItemClickListener {

    private lateinit var _binding: FragmentSearchBinding
    private val viewModel: SearchViewModel by viewModels()
    private val mAdapter: SearchMovieAdapter by lazy { SearchMovieAdapter(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSearchBinding.bind(view)

        initView()
        observe()
    }

    private fun initView() {
        _binding.filledTextField.editText?.doAfterTextChanged {
            viewModel.searchMovies(query = it.toString(), 1)
        }
        _binding.recyclerView.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    private fun observe() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.movies.collect { result ->
                    when (result) {
                        is Resource.Empty -> Unit
                        is Resource.Error -> {
                            loading(false)
                            Toast.makeText(requireContext(), result.message, Toast.LENGTH_LONG)
                                .show()
                            mAdapter.submitList(result.data)
                        }
                        is Resource.Loading -> loading(true)
                        is Resource.Success -> {
                            loading(false)
                            mAdapter.submitList(result.data)
                        }
                    }
                }
            }
        }
    }

    private fun loading(state: Boolean) {
        _binding.progressBar.isVisible = state
    }


    override fun onClick(movie: Movie) {

    }
}