package com.dicoding.core.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.core.databinding.ItemListMoviesBinding
import com.dicoding.core.domain.model.Movie
import com.dicoding.core.utils.getImageUrl
import com.dicoding.core.utils.setImageFromUrl

class MoviesListAdapter(private val movies: List<Movie>) :
    RecyclerView.Adapter<MoviesListAdapter.ViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MoviesListAdapter.ViewHolder {
        val binding =
            ItemListMoviesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MoviesListAdapter.ViewHolder, position: Int) {
        val movie = movies[position]
        val context = holder.itemView.context

        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(movie) }
        holder.bind(context, movie)
    }

    override fun getItemCount(): Int = movies.size

    inner class ViewHolder(private val binding: ItemListMoviesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(context: Context, movie: Movie) {
            val rating = movie.voteAverage.div(2)
            binding.apply {
                tvNameMovie.text = movie.title
                tvRating.text = String.format("%.2f", rating)
                tvMovieItemOverview.text = movie.overview
                imgMovies.setImageFromUrl(context, getImageUrl(movie.posterPath))
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(movie: Movie)
    }
}