package com.example.yeogi.dummy

data class Accommodation(
    val id: Int,
    val name: String,
    val rating: Double,
    val reviewCount: Int,
    val price: String,
    val imageUrl: String,
    val isSpecialPrice: Boolean = false,
    val coordinateX: Double = 0.0,
    val coordinateY: Double = 0.0
)

val dummyAccommodation = listOf(
    // == 서울 ==
    Accommodation(
        1, "호텔 신라 서울", 4.6, 6800, "450,000원", "https://picsum.photos/seed/1/400",
        true, 127.0093, 37.5551
    ),
    Accommodation(
        2, "롯데호텔 서울", 4.5, 7500, "380,000원", "https://picsum.photos/seed/2/400",
        false, 126.9804, 37.5658
    ),
    Accommodation(
        3, "파크 하얏트 서울", 4.6, 2400, "550,000원", "https://picsum.photos/seed/3/400",
        true, 127.0632, 37.5085
    ),
    Accommodation(
        4, "포시즌스 호텔 서울", 4.7, 3300, "600,000원", "https://picsum.photos/seed/4/400",
        true, 126.9760, 37.5710
    ),
    Accommodation(
        5, "시그니엘 서울", 4.7, 3400, "700,000원", "https://picsum.photos/seed/5/400",
        false, 127.1031, 37.5125
    ),
    Accommodation(
        6, "그랜드 워커힐 서울", 4.4, 5400, "320,000원", "https://picsum.photos/seed/6/400",
        false, 127.1065, 37.5552
    ),
    Accommodation(
        7, "반얀트리 클럽 앤 스파 서울", 4.6, 2100, "800,000원", "https://picsum.photos/seed/7/400",
        true, 127.0063, 37.5492
    ),
    Accommodation(
        8, "조선 팰리스, 럭셔리 컬렉션 호텔, 서울 강남", 4.7, 1300, "650,000원", "https://picsum.photos/seed/8/400",
        true, 127.0452, 37.5050
    ),
    Accommodation(
        9, "레스케이프 호텔", 4.5, 3000, "280,000원", "https://picsum.photos/seed/9/400",
        false, 126.9800, 37.5594
    ),
    Accommodation(
        10, "안다즈 서울 강남", 4.6, 1800, "480,000원", "https://picsum.photos/seed/10/400",
        false, 127.0284, 37.5250
    ),
    // == 부산 ==
    Accommodation(
        11, "파크 하얏트 부산", 4.6, 4200, "500,000원", "https://picsum.photos/seed/11/400",
        true, 129.1419, 35.1565
    ),
    Accommodation(
        12, "시그니엘 부산", 4.7, 3600, "650,000원", "https://picsum.photos/seed/12/400",
        false, 129.1764, 35.1614
    ),
    Accommodation(
        13, "힐튼 부산", 4.6, 7300, "480,000원", "https://picsum.photos/seed/13/400",
        true, 129.2223, 35.1918
    ),
    Accommodation(
        14, "그랜드 조선 부산", 4.6, 3200, "400,000원", "https://picsum.photos/seed/14/400",
        false, 129.1625, 35.1593
    ),
    // == 제주 ==
    Accommodation(
        15, "호텔 신라 제주", 4.6, 8500, "500,000원", "https://picsum.photos/seed/15/400",
        false, 126.4069, 33.2487
    ),
    Accommodation(
        16, "롯데호텔 제주", 4.5, 9200, "420,000원", "https://picsum.photos/seed/16/400",
        true, 126.4101, 33.2464
    ),
    Accommodation(
        17, "그랜드 조선 제주", 4.6, 2900, "450,000원", "https://picsum.photos/seed/17/400",
        false, 126.4087, 33.2452
    ),
    Accommodation(
        18, "해비치 호텔앤리조트 제주", 4.6, 6100, "350,000원", "https://picsum.photos/seed/18/400",
        true, 126.9213, 33.3005
    ),
    // == 인천 ==
    Accommodation(
        19, "파라다이스시티", 4.7, 13000, "550,000원", "https://picsum.photos/seed/19/400",
        true, 126.4526, 37.4727
    ),
    // == 강원 ==
    Accommodation(
        20, "씨마크 호텔 강릉", 4.6, 2800, "600,000원", "https://picsum.photos/seed/20/400",
        false, 128.9221, 37.7911
    ),
    Accommodation(
        21, "스카이베이 호텔 경포", 4.3, 11000, "250,000원", "https://picsum.photos/seed/21/400",
        true, 128.8993, 37.8010
    ),
    Accommodation(
        22, "켄싱턴호텔 설악", 4.4, 3800, "200,000원", "https://picsum.photos/seed/22/400",
        false, 128.4878, 38.1915
    ),
    // == 경주 ==
    Accommodation(
        23, "라한셀렉트 경주", 4.5, 4900, "300,000원", "https://picsum.photos/seed/23/400",
        true, 129.2818, 35.8398
    ),
    Accommodation(
        24, "힐튼 경주", 4.4, 4200, "280,000원", "https://picsum.photos/seed/24/400",
        false, 129.2845, 35.8423
    ),
    // == 여수 ==
    Accommodation(
        25, "소노캄 여수", 4.5, 5100, "320,000원", "https://picsum.photos/seed/25/400",
        false, 127.7505, 34.7391
    ),
    Accommodation(
        26, "여수 베네치아 호텔&리조트", 4.4, 4300, "220,000원", "https://picsum.photos/seed/26/400",
        true, 127.7523, 34.7431
    ),
    // == 거제 ==
    Accommodation(
        27, "소노캄 거제", 4.5, 7500, "350,000원", "https://picsum.photos/seed/27/400",
        true, 128.6941, 34.8517
    ),
    // == 기타 지역 ==
    Accommodation(
        28, "몬드리안 서울 이태원", 4.6, 2600, "400,000원", "https://picsum.photos/seed/28/400",
        false, 126.9912, 37.5353
    ),
    Accommodation(
        29, "호텔 나루 서울 엠갤러리", 4.7, 1500, "450,000원", "https://picsum.photos/seed/29/400",
        true, 126.9405, 37.5381
    ),
    Accommodation(
        30, "메이필드호텔 서울", 4.5, 5200, "280,000원", "https://picsum.photos/seed/30/400",
        false, 126.8048, 37.5611
    )
)
