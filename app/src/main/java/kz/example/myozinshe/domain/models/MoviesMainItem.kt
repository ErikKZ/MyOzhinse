package kz.example.myozinshe.domain.models

import com.google.gson.annotations.SerializedName

data class MoviesMainItem(
    @SerializedName("fileId")
    val fileId: Int, // 0
    @SerializedName("id")
    val id: Int, // 0
    @SerializedName("link")
    val link: String, // string
    @SerializedName("movie")
    val movie: MovieInfoResponse,
    @SerializedName("sortOrder")
    val sortOrder: Int // 0
)
