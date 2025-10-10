package com.example.yeogi.core.presentation

import com.example.yeogi.core.data.repository.SharedRepository
import com.example.yeogi.core.data.usecase.GetAccommodationsUseCase
import com.example.yeogi.core.model.Accommodation
import com.example.yeogi.feature.room.data.remote.Room
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

@HiltViewModel
class SharedPaymentViewModel @Inject constructor(
    sharedRepository: SharedRepository,
    private val getAccommodationsUseCase: GetAccommodationsUseCase
) : SharedViewModel(sharedRepository) {
    var accommodation: Accommodation? = null
    var selectedRoom: Room? = null

    fun setAccommodation(accommodationId: Int) {
        val accommodation = getAccommodationsUseCase().find { it.id == accommodationId }

        this.accommodation = accommodation
    }
}