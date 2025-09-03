package com.example.yeogi.dummy

data class RecommendedRegion(
    val name: String,
    val imageUrl: String
)

// 추천 검색어 더미 데이터
val dummyKeywords = listOf(
    "에버랜드", "롯데월드", "스키장", "워터파크", "서울N타워", "아쿠아리움", "제주 액티비티", "경주월드"
)

// 추천 지역 더미 데이터
val dummyRegions = listOf(
    RecommendedRegion("서울", "https://picsum.photos/seed/seoul/200"),
    RecommendedRegion("부산", "https://picsum.photos/seed/busan/200"),
    RecommendedRegion("제주", "https://picsum.photos/seed/jeju/200"),
    RecommendedRegion("강릉", "https://picsum.photos/seed/gangneung/200"),
    RecommendedRegion("경주", "https://picsum.photos/seed/gyeongju/200"),
    RecommendedRegion("가평", "https://picsum.photos/seed/gapyeong/200"),
    RecommendedRegion("여수", "https://picsum.photos/seed/yeosu/200")
)