package kz.example.myozinshe.domain.models


import com.google.gson.annotations.SerializedName

data class Screenshot(
    @SerializedName("fileId")
    val fileId: Int, // 0
    @SerializedName("id")
    val id: Int, // 0
    @SerializedName("link")
    val link: String, // string
    @SerializedName("movieId")
    val movieId: Int // 0
)