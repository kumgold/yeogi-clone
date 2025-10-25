package com.example.yeogi.feature.searchdetail.flight.data

data class FlightTicket(
    val airline: String,
    val departureTime: String,
    val departureAirport: String,
    val arrivalTime: String,
    val arrivalAirport: String,
    val duration: String,
    val price: String,
    val type: String
)
