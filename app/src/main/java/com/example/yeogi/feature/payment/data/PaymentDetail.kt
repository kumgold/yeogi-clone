package com.example.yeogi.feature.payment.data

data class PaymentDetails(
    val accommodationName: String = "서울 시그니처 호텔",
    val roomName: String = "디럭스 더블룸",
    val checkInDate: String = "2025.09.20 (토)",
    val checkOutDate: String = "2025.09.21 (일)",
    val roomPrice: Int = 180000,
    val fees: Int = 5000,
    val totalPrice: Int = 185000
)
