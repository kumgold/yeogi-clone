package com.example.yeogi.core.data.repository

import com.example.yeogi.core.data.network.DummyServer
import com.example.yeogi.core.model.Accommodation
import com.example.yeogi.core.model.FlightTicket
import com.example.yeogi.core.util.getTicket
import com.example.yeogi.feature.hotel.data.Region
import okhttp3.internal.filterList
import java.time.LocalDate

class SharedRepository {
    var reservationStartDate: LocalDate = LocalDate.now()
    var reservationEndDate: LocalDate = reservationStartDate.plusDays(1)
    var reservationGuest = 2

    fun setDatesAndGuest(
        startDate: LocalDate,
        endDate: LocalDate,
        guest: Int
    ) {
        reservationStartDate = startDate
        reservationEndDate = endDate
        reservationGuest = guest
    }

    fun getAccommodations(): List<Accommodation> {
        return DummyServer.accommodations
    }

    fun getOverSeaAccommodations(): List<Accommodation> {
        return DummyServer.overseaAccommodations
    }

    fun getAccommodation(accommodationId: Int): Accommodation? {
        val total = DummyServer.accommodations + DummyServer.overseaAccommodations

        return total.find { it.id == accommodationId }
    }

    fun getRegions(): Map<String, List<Region>> {
        return DummyServer.regions
    }

    fun getFlightTickets(
        departureAirport: String,
        arrivalAirport: String,
        type: String
    ): List<FlightTicket> {
        return DummyServer.flightTickets.filter {
            it.getTicket(departureAirport, arrivalAirport, type)
        }
    }
}