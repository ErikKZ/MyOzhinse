package kz.example.myozinshe.domain.models


import com.google.gson.annotations.SerializedName

data class CategoryMovieResponse(
    @SerializedName("content")
    val content: List<Content>,
    @SerializedName("empty")
    val empty: Boolean,
    @SerializedName("first")
    val first: Boolean,
    @SerializedName("last")
    val last: Boolean,
    @SerializedName("number")
    val number: Int,
    @SerializedName("numberOfElements")
    val numberOfElements: Int,
    @SerializedName("pageable")
    val pageable: Pageable,
    @SerializedName("size")
    val size: Int,
    @SerializedName("sort")
    val sort: Sort,
    @SerializedName("totalElements")
    val totalElements: Int,
    @SerializedName("totalPages")
    val totalPages: Int
) {
    data class Content(
        @SerializedName("categories")
        val categories: List<Category>,
        @SerializedName("categoryAges")
        val categoryAges: List<CategoryAge>,
        @SerializedName("createdDate")
        val createdDate: String, // 2024-07-02T08:59:13.001Z
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
        val lastModifiedDate: String, // 2024-07-02T08:59:13.001Z
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
    ) {
        data class Category(
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

        data class CategoryAge(
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

        data class Genre(
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

        data class Poster(
            @SerializedName("fileId")
            val fileId: Int, // 0
            @SerializedName("id")
            val id: Int, // 0
            @SerializedName("link")
            val link: String, // string
            @SerializedName("movieId")
            val movieId: Int // 0
        )

        data class Screenshot(
            @SerializedName("fileId")
            val fileId: Int, // 0
            @SerializedName("id")
            val id: Int, // 0
            @SerializedName("link")
            val link: String, // string
            @SerializedName("movieId")
            val movieId: Int // 0
        )

        data class Video(
            @SerializedName("id")
            val id: Int, // 0
            @SerializedName("link")
            val link: String, // string
            @SerializedName("number")
            val number: Int, // 0
            @SerializedName("seasonId")
            val seasonId: Int // 0
        )
    }

    data class Pageable(
        @SerializedName("offset")
        val offset: Int,
        @SerializedName("pageNumber")
        val pageNumber: Int,
        @SerializedName("pageSize")
        val pageSize: Int,
        @SerializedName("paged")
        val paged: Boolean,
        @SerializedName("sort")
        val sort: Sort,
        @SerializedName("unpaged")
        val unpaged: Boolean
    ) {
        data class Sort(
            @SerializedName("empty")
            val empty: Boolean,
            @SerializedName("sorted")
            val sorted: Boolean,
            @SerializedName("unsorted")
            val unsorted: Boolean
        )
    }

    data class Sort(
        @SerializedName("empty")
        val empty: Boolean,
        @SerializedName("sorted")
        val sorted: Boolean,
        @SerializedName("unsorted")
        val unsorted: Boolean
    )
}