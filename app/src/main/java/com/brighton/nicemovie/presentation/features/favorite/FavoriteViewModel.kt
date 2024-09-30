package com.brighton.nicemovie.presentation.features.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.brighton.nicemovie.domain.movie.model.Movie
import com.brighton.nicemovie.domain.movie.usecase.FavoriteUseCase
import com.syauqi.watcheez.core.data.source.local.entity.toModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(favoriteUseCase: FavoriteUseCase):ViewModel() {
    val movies: Flow<List<Movie>> = favoriteUseCase.getFavorites().map { it.map { data -> data.toModel() } }
}