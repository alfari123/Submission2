package com.dicoding.core.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.core.databinding.ItemHomeMoviesBinding
import com.dicoding.core.domain.model.Movie
import com.dicoding.core.utils.getImageOriginalUrl
import com.dicoding.core.utils.setImageFromUrl
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexboxLayoutManager

class MoviesHomeAdapter(private val movies: List<Movie>) :
    RecyclerView.Adapter<MoviesHomeAdapter.ViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemHomeMoviesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movies[position]
        val context = holder.itemView.context

        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(movie) }
        holder.bind(context, movie)
    }

    override fun getItemCount(): Int = movies.size

    inner class ViewHolder(private val binding: ItemHomeMoviesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            val lp: ViewGroup.LayoutParams = itemView.layoutParams
            if (lp is FlexboxLayoutManager.LayoutParams) {
                lp.flexShrink = 0.0f
                lp.alignSelf = AlignItems.FLEX_START
            }
        }

        fun bind(context: Context, movie: Movie) {
            val rating = movie.voteAverage.div(2)
            binding.apply {
                tvNameMovie.text = movie.title
                tvMovieItemRating.text = String.format("%.2f", rating)
                imgMovies.setImageFromUrl(
                    context,
                    getImageOriginalUrl(movie.posterPath)
                )
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(movie: Movie)
    }
}