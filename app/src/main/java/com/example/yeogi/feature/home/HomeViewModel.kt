package com.example.yeogi.feature.home

import androidx.lifecycle.ViewModel
import com.example.yeogi.core.data.repository.SharedRepository
import com.example.yeogi.feature.home.data.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val sharedRepository: SharedRepository,
    private val homeRepository: HomeRepository
) : ViewModel() {
    val accommodationList = sharedRepository.getAccommodations()
    val serviceCategoryList = homeRepository.getServiceCategories()
}