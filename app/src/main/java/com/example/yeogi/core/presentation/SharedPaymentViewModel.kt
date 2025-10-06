package com.example.yeogi.core.presentation

import com.example.yeogi.core.data.usecase.GetAccommodationsUseCase
import com.example.yeogi.core.model.Accommodation
import com.example.yeogi.feature.room.data.remote.Room

class SharedPaymentViewModel : SharedViewModel() {
    private val getAccommodationsUseCase = GetAccommodationsUseCase()

    var accommodation: Accommodation? = null
    var selectedRoom: Room? = null

    fun setAccommodation(accommodationId: Int) {
        val accommodation = getAccommodationsUseCase().find { it.id == accommodationId }

        this.accommodation = accommodation
    }


}