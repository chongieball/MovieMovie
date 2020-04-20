package example.chongieball.moviemovie.data.model.video

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Videos(
    @SerializedName("results")
    val results: List<Video>
): Parcelable

@Parcelize
data class Video(
    @SerializedName("id")
    val id: String,
    @SerializedName("key")
    val key: String
): Parcelable