package com.example.yeogi.feature.search

import androidx.lifecycle.ViewModel
import com.example.yeogi.data.repository.SharedRepository
import java.time.LocalDate

class SearchViewModel: ViewModel() {
    val repository = SharedRepository()

    val domesticRecentSearches = repository.getDomesticRecentSearch().toMutableList()
    val overseaRecentSearches = repository.getOverseasRecentSearch().toMutableList()

    var startDate = repository.reservationStartDate
    var endDate = repository.reservationEndDate
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