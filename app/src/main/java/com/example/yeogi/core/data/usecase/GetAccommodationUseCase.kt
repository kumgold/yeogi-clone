package com.example.yeogi.core.data.usecase

import com.example.yeogi.core.data.repository.SharedRepository
import com.example.yeogi.core.model.Accommodation

class GetAccommodationUseCase(
    private val sharedRepository: SharedRepository
) {
    operator fun invoke(accommodationId: Int): Accommodation {
        return sharedRepository.getAccommodation(accommodationId)
            ?: throw Exception("Accommodation Not Found")
    }
}