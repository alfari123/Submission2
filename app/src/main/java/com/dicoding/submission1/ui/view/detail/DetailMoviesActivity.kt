package com.dicoding.submission1.ui.view.detail

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.dicoding.core.adapter.MoviesActreesAdapter
import com.dicoding.core.domain.model.Movie
import com.dicoding.core.utils.animateAlpha
import com.dicoding.core.utils.getImageOriginalUrl
import com.dicoding.submission1.R
import com.dicoding.submission1.databinding.ActivityDetailMoviesBinding
import com.dicoding.submission1.ui.viewmodel.detail.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailMoviesActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityDetailMoviesBinding
    private lateinit var movie: Movie
    private val detailMovieViewModel: DetailViewModel by viewModels()


    private var isFavorite: Boolean? = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMoviesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = binding.tbDetails
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        movie = intent.getParcelableExtra(EXTRA_MOVIE_DETAIL)!!
        setAllDataMovie(movie.id)
        getDataMovie()

        binding.toggleFavorite.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.toggle_favorite -> {
                if (isFavorite == false) {
                    detailMovieViewModel.saveMovieAsFavorite(movie)
                    isFavoriteMovie(false)
                    Toast.makeText(this, getString(R.string.save_to_favorite), Toast.LENGTH_SHORT)
                        .show()
                } else {
                    detailMovieViewModel.deleteMovieFromFavorite(movie.id)
                    isFavoriteMovie(true)
                    Toast.makeText(
                        this,
                        getString(R.string.delete_from_favorite),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun isFavoriteMovie(favorite: Boolean) {
        if (favorite) {
            binding.toggleFavorite.setImageResource(R.drawable.ic_favorite)
        } else {
            binding.toggleFavorite.setImageResource(R.drawable.ic_un_favorite)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {
        const val EXTRA_MOVIE_DETAIL = "extra_movie_detail"
        private const val TAG = "DetailActivity"
    }

    private fun setAllDataMovie(id: Int) {

        detailMovieViewModel.getMovieCasts(id).observe(this) { result ->
            result.onSuccess { actor ->
                Log.d(TAG, "setAllDataMovie: $actor")
                if (actor.isNotEmpty()) {
                    val selectedActor =
                        actor.slice(if (actor.size <= 5) actor.indices else 0 until 5)
                    val adapter = MoviesActreesAdapter(selectedActor)
                    val layoutManager = LinearLayoutManager(this@DetailMoviesActivity)

                    val recyclerView = binding.rvDetailCast
                    recyclerView.apply {
                        this.adapter = adapter
                        this.setHasFixedSize(true)
                        this.layoutManager = layoutManager
                    }

                    binding.apply {
                        tvActor.animateAlpha(true)
                        rvDetailCast.animateAlpha(true)
                    }
                }
            }

            result.onFailure {
                Log.e(TAG, "setAllDataMovie: ${it.message}")
            }
        }

        detailMovieViewModel.isFavoriteMovie(id).observe(this) { state ->
            isFavoriteMovie(state)
            isFavorite = state
        }
    }

    private fun getDataMovie() {
        binding.apply {
            tvNameMovie.text = movie.title
            tvMovieOverview.text = movie.overview

            Glide
                .with(this@DetailMoviesActivity)
                .load(getImageOriginalUrl(movie.posterPath))
                .error(R.drawable.ic_no_image)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
//                        Toast.makeText(
//                            this@DetailMoviesActivity,
//                            getString(R.string.error_data),
//                            Toast.LENGTH_SHORT
//                        )
//                            .show()

                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }
                })
                .into(imgMovie)
        }
    }
}