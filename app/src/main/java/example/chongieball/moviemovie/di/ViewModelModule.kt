package example.chongieball.moviemovie.di

import example.chongieball.moviemovie.ui.detail.DetailMovieViewModel
import example.chongieball.moviemovie.ui.detail.userreview.UserReviewViewModel
import example.chongieball.moviemovie.ui.home.HomeViewModel
import example.chongieball.moviemovie.ui.movie.MovieViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel(get(), get()) }
    viewModel { MovieViewModel(get(), get()) }
    viewModel { DetailMovieViewModel(get(), get()) }
    viewModel { UserReviewViewModel(get(), get()) }
}