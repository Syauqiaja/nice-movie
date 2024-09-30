package com.syauqi.watcheez.presentation.features.movie_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.brighton.nicemovie.core.data.Resource
import com.brighton.nicemovie.domain.movie.model.Movie
import com.brighton.nicemovie.domain.movie.model.MovieDetail
import com.brighton.nicemovie.domain.movie.model.toEntity
import com.brighton.nicemovie.domain.movie.usecase.FavoriteUseCase
import com.brighton.nicemovie.domain.movie.usecase.MovieUseCase
import com.syauqi.watcheez.core.data.source.local.database.MoviesDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val movieUseCase: MovieUseCase,
    private val favoriteUseCase: FavoriteUseCase,
    private val database: MoviesDatabase
):ViewModel() {
    val movieDetail : StateFlow<Resource<MovieDetail>> get() = _movieDetail
    private val _movieDetail = MutableStateFlow<Resource<MovieDetail>>(Resource.Loading())

    val isFav : StateFlow<Boolean> get() = _isFav
    private val _isFav = MutableStateFlow<Boolean>(false)

    fun getFav(movieId: String){
        viewModelScope.launch {
            database.getMovieDao().isFavorite(movieId).collectLatest {
                if(it.isEmpty()){
                    _isFav.value = false
                }else{
                    _isFav.value = true
                }
            }
        }
    }

    fun setMovieId(id: String){
        viewModelScope.launch {
            movieUseCase.getMovieDetail(movieId = id).collectLatest {
                _movieDetail.value = it;
            }
        }
    }

    fun setFav(movie: Movie){
        viewModelScope.launch {
            favoriteUseCase.setFavorite(movie.toEntity())
        }
        getFav(movie.imdbID)
    }
    fun deleteFav(movie: Movie){
        viewModelScope.launch {
            favoriteUseCase.deleteFavorite(movie.toEntity())
        }
        getFav(movie.imdbID)
    }
}