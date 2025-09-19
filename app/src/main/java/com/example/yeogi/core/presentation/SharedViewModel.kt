package com.example.yeogi.core.presentation

import androidx.lifecycle.ViewModel
import com.example.yeogi.core.model.Accommodation
import com.example.yeogi.core.repository.SharedRepository
import java.time.LocalDate

open class SharedViewModel : ViewModel() {
    private val sharedRepository = SharedRepository

    val startDate = sharedRepository.reservationStartDate
    val endDate = sharedRepository.reservationEndDate
    val guest = sharedRepository.reservationGuest

    open fun getAccommodations(): List<Accommodation> {
        return sharedRepository.getAccommodations()
    }

    open fun setDateAndGuest(
        startDate: LocalDate,
        endDate: LocalDate,
        guest: Int
    ) {
        sharedRepository.setDates(startDate, endDate)
        sharedRepository.setGuest(guest)
    }
}