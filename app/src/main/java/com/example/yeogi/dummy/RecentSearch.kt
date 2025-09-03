package com.example.yeogi.dummy

data class RecentSearch(
    val id: Int,
    val keyword: String,
    val details: String
)

val dummyDomesticRecentSearch = mutableListOf(
    RecentSearch(1, "강릉 호텔", "09.09(목) - 09.11(금), 성인 2"),
    RecentSearch(2, "롯데호텔 제주", "10.10(목) - 10.11(금), 성인 2"),
    RecentSearch(3, "서울 신라호텔", "11.23(토) - 11.25(월), 성인 2, 아동 1")
)

val dummyOverseasRecentSearch = mutableListOf(
    RecentSearch(4, "도쿄, 일본", "11.15(금) - 11.18(월), 성인 2"),
    RecentSearch(5, "방콕, 태국", "12.24(화) - 12.28(토), 성인 2"),
    RecentSearch(6, "로스엔젤레스, 미국", "01.10(금) - 01.15(수), 성인 1")
)