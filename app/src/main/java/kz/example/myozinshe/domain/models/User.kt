package kz.example.myozinshe.domain.models

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("email")
    val email: String, // string
    @SerializedName("id")
    val id: Int // 0
)

