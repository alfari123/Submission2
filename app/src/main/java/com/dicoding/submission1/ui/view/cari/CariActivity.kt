package com.dicoding.submission1.ui.view.cari

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.core.adapter.MoviesListAdapter
import com.dicoding.core.domain.model.Movie
import com.dicoding.submission1.R
import com.dicoding.submission1.databinding.ActivityCariBinding
import com.dicoding.submission1.ui.view.detail.DetailMoviesActivity
import com.dicoding.submission1.ui.view.detail.DetailMoviesActivity.Companion.EXTRA_MOVIE_DETAIL
import com.dicoding.submission1.ui.viewmodel.cari.CariViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CariActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCariBinding
    private val viewModel: CariViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCariBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.tbList)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.cari_menu, menu)

        val searchItem = menu.findItem(R.id.search)
        val searchView = searchItem.actionView as SearchView

        searchItem.expandActionView()
        searchView.queryHint = getString(R.string.name_movie)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchMovie(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })

        searchItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem): Boolean {
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                onBackPressed()
                return false
            }
        })

        return true
    }

    private fun searchMovie(query: String) {
        binding.pbLoading.visibility = View.VISIBLE
        viewModel.searchMovieByQuery(query).observe(this) { result ->
            result.onSuccess { movies ->
                if (movies.isNotEmpty()) {
                    val adapter = MoviesListAdapter(movies)
                    adapter.setOnItemClickCallback(object :
                        MoviesListAdapter.OnItemClickCallback {
                        override fun onItemClicked(movie: Movie) {
                            Intent(this@CariActivity, DetailMoviesActivity::class.java).also { intent ->
                                intent.putExtra(EXTRA_MOVIE_DETAIL, movie)
                                startActivity(intent)
                            }
                        }
                    })

                    val layoutManager = LinearLayoutManager(this)

                    val recyclerView = binding.rvMovies
                    recyclerView.apply {
                        this.adapter = adapter
                        this.layoutManager = layoutManager
                    }
                }
                binding.pbLoading.visibility = View.GONE
            }
        }
    }
}