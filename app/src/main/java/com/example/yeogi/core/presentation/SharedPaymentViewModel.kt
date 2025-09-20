package com.example.yeogi.core.presentation

import com.example.yeogi.core.model.Accommodation
import com.example.yeogi.feature.room.data.remote.Room

class SharedPaymentViewModel : SharedViewModel() {
    var accommodation: Accommodation? = null
    var selectedRoom: Room? = null

    fun setAccommodation(accommodationId: Int) {
        val accommodation = getAccommodations().find { it.id == accommodationId }

        this.accommodation = accommodation
    }


}