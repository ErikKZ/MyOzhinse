package kz.example.myozinshe.domain.models

import com.google.gson.annotations.SerializedName

data class Movy(
    @SerializedName("categories")
    val categories: List<Category>,
    @SerializedName("categoryAges")
    val categoryAges: List<CategoryAge>,
    @SerializedName("createdDate")
    val createdDate: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("director")
    val director: String,
    @SerializedName("favorite")
    val favorite: Boolean,
    @SerializedName("genres")
    val genres: List<Genre>,
    @SerializedName("id")
    val id: Int,
    @SerializedName("keyWords")
    val keyWords: String,
    @SerializedName("lastModifiedDate")
    val lastModifiedDate: String,
    @SerializedName("movieType")
    val movieType: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("poster")
    val poster: Poster,
    @SerializedName("producer")
    val producer: String,
    @SerializedName("screenshots")
    val screenshots: List<Screenshot>,
    @SerializedName("seasonCount")
    val seasonCount: Int,
    @SerializedName("seriesCount")
    val seriesCount: Int,
    @SerializedName("timing")
    val timing: Int,
    @SerializedName("trend")
    val trend: Boolean,
    @SerializedName("video")
    val video: Video,
    @SerializedName("watchCount")
    val watchCount: Int,
    @SerializedName("year")
    val year: Int
)
