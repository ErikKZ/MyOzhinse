package kz.example.myozinshe.domain.models


import com.google.gson.annotations.SerializedName

data class SearchResponseModelItem(
    @SerializedName("categories")
    val categories: List<Category>,
    @SerializedName("categoryAges")
    val categoryAges: List<CategoryAge>,
    @SerializedName("createdDate")
    val createdDate: String, // 2024-07-02T18:07:23.302Z
    @SerializedName("description")
    val description: String, // string
    @SerializedName("director")
    val director: String, // string
    @SerializedName("favorite")
    val favorite: Boolean, // true
    @SerializedName("genres")
    val genres: List<Genre>,
    @SerializedName("id")
    val id: Int, // 0
    @SerializedName("keyWords")
    val keyWords: String, // string
    @SerializedName("lastModifiedDate")
    val lastModifiedDate: String, // 2024-07-02T18:07:23.302Z
    @SerializedName("movieType")
    val movieType: String, // MOVIE
    @SerializedName("name")
    val name: String, // string
    @SerializedName("poster")
    val poster: Poster,
    @SerializedName("producer")
    val producer: String, // string
    @SerializedName("screenshots")
    val screenshots: List<Screenshot>,
    @SerializedName("seasonCount")
    val seasonCount: Int, // 0
    @SerializedName("seriesCount")
    val seriesCount: Int, // 0
    @SerializedName("timing")
    val timing: Int, // 0
    @SerializedName("trend")
    val trend: Boolean, // true
    @SerializedName("video")
    val video: Video,
    @SerializedName("watchCount")
    val watchCount: Int, // 0
    @SerializedName("year")
    val year: Int // 0
)