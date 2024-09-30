package com.brighton.nicemovie.presentation.features.home

import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.map
import androidx.recyclerview.widget.LinearLayoutManager
import com.brighton.nicemovie.databinding.FragmentHomeBinding
import com.brighton.nicemovie.domain.movie.model.Movie
import com.brighton.nicemovie.presentation.features.home.adapter.MoviePagingAdapter
import com.syauqi.watcheez.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private val viewModel : HomeViewModel by viewModels()
    private val moviePagingAdapter : MoviePagingAdapter by lazy { MoviePagingAdapter() }
    private var trendingMovieLoaded: Boolean = false

    override fun initView() {
        super.initView()
        binding.apply {
            rvMovies.setHasFixedSize(true)
            rvMovies.adapter = moviePagingAdapter
            rvMovies.layoutManager = LinearLayoutManager(requireActivity())
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.movies.collectLatest {
                it.map { data ->
                    Log.d("HomeFragment", "collecting: $data")
                }
                moviePagingAdapter.submitData(it)
            }
        }

        moviePagingAdapter.onItemClick = {movie ->
            val action = HomeFragmentDirections.actionHomeFragmentToMovieDetailFragment(movie.imdbID)
            findNavController().navigate(action)
        }

        binding.btnActionSave.setOnClickListener{
            val action = HomeFragmentDirections.actionHomeFragmentToFavoriteFragment()
            findNavController().navigate(action)
        }
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val q = newText ?: ""
                startSearchWithDelay(q)
                return false
            }
        })
    }

    private fun startSearchWithDelay(query: String) {
        searchRunnable?.let { searchHandler.removeCallbacks(it) }

        searchRunnable = Runnable {
            viewModel.searchMovies(query)
        }

        searchHandler.postDelayed(searchRunnable!!, 500)
    }

    private val searchHandler = Handler(Looper.getMainLooper())
    private var searchRunnable: Runnable? = null
}