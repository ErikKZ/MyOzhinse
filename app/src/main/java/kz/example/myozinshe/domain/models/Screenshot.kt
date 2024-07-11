package kz.example.myozinshe.domain.models


import com.google.gson.annotations.SerializedName

data class Screenshot(
    @SerializedName("fileId")
    val fileId: Int, // 593
    @SerializedName("id")
    val id: Int, // 129
    @SerializedName("link")
    val link: String, // http://api.ozinshe.com/core/public/V1/show/593
    @SerializedName("movieId")
    val movieId: Int // 109
)