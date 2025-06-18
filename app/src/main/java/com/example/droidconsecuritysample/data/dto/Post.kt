package com.example.droidconsecuritysample.data.dto

import com.google.gson.annotations.SerializedName

/**
 * @author taiful
 * @since 12/6/25
 */
data class Post(
    @SerializedName("userId")
    val userId: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("body")
    val body: String
)