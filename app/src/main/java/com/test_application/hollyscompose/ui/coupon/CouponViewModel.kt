package com.test_application.hollyscompose.ui.coupon

import androidx.lifecycle.ViewModel
import com.test_application.hollyscompose.data.Coupon

class CouponViewModel : ViewModel() {
    val couponList = listOf(
        Coupon(
            title = "멤버십 쿠폰",
            name = "아메리카노 1 + 1 쿠폰",
            startDate = "2023-07-01",
            expiredDate = "2023-07-31",
            store = "쿠폰 주의 사항 확인",
            isExpired = false,
            isAvailable = true
        ),
        Coupon(
            title = "멤버십 쿠폰",
            name = "아메리카노 1 + 1 쿠폰",
            startDate = "2023-06-01",
            expiredDate = "2023-06-30",
            store = "쿠폰 주의 사항 확인",
            isExpired = true,
            isAvailable = true
        ),
        Coupon(
            title = "멤버십 쿠폰",
            name = "아메리카노 1 + 1 쿠폰",
            startDate = "2023-07-01",
            expiredDate = "2023-07-31",
            store = "쿠폰 주의 사항 확인",
            isExpired = false,
            isAvailable = true
        ),
        Coupon(
            title = "멤버십 쿠폰",
            name = "아메리카노 1 + 1 쿠폰",
            startDate = "2023-06-01",
            expiredDate = "2023-06-30",
            store = "쿠폰 주의 사항 확인",
            isExpired = true,
            isAvailable = true
        )
    )
}