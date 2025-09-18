package com.example.yeogi.feature.room.data.remote

data class Room(
    val id: Int,
    val accommodationId: Int,
    val name: String,
    val price: String,
    val originalPrice: String, // 할인 전 가격
    val discountRate: String, // 할인율
    val imageUrl: String,
    val capacity: String, // ex) "기준 2인 / 최대 3인"
    val features: List<String>, // ex) ["오션뷰", "넷플릭스 이용가능"]
    val bookingStatus: String // ex) "예약가능", "마감임박"
)
