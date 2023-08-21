package com.test_application.hollyscompose.ui.coupon.coupon_detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.test_application.hollyscompose.data.Coupon

@Composable
fun CouponDetail(
    couponId: Long
) {
    val viewModel = viewModel<CouponDetailViewModel>()
    val coupon = viewModel.coupon.observeAsState(viewModel.getCoupon(couponId))

    
}