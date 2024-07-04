package kz.example.myozinshe.domain.models


import com.google.gson.annotations.SerializedName

class GenreResponse : ArrayList<GenreResponse.GenreResponseItem>() {
    data class GenreResponseItem(
        @SerializedName("fileId")
        val fileId: Int, // 0

        @SerializedName("id")
        val id: Int, // 0

        @SerializedName("link")
        val link: String, // string

        @SerializedName("movieCount")
        val movieCount: Int, // 0

        @SerializedName("name")
        val name: String // string
    )
}