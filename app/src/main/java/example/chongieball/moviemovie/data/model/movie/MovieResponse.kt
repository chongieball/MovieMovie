package example.chongieball.moviemovie.data.model.movie


import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val movies: List<Movie>,
    @SerializedName("total_result")
    val totalResults: Int,
    @SerializedName("total_pages")
    val totalPages: Int
)