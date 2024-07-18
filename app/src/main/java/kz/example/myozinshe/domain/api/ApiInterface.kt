package kz.example.myozinshe.domain.api

import kz.example.myozinshe.domain.models.CategoryMovieResponse
import kz.example.myozinshe.domain.models.ChangePasswordRequest
import kz.example.myozinshe.domain.models.FavoriteModelItem
import kz.example.myozinshe.domain.models.GenreResponse
import kz.example.myozinshe.domain.models.LoginRequest
import kz.example.myozinshe.domain.models.LoginResponse
import kz.example.myozinshe.domain.models.MainPageModel
import kz.example.myozinshe.domain.models.MovieIdModel
import kz.example.myozinshe.domain.models.MovieInfoResponse
import kz.example.myozinshe.domain.models.MoviesMain
import kz.example.myozinshe.domain.models.SearchResponseModelItem
import kz.example.myozinshe.domain.models.SeriesModelItem
import kz.example.myozinshe.domain.models.UserInfo
import kz.example.myozinshe.domain.models.UserInfoRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {
    @POST("/auth/V1/signin")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @POST("/auth/V1/signup")
    suspend fun regIn(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @GET("/core/V1/movies/page")
    suspend fun getCategoryMovie(
        @Header("Authorization") token: String,
        @Query("categoryId") categoryId: Int,
        @Query("direction") direction: String,
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("sortField") sortField: String
    ): CategoryMovieResponse

    @GET("/core/V1/movies/search")
    suspend fun getSearchMovie(
        @Header("Authorization") token: String,
        @Query("credentials") credentials: String,
        @Query("details") details: String,
        @Query("principal") principal: String,
        @Query("search") search: String
    ): List<SearchResponseModelItem>

    @HTTP(method = "PUT", path = "/core/V1/user/profile/", hasBody = true)
    suspend fun updateUserInfo(
        @Header("Authorization") token: String,
        @Body requestUserInfo: UserInfoRequest
    ): UserInfo

    @HTTP(method = "PUT", path = "/core/V1/user/profile/changePassword", hasBody = true)   // не работает в приложении
    suspend fun updateUserPassword(
        @Header("Authorization") token: String,
        @Body requestUserInfo: ChangePasswordRequest
    ): UserInfo  //***********

    @POST("/core/V1/favorite")
    suspend fun addToFavorite(
        @Header("Authorization") token: String,
        @Body movieId: MovieIdModel
    ): MovieIdModel

    @HTTP(method = "DELETE", path = "/core/V1/favorite/", hasBody = true)
    suspend fun deleteAtFavorite(
        @Header("Authorization") token: String,
        @Body movieId: MovieIdModel
    )

    @GET("/core/V1/seasons/{movieId}")
    suspend fun getSeries(
        @Header("Authorization") token: String,
        @Path("movieId") movieId: Int
    ): List<SeriesModelItem>

    @GET("/core/V1/movies/{id}")
    suspend fun movieWithId(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): MovieInfoResponse

    @GET("/core/V1/genres")
    suspend fun getGenre(
        @Header("Authorization") token: String
    ): GenreResponse

    @GET("/core/V1/movies/main")          //**
    suspend fun moviesMainPage(
        @Header("Authorization") token: String
    ): MainPageModel
    @GET("/core/V1/favorite/")
    suspend fun getFavoriteMovies(
        @Header("Authorization") token: String
    ): List<FavoriteModelItem>

    @GET("/core/V1/user/profile")
    suspend fun getUserInfo(
        @Header("Authorization") token: String
    ): UserInfo

    @GET("/core/V1/movies_main")        //**
    suspend fun moviesMain(
        @Header("Authorization") token: String
    ): MoviesMain

}