package kz.example.myozinshe.domain.models


import com.google.gson.annotations.SerializedName

data class CategoryAge(
    @SerializedName("fileId")
    val fileId: Int, // 257
    @SerializedName("id")
    val id: Int, // 2
    @SerializedName("link")
    val link: String, // http://api.ozinshe.com/core/public/V1/show/257
    @SerializedName("movieCount")
    val movieCount: Any, // null
    @SerializedName("name")
    val name: String // 10-12
)