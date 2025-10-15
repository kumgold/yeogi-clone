package com.example.yeogi.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yeogi.core.data.repository.RecentSearchRepository
import com.example.yeogi.core.data.repository.SharedRepository
import com.example.yeogi.core.model.RecentSearch
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate

data class SearchUiState(
    val startDate: LocalDate = LocalDate.now(),
    val endDate: LocalDate = LocalDate.now().plusDays(1),
    val guest: Int = 2,
    val selectedIndex: Int = 0,
    val domesticRecentSearches: List<RecentSearch> = emptyList(),
    val overseaRecentSearches: List<RecentSearch> = emptyList()
)

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val sharedRepository: SharedRepository,
    private val recentSearchRepository: RecentSearchRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        SearchUiState(
            startDate = sharedRepository.reservationStartDate,
            endDate = sharedRepository.reservationEndDate,
            guest = sharedRepository.reservationGuest,
        )
    )
    val uiState = _uiState.asStateFlow()

    init {
        loadRecentSearches()
    }

    private fun loadRecentSearches() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    domesticRecentSearches = recentSearchRepository.getDomesticRecentSearches(),
                    overseaRecentSearches = recentSearchRepository.getOverseasRecentSearches()
                )
            }
        }
    }

    fun addDomesticRecentSearch(keyword: String) {
        viewModelScope.launch {
            val newSearch = RecentSearch(
                id = uiState.value.domesticRecentSearches.size + 1,
                keyword = keyword,
                guest = uiState.value.guest,
                startDate = uiState.value.startDate.toString(),
                endDate = uiState.value.endDate.toString()
            )
            recentSearchRepository.addDomesticRecentSearch(newSearch)
            loadRecentSearches()
        }
    }

    fun removeDomesticRecentSearch(id: Int) {
        viewModelScope.launch {
            recentSearchRepository.removeDomesticRecentSearch(id)
            loadRecentSearches()
        }
    }

    fun clearDomesticRecentSearches() {
        viewModelScope.launch {
            recentSearchRepository.clearDomesticRecentSearches()
            _uiState.update {
                it.copy(domesticRecentSearches = emptyList())
            }
        }
    }

    fun addOverseasRecentSearch(keyword: String) {
        viewModelScope.launch {
            val newSearch = RecentSearch(
                id = uiState.value.overseaRecentSearches.size + 1,
                keyword = keyword,
                guest = sharedRepository.reservationGuest,
                startDate = sharedRepository.reservationStartDate.toString(),
                endDate = sharedRepository.reservationEndDate.toString()
            )
            recentSearchRepository.addOverseasRecentSearch(newSearch)
            loadRecentSearches()
        }
    }

    fun removeOverseasRecentSearch(id: Int) {
        viewModelScope.launch {
            recentSearchRepository.removeOverseasRecentSearch(id)
            loadRecentSearches()
        }
    }

    fun clearOverseasRecentSearches() {
        viewModelScope.launch {
            recentSearchRepository.clearOverseasRecentSearches()
            _uiState.update {
                it.copy(overseaRecentSearches = emptyList())
            }
        }
    }

    fun setDateAndGuest(startDate: LocalDate, endDate: LocalDate, guest: Int) {
        sharedRepository.setDatesAndGuest(startDate, endDate, guest)

        _uiState.update {
            it.copy(
                startDate = startDate,
                endDate = endDate,
                guest = guest
            )
        }
    }

    fun onTabSelected(index: Int) {
        _uiState.update {
            it.copy(
                selectedIndex = index
            )
        }
    }
}