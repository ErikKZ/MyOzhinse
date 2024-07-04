package kz.example.myozinshe.domain.models


import com.google.gson.annotations.SerializedName

data class SeriesModel(
    @SerializedName("id")
    val id: Int, // 0
    @SerializedName("movieId")
    val movieId: Int, // 0
    @SerializedName("number")
    val number: Int, // 0
    @SerializedName("videos")
    val videos: List<Video>
) {
    data class Video(
        @SerializedName("id")
        val id: Int, // 0
        @SerializedName("link")
        val link: String, // string
        @SerializedName("number")
        val number: Int, // 0
        @SerializedName("seasonId")
        val seasonId: Int // 0
    )
}