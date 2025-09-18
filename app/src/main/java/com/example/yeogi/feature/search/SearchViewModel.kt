package com.example.yeogi.feature.search

import androidx.lifecycle.ViewModel
import com.example.yeogi.core.repository.SharedRepository
import java.time.LocalDate

class SearchViewModel: ViewModel() {
    private val repository = SharedRepository

    val domesticRecentSearches = repository.getDomesticRecentSearch().toMutableList()
    val overseaRecentSearches = repository.getOverseasRecentSearch().toMutableList()

    var startDate: LocalDate = repository.reservationStartDate
    var endDate: LocalDate = repository.reservationEndDate
    var guest = repository.reservationGuest

    fun setDateAndGuest(
        startDate: LocalDate,
        endDate: LocalDate,
        guest: Int
    ) {
        repository.setDates(startDate, endDate)
        repository.setGuest(guest)
    }
}