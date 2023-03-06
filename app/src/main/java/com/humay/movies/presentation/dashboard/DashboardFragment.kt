package com.humay.movies.presentation.dashboard

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.humay.movies.R
import com.humay.movies.databinding.FragmentDashboardBinding
import com.humay.movies.domain.model.Movie
import com.humay.movies.presentation.adapter.MoviesAdapter
import com.humay.movies.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DashboardFragment : Fragment(R.layout.fragment_dashboard), MoviesAdapter.OnItemClickListener {

    private lateinit var _binding: FragmentDashboardBinding
    private val viewModel: DashboardViewModel by viewModels()
    private val mAdapter: MoviesAdapter by lazy { MoviesAdapter(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDashboardBinding.bind(view)

        initView()
        observe()
    }

    private fun initView() {
        _binding.apply {
            recyclerView.apply {
                adapter = mAdapter
                layoutManager =
                    GridLayoutManager(requireContext(), 3, GridLayoutManager.VERTICAL, false)
                setHasFixedSize(true)
            }
            leftBtn.setOnClickListener {
                var page = pageTv.text.toString().toInt()
                page--
                viewModel.getMovies(page)
                pageTv.text = page.toString()
                leftBtn.isVisible = page > 1
            }
            rightBtn.setOnClickListener {
                var page = pageTv.text.toString().toInt()
                page++
                viewModel.getMovies(page)
                pageTv.text = page.toString()
                leftBtn.isVisible = page > 1
            }
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