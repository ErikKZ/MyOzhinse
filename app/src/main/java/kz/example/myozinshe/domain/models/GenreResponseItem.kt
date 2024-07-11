package kz.example.myozinshe.domain.models


import com.google.gson.annotations.SerializedName

data class GenreResponseItem(
    @SerializedName("fileId")
    val fileId: Int, // 360
    @SerializedName("id")
    val id: Int, // 4
    @SerializedName("link")
    val link: String, // http://api.ozinshe.com/core/public/V1/show/360
    @SerializedName("movieCount")
    val movieCount: Int, // 9
    @SerializedName("name")
    val name: String // Ойын-сауық
)