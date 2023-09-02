package com.test_application.hollyscompose.model

import com.test_application.hollyscompose.data.Coupon

/**
 * A fake repository
 */
class HollysRepository {
    val couponList = listOf(
        Coupon(
            id = 0L,
            title = "멤버십 쿠폰",
            name = "아메리카노 1 + 1 쿠폰",
            startDate = "2023-07-01",
            expiredDate = "2023-07-31",
            store = "쿠폰 주의 사항 확인",
            isExpired = false
        ),
        Coupon(
            id = 1L,
            title = "멤버십 쿠폰",
            name = "아메리카노 1 + 1 쿠폰",
            startDate = "2023-06-01",
            expiredDate = "2023-06-30",
            store = "쿠폰 주의 사항 확인",
            isExpired = true
        ),
        Coupon(
            id = 2L,
            title = "멤버십 쿠폰",
            name = "아메리카노 1 + 1 쿠폰",
            startDate = "2023-07-01",
            expiredDate = "2023-07-31",
            store = "쿠폰 주의 사항 확인",
            isExpired = false
        ),
        Coupon(
            id = 3L,
            title = "멤버십 쿠폰",
            name = "아메리카노 1 + 1 쿠폰",
            startDate = "2023-06-01",
            expiredDate = "2023-06-30",
            store = "쿠폰 주의 사항 확인",
            isExpired = true
        )
    )

    fun getCoupon(id: Long): Coupon {
        return couponList.first { it.id == id }
    }
}