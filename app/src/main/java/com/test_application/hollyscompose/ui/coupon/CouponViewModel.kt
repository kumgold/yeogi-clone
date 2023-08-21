package com.test_application.hollyscompose.ui.coupon

import androidx.lifecycle.ViewModel
import com.test_application.hollyscompose.model.HollysRepository

class CouponViewModel : ViewModel() {
    private val repository = HollysRepository()

    val couponList = repository.couponList
}