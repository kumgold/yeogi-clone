package com.example.yeogi.feature.searchdetail.flight

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.yeogi.core.data.repository.SharedRepository
import com.example.yeogi.core.data.usecase.GetFlightUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

@HiltViewModel
class FlightSearchDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val sharedRepository: SharedRepository,
    private val getFlightUseCase: GetFlightUseCase
) : ViewModel() {
    fun getTickets(
        departureAirport: String,
        arrivalAirport: String,
        type: String
    ) {

    }
}