package com.example.yeogi.feature.search

import androidx.lifecycle.ViewModel
import com.example.yeogi.data.repository.SharedRepository
import java.time.LocalDate

class SearchViewModel: ViewModel() {
    val repository = SharedRepository()

    val recentSearches = repository.getDomesticRecentSearch().toMutableList()
    var startDate = LocalDate.now()
    var endDate = startDate.plusDays(1)
    var guest = 2

    fun setDateAndGuest(
        startDate: LocalDate,
        endDate: LocalDate,
        guest: Int
    ) {
        this.startDate = startDate
        this.endDate = endDate
        this.guest = guest
    }
}