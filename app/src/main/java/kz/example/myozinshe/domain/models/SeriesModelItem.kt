package kz.example.myozinshe.domain.models


import com.google.gson.annotations.SerializedName

data class SeriesModelItem(
    @SerializedName("id")
    val id: Int, // 0
    @SerializedName("movieId")
    val movieId: Int, // 0
    @SerializedName("number")
    val number: Int, // 0
    @SerializedName("videos")
    val videos: List<Video>
)