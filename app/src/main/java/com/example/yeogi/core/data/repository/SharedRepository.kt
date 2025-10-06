package com.example.yeogi.core.data.repository

import com.example.yeogi.core.data.network.DummyServer
import com.example.yeogi.core.model.Accommodation
import com.example.yeogi.core.model.RecentSearch
import com.example.yeogi.feature.hotel.data.Region
import java.time.LocalDate

object SharedRepository {
    var reservationStartDate: LocalDate = LocalDate.now()
    var reservationEndDate: LocalDate = reservationStartDate.plusDays(1)
    var reservationGuest = 2

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

    fun getAccommodations(): List<Accommodation> {
        return DummyServer.accommodations
    }

    fun getDomesticRecentSearch(): List<RecentSearch> {
        return DummyServer.domesticRecentSearch
    }

    fun getOverseasRecentSearch(): List<RecentSearch> {
        return DummyServer.overseasRecentSearch
    }

    fun getRegions(): Map<String, List<Region>> {
        return DummyServer.regions
    }
}