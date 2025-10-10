package com.example.yeogi.feature.home

import androidx.lifecycle.ViewModel
import com.example.yeogi.core.data.repository.SharedRepository
import com.example.yeogi.feature.home.data.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    private val sharedRepository = SharedRepository
    private val repository = HomeRepository()

    val accommodationList = sharedRepository.getAccommodations()
    val serviceCategoryList = repository.getServiceCategories()
}