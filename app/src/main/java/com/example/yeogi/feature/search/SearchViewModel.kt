package com.example.yeogi.feature.search

import androidx.lifecycle.viewModelScope
import com.example.yeogi.core.data.repository.RecentSearchRepository
import com.example.yeogi.core.data.repository.SharedRepository
import com.example.yeogi.core.model.RecentSearch
import com.example.yeogi.core.presentation.SharedViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: SharedRepository,
    private val recentSearchRepository: RecentSearchRepository
) : SharedViewModel(repository) {

    private val _domesticRecentSearches = MutableStateFlow<List<RecentSearch>>(emptyList())
    val domesticRecentSearches: StateFlow<List<RecentSearch>> = _domesticRecentSearches.asStateFlow()

    private val _overseaRecentSearches = MutableStateFlow<List<RecentSearch>>(emptyList())
    val overseaRecentSearches: StateFlow<List<RecentSearch>> = _overseaRecentSearches.asStateFlow()

    init {
        loadRecentSearches()
    }

    private fun loadRecentSearches() {
        viewModelScope.launch {
            _domesticRecentSearches.value = recentSearchRepository.getDomesticRecentSearches()
            _overseaRecentSearches.value = recentSearchRepository.getOverseasRecentSearches()
        }
    }

    fun addDomesticRecentSearch(keyword: String) {
        viewModelScope.launch {
            val newSearch = RecentSearch(
                id = _domesticRecentSearches.value.size + 1,
                keyword = keyword,
                guest = guest,
                startDate = startDate.toString(),
                endDate = endDate.toString()
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
            _domesticRecentSearches.value = emptyList()
        }
    }

    fun addOverseasRecentSearch(keyword: String) {
        viewModelScope.launch {
            val newSearch = RecentSearch(
                id = _overseaRecentSearches.value.size + 1,
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
            _overseaRecentSearches.value = emptyList()
        }
    }
}