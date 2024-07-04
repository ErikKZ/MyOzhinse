package kz.example.myozinshe.domain.models


import com.google.gson.annotations.SerializedName

data class ChangePasswordRequest(
    @SerializedName("password")
    val password: String // string
)