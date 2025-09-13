package com.example.yeogi.data.repository

import com.example.yeogi.data.model.Accommodation
import com.example.yeogi.data.model.RecentSearch
import com.example.yeogi.data.model.ServiceCategory
import com.example.yeogi.data.model.dummyAccommodations
import com.example.yeogi.data.model.dummyDomesticRecentSearch
import com.example.yeogi.data.model.dummyOverseasRecentSearch
import com.example.yeogi.data.model.dummyServiceCategory
import java.time.LocalDate

class SharedRepository {
    val reservationStartDate = LocalDate.now()
    val reservationEndDate = reservationStartDate.plusDays(1)

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
}