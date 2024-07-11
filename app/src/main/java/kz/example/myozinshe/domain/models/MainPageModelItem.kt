package kz.example.myozinshe.domain.models


import com.google.gson.annotations.SerializedName

data class MainPageModelItem(
    @SerializedName("categoryId")
    val categoryId: Int,
    @SerializedName("categoryName")
    val categoryName: String,
    @SerializedName("movies")
    val movies: List<Movy>
)