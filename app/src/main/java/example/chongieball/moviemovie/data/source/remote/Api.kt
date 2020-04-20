package example.chongieball.moviemovie.data.source.remote

import example.chongieball.moviemovie.data.model.genre.GenreResponse
import example.chongieball.moviemovie.data.model.movie.DetailMovieResponse
import example.chongieball.moviemovie.data.model.movie.MovieResponse
import example.chongieball.moviemovie.data.model.review.UserReviewResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    @GET("genre/movie/list")
    fun genres(): Observable<GenreResponse>

    @GET("discover/movie")
    fun movies(@Query("with_genres") genre: String, @Query("page") page: Int): Observable<MovieResponse>

    @GET("movie/{movieId}?append_to_response=videos")
    fun detailMovie(@Path("movieId") movieId: Int): Observable<DetailMovieResponse>

    @GET("movie/{movieId}/reviews")
    fun movieUserReviews(@Path("movieId") movieId: Int, @Query("page") page: Int): Observable<UserReviewResponse>


}