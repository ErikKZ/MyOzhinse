package kz.example.myozinshe.domain.models


import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("categories")
    val categories: List<Category>,
    @SerializedName("categoryAges")
    val categoryAges: List<CategoryAge>,
    @SerializedName("createdDate")
    val createdDate: String, // 2022-01-31T05:09:15.703+00:00
    @SerializedName("description")
    val description: String, // Махамбет
    @SerializedName("director")
    val director: String, // Қасиет Сақиолла
    @SerializedName("favorite")
    val favorite: Boolean, // true
    @SerializedName("genres")
    val genres: List<Genre>,
    @SerializedName("id")
    val id: Int, // 109
    @SerializedName("keyWords")
    val keyWords: String, // Махамбет батыр
    @SerializedName("lastModifiedDate")
    val lastModifiedDate: String, // 2022-07-14T05:50:03.680+00:00
    @SerializedName("movieType")
    val movieType: String, // MOVIE
    @SerializedName("name")
    val name: String, // Махамбет
    @SerializedName("poster")
    val poster: Poster,
    @SerializedName("producer")
    val producer: String, // Қасиет Сақиолла
    @SerializedName("screenshots")
    val screenshots: List<Screenshot>,
    @SerializedName("seasonCount")
    val seasonCount: Int, // 0
    @SerializedName("seriesCount")
    val seriesCount: Int, // 0
    @SerializedName("timing")
    val timing: Int, // 45
    @SerializedName("trend")
    val trend: Boolean, // true
    @SerializedName("video")
    val video: Video,
    @SerializedName("watchCount")
    val watchCount: Int, // 5754
    @SerializedName("year")
    val year: Int // 2020
)