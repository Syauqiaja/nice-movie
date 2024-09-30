package com.brighton.nicemovie.presentation.features.movie_detail

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.core.text.parseAsHtml
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.brighton.nicemovie.R
import com.brighton.nicemovie.core.data.Resource
import com.brighton.nicemovie.databinding.FragmentMovieDetailBinding
import com.brighton.nicemovie.domain.movie.model.MovieDetail
import com.brighton.nicemovie.domain.movie.model.parseMovieDetailToMovie
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.carousel.CarouselLayoutManager
import com.google.android.material.carousel.CarouselSnapHelper
import com.syauqi.watcheez.presentation.base.BaseFragment
import com.brighton.nicemovie.utils.MarginItemDecoration
import com.brighton.nicemovie.utils.blurAtRuntime
import com.syauqi.watcheez.presentation.features.movie_detail.MovieDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.math.abs

@AndroidEntryPoint
class MovieDetailFragment : BaseFragment<FragmentMovieDetailBinding>(
    FragmentMovieDetailBinding::inflate
) {
    private val viewModel : MovieDetailViewModel by viewModels()
    private val movieId : String by lazy {
        MovieDetailFragmentArgs.fromBundle(requireArguments()).movieId
    }

    override fun initView() {
        binding.apply {
            appBar.addOnOffsetChangedListener(appbarScrollOffsetListener)
            toolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    override fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
                launch {
                    viewModel.movieDetail.collectLatest {
                        when(it){
                            is Resource.Error -> {Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()}
                            is Resource.Loading -> {

                            }
                            is Resource.Success -> {
                                setupMovieDetailView(it.data!!)
                            }
                        }
                    }
                }
                launch {
                    viewModel.isFav.collectLatest {value ->
                        if (value) {
                            binding.btnSave.icon = ResourcesCompat.getDrawable(resources, R.drawable.ic_bookmark_filled, null)
                            binding.btnActionSave.setImageResource(R.drawable.ic_bookmark_filled)
                        } else {
                            binding.btnSave.icon = ResourcesCompat.getDrawable(resources, R.drawable.ic_bookmark_outline, null)
                            binding.btnActionSave.setImageResource(R.drawable.ic_bookmark_outline)
                        }
                    }
                }
            }
        }
        viewModel.setMovieId(movieId)
    }

    @SuppressLint("StringFormatMatches")
    private fun setupMovieDetailView(movie: MovieDetail){
        binding.apply {
            tvMovieTitle.text = movie.title
            tvDetailHead.text = getString(R.string.detail_head_movie, movie.year, movie.runtime, movie.genre)
            tvPopularity.text = getString(R.string.popularity_format, movie.ratings!![0].value)

            Glide.with(requireContext())
                .asBitmap()
                .placeholder(shimmerDrawable)
                .load(movie.poster)
                .into(object : CustomTarget<Bitmap>(){
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        if(_binding != null){
                            binding.ivBackdrop.setImageBitmap(resource.blurAtRuntime(requireContext()))
                        }
                    }
                    override fun onLoadCleared(placeholder: Drawable?) {}
                })
            Glide.with(requireContext())
                .load(movie.poster)
                .placeholder(shimmerDrawable)
                .into(binding.ivPoster)

            btnActionSave.setOnClickListener {
                if(viewModel.isFav.value){
                    viewModel.deleteFav(movie.parseMovieDetailToMovie())
                }else{
                    viewModel.setFav(movie.parseMovieDetailToMovie())
                }
            }
            btnActionSave.setOnClickListener {
                if(viewModel.isFav.value){
                    viewModel.deleteFav(movie.parseMovieDetailToMovie())
                }else{
                    viewModel.setFav(movie.parseMovieDetailToMovie())
                }
            }
            tvSummary.text = movie.plot
            tvDirector.text = getString(R.string.director_s, movie.director)
            tvProductionCompany.text = getString(R.string.production_company_s, movie.production)

        }
    }

    private val appbarScrollOffsetListener = AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
        binding.appbarForeground.alpha = abs(verticalOffset.toFloat() / appBarLayout.totalScrollRange.toFloat())
    }
}