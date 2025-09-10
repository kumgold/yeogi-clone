package com.example.yeogi.data

import java.time.LocalDate

data class RecentSearch(
    val id: Int,
    val keyword: String,
    val guest: Int,
    val startDate: LocalDate,
    val endDate: LocalDate
)

val dummyDomesticRecentSearch = mutableListOf(
    RecentSearch(
        id = 1,
        keyword = "강릉 호텔",
        guest = 2,
        startDate = LocalDate.of(2025, 8, 1),
        endDate = LocalDate.of(2025, 8, 2)
    ),
    RecentSearch(
        id = 2,
        keyword = "서울 호텔",
        guest = 2,
        startDate = LocalDate.of(2025, 10, 9),
        endDate = LocalDate.of(2025, 10, 11)
    ),
    RecentSearch(
        id = 3,
        keyword = "부산 호텔",
        guest = 2,
        startDate = LocalDate.of(2025, 11, 1),
        endDate = LocalDate.of(2025, 11, 6)
    ),
)

val dummyOverseasRecentSearch = mutableListOf(
    RecentSearch(
        id = 4,
        keyword = "도쿄, 일본",
        guest = 2,
        startDate = LocalDate.of(2025, 8, 1),
        endDate = LocalDate.of(2025, 8, 2)
    ),
    RecentSearch(
        id = 5,
        keyword = "방콕, 태국",
        guest = 2,
        startDate = LocalDate.of(2025, 10, 9),
        endDate = LocalDate.of(2025, 10, 11)
    ),
    RecentSearch(
        id = 6,
        keyword = "로스엔젤레스, 미국",
        guest = 2,
        startDate = LocalDate.of(2025, 11, 1),
        endDate = LocalDate.of(2025, 11, 6)
    ),
)