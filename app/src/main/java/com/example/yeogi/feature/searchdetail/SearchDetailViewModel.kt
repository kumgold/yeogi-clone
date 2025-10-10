package com.example.yeogi.feature.searchdetail

import androidx.lifecycle.viewModelScope
import com.example.yeogi.core.data.usecase.GetAccommodationsUseCase
import com.example.yeogi.core.model.Accommodation
import com.example.yeogi.core.presentation.SharedViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate

data class SearchDetailUiState(
    val startDate: LocalDate = LocalDate.now(),
    val endDate: LocalDate = LocalDate.now().plusDays(1),
    val guestCount: Int = 2,
    val originalAccommodations: List<Accommodation> = emptyList(),
    val displayedAccommodations: List<Accommodation> = emptyList(),
    val selectedAccommodationTypes: Set<String> = setOf("전체"),
    val selectedDetailFilters: Set<String> = emptySet(),
    val isSearching: Boolean = false,
    val query: String = "",
)

@HiltViewModel
class SearchDetailViewModel @Inject constructor() : SharedViewModel() {
    private val getAccommodationsUseCase = GetAccommodationsUseCase()

    private val _uiState = MutableStateFlow(SearchDetailUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadInitialAccommodations()
    }

    private fun loadInitialAccommodations() {
        viewModelScope.launch {
            _uiState.update { it.copy(isSearching = true) }

            val allAccommodations = getAccommodationsUseCase()

            _uiState.update {
                it.copy(
                    startDate = startDate,
                    endDate = endDate,
                    guestCount = guest,
                    originalAccommodations = allAccommodations,
                    displayedAccommodations = allAccommodations,
                    isSearching = false
                )
            }
        }
    }


    fun selectAccommodationType(type: String) {
        val currentSelection = _uiState.value.selectedAccommodationTypes
        val newSelection = if (type == "전체") {
            setOf("전체")
        } else {
            if (currentSelection.contains(type)) {
                currentSelection - type
            } else {
                currentSelection + type - "전체"
            }
        }

        val finalSelection = newSelection.ifEmpty { setOf("전체") }

        _uiState.update { it.copy(selectedAccommodationTypes = finalSelection) }
        applyFilters()
    }

    fun toggleDetailFilter(filter: String) {
        val currentFilters = _uiState.value.selectedDetailFilters
        val newFilters = if (currentFilters.contains(filter)) {
            currentFilters - filter
        } else {
            currentFilters + filter
        }
        _uiState.update { it.copy(selectedDetailFilters = newFilters) }
        applyFilters()
    }

    fun searchAccommodationsByQueryString(query: String, radiusKm: Double = 10.0) {
        viewModelScope.launch {
            _uiState.update { it.copy(isSearching = true) }

            val allAccommodations = getAccommodationsUseCase()
            val lowerCaseQuery = query.lowercase()

            val keywordMatches = allAccommodations.filter {
                it.name.lowercase().contains(lowerCaseQuery) ||
                        it.address.lowercase().contains(lowerCaseQuery)
            }

            val allRegions = getRegions().values.flatten()
            val region = allRegions.find { it.name.lowercase().contains(lowerCaseQuery) }

            val regionMatches = if (region != null) {
                allAccommodations.filter {
                    calculateDistance(region.latitude, region.longitude, it.coordinateY, it.coordinateX) <= radiusKm
                }
            } else {
                emptyList()
            }

            val finalResult = (keywordMatches + regionMatches).distinctBy { it.id }

            _uiState.update {
                it.copy(
                    originalAccommodations = finalResult,
                    displayedAccommodations = finalResult,
                    isSearching = false,
                    query = query
                )
            }

            applyFilters()
        }
    }

    private fun calculateDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val r = 6371
        val latDistance = Math.toRadians(lat2 - lat1)
        val lonDistance = Math.toRadians(lon2 - lon1)
        val a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2)
        val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
        return r * c
    }

    private fun applyFilters() {
        val currentState = _uiState.value
        var filteredList = currentState.originalAccommodations

        val selectedTypes = currentState.selectedAccommodationTypes
        if ("전체" !in selectedTypes && selectedTypes.isNotEmpty()) {
            filteredList = filteredList.filter { it.category in selectedTypes }
        }

        currentState.selectedDetailFilters.forEach { filter ->
            filteredList = when (filter) {
                "특가" -> filteredList.filter { it.isSpecialPrice }
                "예약가능" -> filteredList.filter { it.isAvailable }
                "쿠폰" -> filteredList.filter { it.hasCoupon }
                "조식포함" -> filteredList.filter { it.isBreakfastIncluded }
                "오션뷰" -> filteredList.filter { it.hasOceanView }
                "시티뷰" -> filteredList.filter { it.hasCityView }
                "마운틴뷰" -> filteredList.filter { it.hasMountainView }
                else -> filteredList
            }
        }

        _uiState.update { it.copy(displayedAccommodations = filteredList) }
    }

    override fun setDateAndGuest(
        startDate: LocalDate,
        endDate: LocalDate,
        guest: Int
    ) {
        super.setDateAndGuest(startDate, endDate, guest)

        _uiState.update {
            it.copy(
                startDate = startDate,
                endDate = endDate,
                guestCount = guest
            )
        }
    }
}