package com.dicoding.submission1.ui.view.list

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.os.ConfigurationCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.core.adapter.MoviesListAdapter
import com.dicoding.core.domain.model.Movie
import com.dicoding.submission1.R
import com.dicoding.submission1.databinding.ActivityListMoviesBinding
import com.dicoding.submission1.ui.view.detail.DetailMoviesActivity
import com.dicoding.submission1.ui.viewmodel.list.ListMoviesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListMoviesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListMoviesBinding
    private val viewModel: ListMoviesViewModel by viewModels()
    private var region: String = "US"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListMoviesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        region = ConfigurationCompat.getLocales(resources.configuration)[0]!!.country

        when (intent.getStringExtra(EXTRA_LIST_CATEGORY)) {
            CATEGORY_TRENDING -> {
                parseTrendingMovies()
            }
            CATEGORY_UPCOMING -> {
                parseUpcomingMovies()
            }
            CATEGORY_POPULAR -> {
                parsePopularMovies()
            }
            else -> {
                Toast.makeText(this, getString(R.string.invalid_data), Toast.LENGTH_SHORT)
                    .show()
                finish()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun parsePopularMovies() {
        supportActionBar?.title = getString(R.string.popular)
        viewModel.getAllPopularMovies(region).observe(this) { result ->
            result.onSuccess { movies ->
                if (movies.isNotEmpty()) {
                    val layoutManager = LinearLayoutManager(this)
                    val adapter = MoviesListAdapter(movies)
                    adapter.setOnItemClickCallback(object :
                        MoviesListAdapter.OnItemClickCallback {
                        override fun onItemClicked(movie: Movie) {
                            Intent(this@ListMoviesActivity, DetailMoviesActivity::class.java).also { intent ->
                                intent.putExtra(DetailMoviesActivity.EXTRA_MOVIE_DETAIL, movie)
                                startActivity(intent)
                            }
                        }
                    })

                    val recyclerView = binding.rvList
                    recyclerView.apply {
                        this.adapter = adapter
                        this.layoutManager = layoutManager
                    }

//                    binding.shimmerRv.setVisibility(false)
                    showErrorMessage(false)
                } else {
                    showErrorMessage(true, getString(R.string.movie_not_found))
                }
            }

            result.onFailure {
                showErrorMessage(true, getString(R.string.error_data))
            }
        }
    }

    private fun parseUpcomingMovies() {
        supportActionBar?.title = getString(R.string.upcoming)
        viewModel.getAllUpcomingMovies(region).observe(this) { result ->
            result.onSuccess { movies ->
                if (movies.isNotEmpty()) {
                    val layoutManager = LinearLayoutManager(this)
                    val adapter = MoviesListAdapter(movies)
                    adapter.setOnItemClickCallback(object :
                        MoviesListAdapter.OnItemClickCallback {
                        override fun onItemClicked(movie: Movie) {
                            Intent(this@ListMoviesActivity, DetailMoviesActivity::class.java).also { intent ->
                                intent.putExtra(DetailMoviesActivity.EXTRA_MOVIE_DETAIL, movie)
                                startActivity(intent)
                            }
                        }
                    })

                    val recyclerView = binding.rvList
                    recyclerView.apply {
                        this.adapter = adapter
                        this.layoutManager = layoutManager
                    }

//                    binding.shimmerRv.setVisibility(false)
                    showErrorMessage(false)
                } else {
                    showErrorMessage(true, getString(R.string.movie_not_found))
                }
            }

            result.onFailure {
                showErrorMessage(true, getString(R.string.error_data))
            }
        }
    }

    private fun parseTrendingMovies() {
        supportActionBar?.title = getString(R.string.trending)
        viewModel.getAllTrendingMovies(region).observe(this) { result ->
            result.onSuccess { movies ->
                if (movies.isNotEmpty()) {
                    val layoutManager = LinearLayoutManager(this)
                    val adapter = MoviesListAdapter(movies)
                    adapter.setOnItemClickCallback(object :
                        MoviesListAdapter.OnItemClickCallback {
                        override fun onItemClicked(movie: Movie) {
                            Intent(this@ListMoviesActivity, DetailMoviesActivity::class.java).also { intent ->
                                intent.putExtra(DetailMoviesActivity.EXTRA_MOVIE_DETAIL, movie)
                                startActivity(intent)
                            }
                        }
                    })

                    val recyclerView = binding.rvList
                    recyclerView.apply {
                        this.adapter = adapter
                        this.layoutManager = layoutManager
                    }

//                    binding.shimmerRv.setVisibility(false)
                    showErrorMessage(false)
                } else {
                    showErrorMessage(true, getString(R.string.movie_not_found))
                }
            }

            result.onFailure {
                showErrorMessage(true, getString(R.string.error_data))
            }
        }
    }

    private fun showErrorMessage(isVisible: Boolean, message: String? = null) {
        binding.apply {
            rvList.visibility = if (isVisible) View.GONE else View.VISIBLE
            rlMessage.visibility = if (isVisible) View.VISIBLE else View.GONE
            tvMessage.text = message
        }
    }

    companion object {
        const val EXTRA_LIST_CATEGORY = "extra_list_category"
        const val CATEGORY_POPULAR = "popular"
        const val CATEGORY_TRENDING = "trending"
        const val CATEGORY_UPCOMING = "upcoming"
    }
}