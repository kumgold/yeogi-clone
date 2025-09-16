package com.example.yeogi.data.repository

import com.example.yeogi.data.model.Accommodation
import com.example.yeogi.data.model.RecentSearch
import com.example.yeogi.data.model.ServiceCategory
import com.example.yeogi.data.model.dummyAccommodations
import com.example.yeogi.data.model.dummyDomesticRecentSearch
import com.example.yeogi.data.model.dummyOverseasRecentSearch
import com.example.yeogi.data.model.dummyServiceCategory
import com.example.yeogi.util.getFormattedMonthDay
import java.time.LocalDate

object SharedRepository {

    var reservationStartDate: LocalDate = LocalDate.now()
    var reservationEndDate: LocalDate = reservationStartDate.plusDays(1)
    var reservationGuest = 2

    fun getServiceCategory(): List<ServiceCategory> {
        return dummyServiceCategory
    }

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