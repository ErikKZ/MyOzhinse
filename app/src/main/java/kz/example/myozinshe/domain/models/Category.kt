package kz.example.myozinshe.domain.models


import com.google.gson.annotations.SerializedName

data class Category(
    @SerializedName("fileId")
    val fileId: Any, // null
    @SerializedName("id")
    val id: Int, // 8
    @SerializedName("link")
    val link: Any, // null
    @SerializedName("movieCount")
    val movieCount: Any, // null
    @SerializedName("name")
    val name: String // Толықметрлі мультфильмдер
)