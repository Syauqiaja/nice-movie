package com.brighton.nicemovie.presentation.features.favorite

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.map
import androidx.recyclerview.widget.LinearLayoutManager
import com.brighton.nicemovie.databinding.FragmentFavoriteBinding
import com.brighton.nicemovie.presentation.features.home.HomeFragmentDirections
import com.brighton.nicemovie.presentation.features.home.adapter.MovieAdapter
import com.brighton.nicemovie.presentation.features.home.adapter.MoviePagingAdapter
import com.syauqi.watcheez.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteFragment:BaseFragment<FragmentFavoriteBinding>(
    FragmentFavoriteBinding::inflate
) {
    private val viewModel: FavoriteViewModel by viewModels()
    private lateinit var moviePagingAdapter : MovieAdapter

    override fun initView() {
        super.initView()
        binding.apply {
            moviePagingAdapter = MovieAdapter(arrayListOf())
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

        moviePagingAdapter.onItemClick = { movie ->
            val action =
                FavoriteFragmentDirections.actionFavoriteFragmentToMovieDetailFragment(movie.imdbID)
            findNavController().navigate(action)
        }
    }
}