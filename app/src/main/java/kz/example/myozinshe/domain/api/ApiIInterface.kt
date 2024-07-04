package kz.example.myozinshe.domain.api

import kz.example.myozinshe.domain.models.CategoryMovieResponse
import kz.example.myozinshe.domain.models.ChangePasswordRequest
import kz.example.myozinshe.domain.models.GenreResponse
import kz.example.myozinshe.domain.models.LoginResponse
import kz.example.myozinshe.domain.models.MovieIdModel
import kz.example.myozinshe.domain.models.SearchResponseModel
import kz.example.myozinshe.domain.models.SeriesModel
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

interface ApiIInterface {
    @POST("/core/V1/signin")
    suspend fun login(@Body loginRequest: LoginResponse): Response<LoginResponse>

    @POST("/core/V1/signup")
    suspend fun regIn(@Body loginRequest: LoginResponse): Response<LoginResponse>

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
    ): SearchResponseModel

    @HTTP(method = "PUT", path = "/core/V1/user/profile/", hasBody = true)
    suspend fun updateUserInfo(
        @Header("Authorization") token: String,
        @Body requestUserInfo: UserInfoRequest
    ): UserInfo

//    @HTTP(method = "PUT", path = "/core/V1/user/profile/", hasBody = true)   // не работает в приложении
//    suspend fun updateUserPassword(
//        @Header("Authorization") token: String,
//        @Body requestUserInfo: ChangePasswordRequest
//    ): UserInfo  //***********

    @POST("/core/V1/favorite")
    suspend fun addToFavorite(
        @Header("Authorization") token: String,
        @Body movieId: MovieIdModel
    ): MovieIdModel

    @HTTP(method = "DELETE", path = "/core/V1/favorite", hasBody = true)
    suspend fun deleteAtFavorite(
        @Header("Authorization") token: String,
        @Body movieId: MovieIdModel
    ): Response<Unit>

    @GET("/core/V1/seasson/{movieId}")
    suspend fun getSeries(
        @Header("Authorization") token: String,
        @Path("movieId") movieId: Int
    ): SeriesModel

    @GET("/core/V1/movies/{id}")
    suspend fun movieWithId(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): MovieIdModel

    @GET("/core/V1/genres")
    suspend fun getGenre(
        @Header("Authorization") token: String
    ): GenreResponse

    @GET("/core/V1/movies/main")          //**
    suspend fun moviesMainPage(
        @Header("Authorization") token: String
    ): List<MovieIdModel>

    @GET("/core/V1/favorite")
    suspend fun getFavorite(
        @Header("Authorization") token: String
    ): List<MovieIdModel>

    @GET("/core/V1/user/profile")
    suspend fun getUserInfo(
        @Header("Authorization") token: String
    ): UserInfo

    @GET("/core/V1/movies_main")        //**
    suspend fun moviesMain(
        @Header("Authorization") token: String
    ): List<MovieIdModel>

}