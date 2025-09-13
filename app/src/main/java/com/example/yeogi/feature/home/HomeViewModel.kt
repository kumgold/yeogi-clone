package com.example.yeogi.feature.home

import androidx.lifecycle.ViewModel
import com.example.yeogi.data.repository.SharedRepository

class HomeViewModel : ViewModel() {
    val repository = SharedRepository()

    val accommodationList = repository.getAccommodations()
    val serviceCategoryList = repository.getServiceCategory()
}