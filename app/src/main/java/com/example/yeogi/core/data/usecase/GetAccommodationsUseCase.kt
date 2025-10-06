package com.example.yeogi.core.data.usecase

import com.example.yeogi.core.data.repository.SharedRepository
import com.example.yeogi.core.model.Accommodation

class GetAccommodationsUseCase {
    operator fun invoke(): List<Accommodation> {
        return SharedRepository.getAccommodations()
    }
}