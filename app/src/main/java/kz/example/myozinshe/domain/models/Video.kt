package kz.example.myozinshe.domain.models


import com.google.gson.annotations.SerializedName

data class Video(
    @SerializedName("id")
    val id: Int, // 365
    @SerializedName("link")
    val link: String, // Kq0dkn0W0jE
    @SerializedName("number")
    val number: Int, // 0
    @SerializedName("seasonId")
    val seasonId: Any // null
)