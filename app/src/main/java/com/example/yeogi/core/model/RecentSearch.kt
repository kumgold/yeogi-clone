package com.example.yeogi.core.model

import com.google.gson.annotations.SerializedName

data class RecentSearch(
    @SerializedName("id") val id: Int,
    @SerializedName("keyword") val keyword: String,
    @SerializedName("guest") val guest: Int,
    @SerializedName("startDate") val startDate: String,
    @SerializedName("endDate") val endDate: String
)
