package com.example.yeogi.feature.accommodation

import androidx.lifecycle.ViewModel
import com.example.yeogi.data.repository.SharedRepository
import java.time.LocalDate

class AccommodationViewModel : ViewModel() {
    private val repository = SharedRepository

    val startDate = repository.reservationStartDate
    val endDate = repository.reservationEndDate
    val guest = repository.reservationGuest

    fun setDateAndGuest(
        startDate: LocalDate,
        endDate: LocalDate,
        guest: Int
    ) {
        repository.setDates(startDate, endDate)
        repository.setGuest(guest)
    }
}