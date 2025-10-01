package com.example.yeogi.feature.searchdetail

import androidx.lifecycle.viewModelScope
import com.example.yeogi.core.model.Accommodation
import com.example.yeogi.core.presentation.SharedViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class SearchDetailUiState(
    val accommodations: List<Accommodation> = listOf(),
    val filteredAccommodations: List<Accommodation> = emptyList(),
    val selectedAccommodationTypes: Set<String> = setOf("전체"),
    val selectedDetailFilters: Set<String> = emptySet()
)

class SearchDetailViewModel : SharedViewModel() {
    private val _uiState = MutableStateFlow(SearchDetailUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadAndFilterAccommodations()
    }

    private fun loadAndFilterAccommodations() {
        viewModelScope.launch {
            val allAccommodations = getAccommodations()
            _uiState.update {
                it.copy(
                    accommodations = allAccommodations,
                    filteredAccommodations = allAccommodations
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

        val finalSelection = if (newSelection.isEmpty()) setOf("전체") else newSelection

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

    private fun applyFilters() {
        val currentState = _uiState.value
        var filteredList = currentState.accommodations

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

        _uiState.update { it.copy(filteredAccommodations = filteredList) }
    }
}