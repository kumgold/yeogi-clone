package com.example.yeogi.core.repository

import com.example.yeogi.core.model.Accommodation
import com.example.yeogi.core.model.RecentSearch
import com.example.yeogi.core.model.dummyAccommodations
import com.example.yeogi.core.model.dummyDomesticRecentSearch
import com.example.yeogi.core.model.dummyOverseasRecentSearch
import java.time.LocalDate

object SharedRepository {

    var reservationStartDate: LocalDate = LocalDate.now()
    var reservationEndDate: LocalDate = reservationStartDate.plusDays(1)
    var reservationGuest = 2

    fun getAccommodations(): List<Accommodation> {
        return dummyAccommodations
    }

    fun getDomesticRecentSearch(): List<RecentSearch> {
        return dummyDomesticRecentSearch
    }
 
    fun getOverseasRecentSearch(): List<RecentSearch> {
        return dummyOverseasRecentSearch
    }

    fun setDates(
        startDate: LocalDate,
        endDate: LocalDate,
    ) {
        reservationStartDate = startDate
        reservationEndDate = endDate
    }

    fun setGuest(guest: Int) {
        reservationGuest = guest
    }
}