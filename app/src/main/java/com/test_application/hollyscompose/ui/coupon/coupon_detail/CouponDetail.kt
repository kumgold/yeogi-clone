package com.test_application.hollyscompose.ui.coupon.coupon_detail

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun CouponDetail(
    couponId: Long
) {
    val viewModel = viewModel<CouponDetailViewModel>()

    viewModel.getCoupon(couponId)
}