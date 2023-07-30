package com.test_application.hollyscompose.data

data class Coupon(
    val title: String,
    val name: String,
    val startDate: String,
    val expiredDate: String,
    val store: String,
    val isExpired: Boolean,
    val isAvailable: Boolean
)