package kz.example.myozinshe.domain.models


import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("email")
    val email: String, // string
    @SerializedName("password")
    val password: String // string
)