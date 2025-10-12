package com.example.yeogi.feature.search

import androidx.lifecycle.viewModelScope
import com.example.yeogi.core.data.repository.RecentSearchRepository
import com.example.yeogi.core.data.repository.SharedRepository
import com.example.yeogi.core.model.RecentSearch
import com.example.yeogi.core.presentation.SharedViewModel
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
    val domesticRecentSearches: List<RecentSearch> = emptyList(),
    val overseaRecentSearches: List<RecentSearch> = emptyList()
)

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: SharedRepository,
    private val recentSearchRepository: RecentSearchRepository
) : SharedViewModel(repository) {

    private val _uiState = MutableStateFlow(
        SearchUiState(
            startDate = startDate,
            endDate = endDate,
            guest = guest,
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
                guest = guest,
                startDate = startDate.toString(),
                endDate = endDate.toString()
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

    override fun setDateAndGuest(startDate: LocalDate, endDate: LocalDate, guest: Int) {
        super.setDateAndGuest(startDate, endDate, guest)

        _uiState.update {
            it.copy(
                startDate = startDate,
                endDate = endDate,
                guest = guest
            )
        }
    }
}