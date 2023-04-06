package com.dicoding.fav

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.core.adapter.MoviesListAdapter
import com.dicoding.core.domain.model.Movie
import com.dicoding.fav.databinding.ActivityFavoriteBinding
import com.dicoding.submission1.di.FavModuleDepedency
import com.dicoding.submission1.ui.view.detail.DetailMoviesActivity
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModelFavorite: FavoriteViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerFavoriteComponent.builder()
            .context(this)
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    applicationContext,
                    FavModuleDepedency::class.java
                )
            )
            .build()
            .inject(this)

        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.tbFavorite)
        supportActionBar?.title = getString(R.string.favorite)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        getFavoriteData()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun getFavoriteData() {
        supportActionBar?.title = getString(R.string.favorite)
        viewModelFavorite.favoriteMovies.observe(this) { movies ->
            if (movies.isNotEmpty()) {
                val layoutManager = LinearLayoutManager(this)
                val adapter = MoviesListAdapter(movies)
                adapter.setOnItemClickCallback(object : MoviesListAdapter.OnItemClickCallback {
                    override fun onItemClicked(movie: Movie) {
                        Intent(this@FavoriteActivity, DetailMoviesActivity::class.java).also { intent ->
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

                showErrorMessage(false)

            } else {
                showErrorMessage(
                    true,
                    getString(com.dicoding.submission1.R.string.movie_not_found)
                )
            }
        }
    }

    private fun showErrorMessage(isVisible: Boolean, message: String? = null) {
        binding.apply {
            rvList.visibility = if (isVisible) View.GONE else View.VISIBLE
            rlError.visibility = if (isVisible) View.VISIBLE else View.GONE
            tvMessage.text = message
        }
    }
}