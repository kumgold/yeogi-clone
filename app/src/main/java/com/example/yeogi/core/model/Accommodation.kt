package com.example.yeogi.core.model

data class Accommodation(
    val id: Int,
    val name: String,
    val rating: Double,
    val reviewCount: Int,
    val price: Int,
    val imageUrl: String,
    val category: String,
    val address: String,
    val isSpecialPrice: Boolean = false,
    val coordinateX: Double = 0.0, // 경도 (Longitude)
    val coordinateY: Double = 0.0,  // 위도 (Latitude)
    val facilities: List<Facility>,
    val checkInTime: String,
    val checkOutTime: String,
    val notice: String,
    val usageInfo: String,
    val reviews: List<Review>
)

