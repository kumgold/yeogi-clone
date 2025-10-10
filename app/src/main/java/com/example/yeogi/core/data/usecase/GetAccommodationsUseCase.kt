package com.example.yeogi.core.data.usecase

import com.example.yeogi.core.data.repository.SharedRepository
import com.example.yeogi.core.model.Accommodation

class GetAccommodationsUseCase(
    private val sharedRepository: SharedRepository
) {
    operator fun invoke(): List<Accommodation> {
        return sharedRepository.getAccommodations()
    }
}