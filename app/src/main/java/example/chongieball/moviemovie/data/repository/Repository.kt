package example.chongieball.moviemovie.data.repository

import example.chongieball.moviemovie.data.model.genre.GenreResponse
import example.chongieball.moviemovie.data.model.movie.DetailMovieResponse
import example.chongieball.moviemovie.data.model.movie.MovieResponse
import example.chongieball.moviemovie.data.model.review.UserReviewResponse
import io.reactivex.Observable


interface Repository {

    fun genres(): Observable<GenreResponse>

    fun movies(genre: String, page: Int): Observable<MovieResponse>

    fun detailMovie(movieId: Int): Observable<DetailMovieResponse>

    fun movieUserReviews(movieId: Int, page: Int): Observable<UserReviewResponse>
}