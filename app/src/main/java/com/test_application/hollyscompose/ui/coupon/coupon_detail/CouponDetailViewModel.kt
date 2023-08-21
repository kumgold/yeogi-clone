package com.test_application.hollyscompose.ui.coupon.coupon_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test_application.hollyscompose.data.Coupon
import com.test_application.hollyscompose.model.HollysRepository

class CouponDetailViewModel : ViewModel() {
    private val repository = HollysRepository()

    private val _coupon = MutableLiveData<Coupon>()
    val coupon: LiveData<Coupon> = _coupon

    fun getCoupon(id: Long) {
        _coupon.value = repository.getCoupon(id)
    }
}