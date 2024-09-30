package com.brighton.nicemovie.presentation.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.brighton.nicemovie.domain.movie.model.Movie
import com.brighton.nicemovie.domain.movie.usecase.MovieUseCase
import com.syauqi.watcheez.core.data.source.local.entity.MovieEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(movieUseCase: MovieUseCase):ViewModel() {
    private val _query = MutableStateFlow<String?>(null)
    val query: StateFlow<String?> get() = _query

    @OptIn(ExperimentalCoroutinesApi::class)
    val movies: Flow<PagingData<Movie>> = _query.flatMapLatest { query ->
        movieUseCase.getMovies(query).cachedIn(viewModelScope)
    }

    fun searchMovies(newQuery: String?) {
        _query.value = newQuery
    }
}