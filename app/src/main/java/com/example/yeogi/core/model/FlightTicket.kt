package com.example.yeogi.core.model

data class FlightTicket(
    val id: Int,
    val airline: String,
    val departureTime: String,
    val departureAirport: String,
    val arrivalTime: String,
    val arrivalAirport: String,
    val duration: String,
    val price: String,
    val type: String
)
