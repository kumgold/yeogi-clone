package com.example.yeogi.feature.home

import androidx.lifecycle.ViewModel
import com.example.yeogi.core.repository.SharedRepository
import com.example.yeogi.feature.home.data.repository.HomeRepository

class HomeViewModel : ViewModel() {
    private val sharedRepository = SharedRepository
    private val repository = HomeRepository()

    val accommodationList = sharedRepository.getAccommodations()
    val serviceCategoryList = repository.getServiceCategories()
}