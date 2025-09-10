package com.example.yeogi.data

data class Accommodation(
    val id: Int,
    val name: String,
    val rating: Double,
    val reviewCount: Int,
    val price: String,
    val imageUrl: String,
    val category: String,
    val address: String,
    val isSpecialPrice: Boolean = false,
    val coordinateX: Double = 0.0, // 경도 (Longitude)
    val coordinateY: Double = 0.0  // 위도 (Latitude)
)

val dummyAccommodations = listOf(
    // == 서울 ==
    Accommodation(
        id = 1,
        name = "호텔 신라 서울",
        rating = 4.6,
        reviewCount = 6800,
        price = "450,000원",
        imageUrl = "https://picsum.photos/seed/1/400",
        category = "호텔",
        address = "서울 중구 동호로 249",
        isSpecialPrice = true,
        coordinateY = 37.5551,
        coordinateX = 127.0093
    ),
    Accommodation(
        id = 2,
        name = "롯데호텔 서울",
        rating = 4.5,
        reviewCount = 7500,
        price = "380,000원",
        imageUrl = "https://picsum.photos/seed/2/400",
        category = "호텔",
        address = "서울 중구 을지로 30",
        isSpecialPrice = false,
        coordinateY = 37.5658,
        coordinateX = 126.9804
    ),
    Accommodation(
        id = 3,
        name = "파크 하얏트 서울",
        rating = 4.6,
        reviewCount = 2400,
        price = "550,000원",
        imageUrl = "https://picsum.photos/seed/3/400",
        category = "호텔",
        address = "서울 강남구 테헤란로 606",
        isSpecialPrice = true,
        coordinateY = 37.5085,
        coordinateX = 127.0632
    ),
    Accommodation(
        id = 4,
        name = "포시즌스 호텔 서울",
        rating = 4.7,
        reviewCount = 3300,
        price = "600,000원",
        imageUrl = "https://picsum.photos/seed/4/400",
        category = "호텔",
        address = "서울 종로구 새문안로 97",
        isSpecialPrice = true,
        coordinateY = 37.5710,
        coordinateX = 126.9760
    ),
    Accommodation(
        id = 5,
        name = "시그니엘 서울",
        rating = 4.7,
        reviewCount = 3400,
        price = "700,000원",
        imageUrl = "https://picsum.photos/seed/5/400",
        category = "호텔",
        address = "서울 송파구 올림픽로 300",
        isSpecialPrice = false,
        coordinateY = 37.5125,
        coordinateX = 127.1031
    ),
    Accommodation(
        id = 6,
        name = "가평 하늘정원 펜션",
        rating = 4.8,
        reviewCount = 1250,
        price = "180,000원",
        imageUrl = "https://picsum.photos/seed/6/400",
        category = "펜션",
        address = "경기 가평군 북면",
        isSpecialPrice = true,
        coordinateY = 37.8222,
        coordinateX = 127.5222
    ),
    // == 부산 ==
    Accommodation(
        id = 11,
        name = "파크 하얏트 부산",
        rating = 4.6,
        reviewCount = 4200,
        price = "500,000원",
        imageUrl = "https://picsum.photos/seed/11/400",
        category = "호텔",
        address = "부산 해운대구 마린시티1로 51",
        isSpecialPrice = true,
        coordinateY = 35.1565,
        coordinateX = 129.1419
    ),
    Accommodation(
        id = 12,
        name = "시그니엘 부산",
        rating = 4.7,
        reviewCount = 3600,
        price = "650,000원",
        imageUrl = "https://picsum.photos/seed/12/400",
        category = "호텔",
        address = "부산 해운대구 달맞이길 30",
        isSpecialPrice = false,
        coordinateY = 35.1614,
        coordinateX = 129.1764
    ),
    Accommodation(
        id = 13,
        name = "힐튼 부산",
        rating = 4.6,
        reviewCount = 7300,
        price = "480,000원",
        imageUrl = "https://picsum.photos/seed/13/400",
        category = "리조트",
        address = "부산 기장군 기장읍 기장해안로 268-32",
        isSpecialPrice = true,
        coordinateY = 35.1918,
        coordinateX = 129.2223
    ),
    // == 제주 ==
    Accommodation(
        id = 15,
        name = "호텔 신라 제주",
        rating = 4.6,
        reviewCount = 8500,
        price = "500,000원",
        imageUrl = "https://picsum.photos/seed/15/400",
        category = "호텔",
        address = "제주 서귀포시 중문관광로72번길 75",
        isSpecialPrice = false,
        coordinateY = 33.2487,
        coordinateX = 126.4069
    ),
    Accommodation(
        id = 16,
        name = "롯데호텔 제주",
        rating = 4.5,
        reviewCount = 9200,
        price = "420,000원",
        imageUrl = "https://picsum.photos/seed/16/400",
        category = "호텔",
        address = "제주 서귀포시 중문관광로72번길 35",
        isSpecialPrice = true,
        coordinateY = 33.2464,
        coordinateX = 126.4101
    ),
    Accommodation(
        id = 17,
        name = "제주 돌담집",
        rating = 4.9,
        reviewCount = 850,
        price = "250,000원",
        imageUrl = "https://picsum.photos/seed/17/400",
        category = "펜션",
        address = "제주 제주시 구좌읍",
        isSpecialPrice = false,
        coordinateY = 33.5187,
        coordinateX = 126.8392
    ),
    // == 강원 ==
    Accommodation(
        id = 20,
        name = "씨마크 호텔 강릉",
        rating = 4.6,
        reviewCount = 2800,
        price = "600,000원",
        imageUrl = "https://picsum.photos/seed/20/400",
        category = "호텔",
        address = "강원 강릉시 해안로406번길 2",
        isSpecialPrice = false,
        coordinateY = 37.7911,
        coordinateX = 128.9221
    ),
    Accommodation(
        id = 21,
        name = "스카이베이 호텔 경포",
        rating = 4.3,
        reviewCount = 11000,
        price = "250,000원",
        imageUrl = "https://picsum.photos/seed/21/400",
        category = "호텔",
        address = "강원 강릉시 해안로 476",
        isSpecialPrice = true,
        coordinateY = 37.8010,
        coordinateX = 128.8993
    ),
    Accommodation(
        id = 22,
        name = "켄싱턴호텔 설악",
        rating = 4.4,
        reviewCount = 3800,
        price = "200,000원",
        imageUrl = "https://picsum.photos/seed/22/400",
        category = "호텔",
        address = "강원 속초시 설악산로 998",
        isSpecialPrice = false,
        coordinateY = 38.1915,
        coordinateX = 128.4878
    ),
    // == 경주 ==
    Accommodation(
        id = 23,
        name = "라한셀렉트 경주",
        rating = 4.5,
        reviewCount = 4900,
        price = "300,000원",
        imageUrl = "https://picsum.photos/seed/23/400",
        category = "호텔",
        address = "경북 경주시 보문로 338",
        isSpecialPrice = true,
        coordinateY = 35.8398,
        coordinateX = 129.2818
    ),
    Accommodation(
        id = 24,
        name = "황리단길 한옥 스테이",
        rating = 4.8,
        reviewCount = 980,
        price = "190,000원",
        imageUrl = "https://picsum.photos/seed/24/400",
        category = "게스트하우스",
        address = "경북 경주시 포석로 1080",
        isSpecialPrice = false,
        coordinateY = 35.8353,
        coordinateX = 129.2124
    ),
    // == 여수 ==
    Accommodation(
        id = 25,
        name = "소노캄 여수",
        rating = 4.5,
        reviewCount = 5100,
        price = "320,000원",
        imageUrl = "https://picsum.photos/seed/25/400",
        category = "리조트",
        address = "전남 여수시 오동도로 111",
        isSpecialPrice = false,
        coordinateY = 34.7391,
        coordinateX = 127.7505
    ),
    Accommodation(
        id = 26,
        name = "여수 베네치아 호텔&리조트",
        rating = 4.4,
        reviewCount = 4300,
        price = "220,000원",
        imageUrl = "https://picsum.photos/seed/26/400",
        category = "호텔",
        address = "전남 여수시 오동도로 61-13",
        isSpecialPrice = true,
        coordinateY = 34.7431,
        coordinateX = 127.7523
    )
)