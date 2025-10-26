package com.example.yeogi.core.data.usecase

import com.example.yeogi.core.data.repository.SharedRepository
import com.example.yeogi.core.model.FlightTicket

class GetFlightUseCase(
    private val sharedRepository: SharedRepository
) {
    operator fun invoke(
        departureAirport: String,
        arrivalAirport: String,
        type: String
    ): List<FlightTicket> {
        return sharedRepository.getFlightTickets(
            departureAirport = departureAirport,
            arrivalAirport = arrivalAirport,
            type = type
        )
    }
}