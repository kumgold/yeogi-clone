package com.example.yeogi.core.model

import java.time.LocalDate

data class RecentSearch(
    val id: Int,
    val keyword: String,
    val guest: Int,
    val startDate: LocalDate,
    val endDate: LocalDate
)
