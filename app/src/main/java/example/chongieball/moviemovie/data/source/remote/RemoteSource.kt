package example.chongieball.moviemovie.data.source.remote


class RemoteSource(private val api: Api) {

    fun genres() = api.genres()
    fun movies(genre: String, page: Int) = api.movies(genre, page)
    fun detailMovie(movieId: Int) = api.detailMovie(movieId)
    fun movieUserReviews(movieId: Int, page: Int) = api.movieUserReviews(movieId, page)
}