package com.brighton.nicemovie.core.di

import com.syauqi.watcheez.core.data.repositories.MovieRepository
import com.brighton.nicemovie.domain.movie.repository.IMovieRepository
import com.brighton.nicemovie.domain.movie.usecase.FavoriteUseCase
import com.brighton.nicemovie.domain.movie.usecase.MovieInteractor
import com.brighton.nicemovie.domain.movie.usecase.MovieUseCase

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [NetworkModule::class, DatabaseModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindsMovieUseCase(movieInteractor: MovieInteractor): MovieUseCase
    @Binds
    abstract fun bindsFavoriteUseCase(movieInteractor: MovieInteractor): FavoriteUseCase
    @Binds
    abstract fun bindsMovieRepository(movieRepository: MovieRepository): IMovieRepository
}