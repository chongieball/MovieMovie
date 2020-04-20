package example.chongieball.moviemovie.data.repository

import example.chongieball.moviemovie.data.model.genre.GenreResponse
import example.chongieball.moviemovie.data.model.movie.DetailMovieResponse
import example.chongieball.moviemovie.data.model.movie.MovieResponse
import example.chongieball.moviemovie.data.model.review.UserReviewResponse
import example.chongieball.moviemovie.data.source.remote.RemoteSource
import io.reactivex.Observable

class RepositoryImpl(private val remoteSource: RemoteSource) : Repository {

    override fun genres(): Observable<GenreResponse> = remoteSource.genres()

    override fun movies(genre: String, page: Int): Observable<MovieResponse> = remoteSource.movies(genre, page)

    override fun detailMovie(movieId: Int): Observable<DetailMovieResponse> = remoteSource.detailMovie(movieId)

    override fun movieUserReviews(movieId: Int, page: Int): Observable<UserReviewResponse> = remoteSource.movieUserReviews(movieId, page)
}