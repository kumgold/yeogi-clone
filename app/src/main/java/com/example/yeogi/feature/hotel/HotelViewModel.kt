package com.example.yeogi.feature.hotel

import androidx.lifecycle.viewModelScope
import com.example.yeogi.core.model.Accommodation
import com.example.yeogi.core.presentation.SharedViewModel
import com.example.yeogi.feature.hotel.data.HotelCategory
import com.example.yeogi.feature.hotel.data.Region
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class HotelUiState(
    val categories: List<HotelCategory> = emptyList(),
    val regions: Map<String, List<Region>> = emptyMap(),
    val popularHotels: List<Accommodation> = emptyList(),
    val premiumHotels: List<Accommodation> = emptyList()
)

class HotelViewModel : SharedViewModel() {
    private val _uiState = MutableStateFlow(HotelUiState())
    val uiState: StateFlow<HotelUiState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            val categories = listOf(
                HotelCategory("전체", "https://images.unsplash.com/photo-1566073771259-6a8506099945"),
                HotelCategory("5성급", "https://images.unsplash.com/photo-1542314831-068cd1dbb5eb"),
                HotelCategory("리조트", "https://images.unsplash.com/photo-1571003123894-1f0594d2b5d9"),
                HotelCategory("가성비", "https://images.unsplash.com/photo-1590490359854-dfba5d78b13d"),
                HotelCategory("호캉스", "https://images.unsplash.com/photo-1582719478250-c89cae4dc85b"),
                HotelCategory("반려견", "https://images.unsplash.com/photo-1558788353-f76d92427f16"),
                HotelCategory("신규", "https://images.unsplash.com/photo-1520250497591-112f2f40a3f4")
            )

            _uiState.update { currentState ->
                currentState.copy(
                    categories = categories,
                    regions = getRegions(),
                    popularHotels = getAccommodations().shuffled().subList(0, 4),
                    premiumHotels = getAccommodations().shuffled().subList(0, 4)
                )
            }
        }
    }
}