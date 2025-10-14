package com.example.yeogi.feature.searchdetail.oversea

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yeogi.core.data.repository.SharedRepository
import com.example.yeogi.core.data.usecase.GetOverseaAccommodationsUseCase
import com.example.yeogi.core.model.Accommodation
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate

data class OverseaSearchDetailUiState(
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
class OverseaSearchDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val sharedRepository: SharedRepository,
    private val getOverseaAccommodationsUseCase: GetOverseaAccommodationsUseCase
) : ViewModel() {

    private val query: String? = savedStateHandle.get<String>("query")

    private val _uiState = MutableStateFlow(OverseaSearchDetailUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadInitialAccommodations()

        if (query != null) {
            searchAccommodationsByQueryString(query)
        }
    }

    private fun loadInitialAccommodations() {
        viewModelScope.launch {
            _uiState.update { it.copy(isSearching = true) }

            val allAccommodations = getOverseaAccommodationsUseCase()

            _uiState.update {
                it.copy(
                    startDate = sharedRepository.reservationStartDate,
                    endDate = sharedRepository.reservationEndDate,
                    guestCount = sharedRepository.reservationGuest,
                    originalAccommodations = allAccommodations,
                    displayedAccommodations = allAccommodations,
                    isSearching = false
                )
            }
        }
    }

    fun setDateAndGuest(
        startDate: LocalDate,
        endDate: LocalDate,
        guest: Int
    ) {
        sharedRepository.setDatesAndGuest(startDate, endDate, guest)

        _uiState.update {
            it.copy(
                startDate = startDate,
                endDate = endDate,
                guestCount = guest
            )
        }
    }

    fun searchAccommodationsByQueryString(query: String, radiusKm: Double = 10.0) {
        viewModelScope.launch {
            _uiState.update { it.copy(isSearching = true) }

            val allAccommodations = getOverseaAccommodationsUseCase()
            val lowerCaseQuery = query.lowercase()

            val keywordMatches = allAccommodations.filter {
                it.name.lowercase().contains(lowerCaseQuery) ||
                        it.address.lowercase().contains(lowerCaseQuery)
            }

            val allRegions = sharedRepository.getRegions().values.flatten()
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
}