package kz.example.myozinshe.domain.models


import com.google.gson.annotations.SerializedName

data class Genre(
    @SerializedName("fileId")
    val fileId: Int, // 661
    @SerializedName("id")
    val id: Int, // 58
    @SerializedName("link")
    val link: String, // http://api.ozinshe.com/core/public/V1/show/661
    @SerializedName("movieCount")
    val movieCount: Any, // null
    @SerializedName("name")
    val name: String // Отбасымен көретіндер
)