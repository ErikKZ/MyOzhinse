package kz.example.myozinshe.domain.models


import com.google.gson.annotations.SerializedName

data class UserInfo(
    @SerializedName("birthDate")
    val birthDate: String, // 2024-07-02
    @SerializedName("id")
    val id: Int, // 0
    @SerializedName("language")
    val language: String, // string
    @SerializedName("name")
    val name: String, // string
    @SerializedName("phoneNumber")
    val phoneNumber: String, // string
    @SerializedName("user")
    val user: User
) {
    data class User(
        @SerializedName("email")
        val email: String, // string
        @SerializedName("id")
        val id: Int // 0
    )
}