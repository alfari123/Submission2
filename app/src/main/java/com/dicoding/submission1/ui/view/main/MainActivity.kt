package com.dicoding.submission1.ui.view.main

import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.ConfigurationCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.dicoding.core.adapter.MoviesHomeAdapter
import com.dicoding.core.domain.model.Movie
import com.dicoding.core.utils.getImageOriginalUrl
import com.dicoding.submission1.R
import com.dicoding.submission1.databinding.ActivityMainBinding
import com.dicoding.submission1.ui.view.cari.CariActivity
import com.dicoding.submission1.ui.view.detail.DetailMoviesActivity
import com.dicoding.submission1.ui.view.detail.DetailMoviesActivity.Companion.EXTRA_MOVIE_DETAIL
import com.dicoding.submission1.ui.view.list.ListMoviesActivity
import com.dicoding.submission1.ui.view.list.ListMoviesActivity.Companion.CATEGORY_POPULAR
import com.dicoding.submission1.ui.view.list.ListMoviesActivity.Companion.CATEGORY_TRENDING
import com.dicoding.submission1.ui.view.list.ListMoviesActivity.Companion.CATEGORY_UPCOMING
import com.dicoding.submission1.ui.view.list.ListMoviesActivity.Companion.EXTRA_LIST_CATEGORY
import com.dicoding.submission1.ui.viewmodel.main.MainViewModel
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.random.Random

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.tbHome)
        supportActionBar?.setDisplayShowTitleEnabled(false)


        getDataMovies()

        binding.apply {
            tvTrendingMore.setOnClickListener(this@MainActivity)
            tvUpcomingMore.setOnClickListener(this@MainActivity)
            tvPopularMore.setOnClickListener(this@MainActivity)
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.tv_trending_more -> {
                Intent(this, ListMoviesActivity::class.java).also { intent ->
                    intent.putExtra(EXTRA_LIST_CATEGORY, CATEGORY_TRENDING)
                    startActivity(intent)
                }
            }
            R.id.tv_upcoming_more -> {
                Intent(this, ListMoviesActivity::class.java).also { intent ->
                    intent.putExtra(EXTRA_LIST_CATEGORY, CATEGORY_UPCOMING)
                    startActivity(intent)
                }
            }
            R.id.tv_popular_more -> {
                Intent(this, ListMoviesActivity::class.java).also { intent ->
                    intent.putExtra(EXTRA_LIST_CATEGORY, CATEGORY_POPULAR)
                    startActivity(intent)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.item_search -> {
                Intent(this, CariActivity::class.java).also { intent ->
                    startActivity(intent)
                }
                true
            }
            R.id.item_favorite -> {
                val uri = Uri.parse("moviefavorite://favorite")
                startActivity(Intent(Intent.ACTION_VIEW, uri))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun getDataMovies() {
        val region = ConfigurationCompat.getLocales(resources.configuration)[0]!!.country

        binding.apply {
            rlError.visibility = View.GONE
            nestedScroll.visibility = View.VISIBLE
        }

        mainViewModel.getNowPlayingMovies(region).observe(this) { result ->
            result.onSuccess { movies ->
                if (movies.isNotEmpty()) {
                    val resultLength = movies.size
                    val seed = Calendar.getInstance().timeInMillis

                    val index = Random(seed).nextInt(0, resultLength)
                    val movie = movies[index]

                    binding.apply {
                        tvNameMovie.text = movie.title
                        tvInformation.text = movie.overview

                        Glide
                            .with(this@MainActivity)
                            .load(getImageOriginalUrl(movie.posterPath))
                            .error(R.drawable.ic_no_image)
                            .listener(object : RequestListener<Drawable> {
                                override fun onLoadFailed(
                                    e: GlideException?,
                                    model: Any?,
                                    target: Target<Drawable>?,
                                    isFirstResource: Boolean
                                ): Boolean {
                                    return false
                                }

                                override fun onResourceReady(
                                    resource: Drawable?,
                                    model: Any?,
                                    target: Target<Drawable>?,
                                    dataSource: DataSource?,
                                    isFirstResource: Boolean
                                ): Boolean {

//                                    shimmerMainPoster.stopShimmer()
//                                    shimmerMainPoster.animateAlpha(false)
//                                    tvMainTitle.animateAlpha(true)
//                                    tvMainInformation.animateAlpha(true)
//                                    llRatingView.animateAlpha(true)

                                    return false
                                }
                            })
                            .into(imgMovies)

                        imgMovies.setOnClickListener {
                            Intent(this@MainActivity, DetailMoviesActivity::class.java).also { intent ->
                                intent.putExtra(EXTRA_MOVIE_DETAIL, movie)
                                startActivity(intent)
                            }
                        }
                    }
                }
            }

            result.onFailure {
                onErrorOccurred()
            }
        }

        mainViewModel.getTrendingMovies(region).observe(this) { result ->
            result.onSuccess { movies ->
                if (movies.isNotEmpty()) {
                    val adapter = MoviesHomeAdapter(movies)
                    adapter.setOnItemClickCallback(object :
                        MoviesHomeAdapter.OnItemClickCallback {
                        override fun onItemClicked(movie: Movie) {
                            Intent(this@MainActivity, DetailMoviesActivity::class.java).also { intent ->
                                intent.putExtra(EXTRA_MOVIE_DETAIL, movie)
                                startActivity(intent)
                            }
                        }
                    })

                    val layoutManager = FlexboxLayoutManager(this)
                    layoutManager.apply {
                        flexDirection = FlexDirection.ROW
                        flexWrap = FlexWrap.NOWRAP
                    }

                    val recyclerView = binding.rvTrending
                    recyclerView.apply {
                        this.adapter = adapter
                        this.setHasFixedSize(true)
                        this.layoutManager = layoutManager
                    }

//                    binding.shimmerRvTrending.apply {
//                        stopShimmer()
//                        animateAlpha(false)
//                    }
                }
            }

            result.onFailure {
                onErrorOccurred()
            }
        }

        mainViewModel.getUpcomingMovies(region).observe(this) { result ->
            result.onSuccess { movies ->
                if (movies.isNotEmpty()) {
                    val adapter = MoviesHomeAdapter(movies)
                    adapter.setOnItemClickCallback(object :
                        MoviesHomeAdapter.OnItemClickCallback {
                        override fun onItemClicked(movie: Movie) {
                            Intent(this@MainActivity, DetailMoviesActivity::class.java).also { intent ->
                                intent.putExtra(EXTRA_MOVIE_DETAIL, movie)
                                startActivity(intent)
                            }
                        }
                    })

                    val layoutManager = FlexboxLayoutManager(this)
                    layoutManager.apply {
                        flexDirection = FlexDirection.ROW
                        flexWrap = FlexWrap.NOWRAP
                    }

                    val recyclerView = binding.rvUpcoming
                    recyclerView.apply {
                        this.adapter = adapter
                        this.setHasFixedSize(true)
                        this.layoutManager = layoutManager
                    }

//                    binding.shimmerRvUpcoming.apply {
//                        stopShimmer()
//                        animateAlpha(false)
//                    }
                }
            }

            result.onFailure {
                onErrorOccurred()
            }
        }

        mainViewModel.getPopularMovies(region).observe(this) { result ->
            result.onSuccess { movies ->
                if (movies.isNotEmpty()) {
                    val adapter = MoviesHomeAdapter(movies)
                    adapter.setOnItemClickCallback(object :
                        MoviesHomeAdapter.OnItemClickCallback {
                        override fun onItemClicked(movie: Movie) {
                            Intent(this@MainActivity, DetailMoviesActivity::class.java).also { intent ->
                                intent.putExtra(EXTRA_MOVIE_DETAIL, movie)
                                startActivity(intent)
                            }
                        }
                    })

                    val layoutManager = FlexboxLayoutManager(this)
                    layoutManager.apply {
                        flexDirection = FlexDirection.ROW
                        flexWrap = FlexWrap.NOWRAP
                    }

                    val recyclerView = binding.rvPopular
                    recyclerView.apply {
                        this.adapter = adapter
                        this.setHasFixedSize(true)
                        this.layoutManager = layoutManager
                    }

//                    binding.shimmerRvPopular.apply {
//                        stopShimmer()
//                        animateAlpha(false)
//                    }
                }
            }

            result.onFailure {
                onErrorOccurred()
            }
        }
    }

    private fun onErrorOccurred() {
        binding.apply {
            rlError.visibility = View.VISIBLE
            nestedScroll.visibility = View.GONE
        }
    }
}