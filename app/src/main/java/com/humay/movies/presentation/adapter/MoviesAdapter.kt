package com.humay.movies.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.humay.movies.R
import com.humay.movies.databinding.ItemDashboardMovieBinding
import com.humay.movies.domain.model.Movie
import com.humay.movies.util.Constants
import com.bumptech.glide.Glide

class MoviesAdapter(private val listener: OnItemClickListener) :
    ListAdapter<Movie, MoviesAdapter.MoviesViewHolder>(DIFF_CALL_BACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        return MoviesViewHolder(
            ItemDashboardMovieBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
    }

    inner class MoviesViewHolder(private val _binding: ItemDashboardMovieBinding) :
        RecyclerView.ViewHolder(_binding.root) {

        init {
            _binding.root.setOnClickListener {
                val position = absoluteAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    if (item != null) {
                        listener.onClick(item)
                    }
                }
            }
        }

        fun bind(item: Movie) {
            _binding.apply {
                movieNameTv.text = item.original_title
                Glide.with(_binding.root)
                    .load(Constants.IMAGE_BASE_URL + item.poster_path)
                    .placeholder(R.drawable.ic_baseline_image_24)
                    .into(posterIv)
            }
        }
    }

    companion object {
        private val DIFF_CALL_BACK = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }
        }
    }

    interface OnItemClickListener {
        fun onClick(movie: Movie)
    }
}