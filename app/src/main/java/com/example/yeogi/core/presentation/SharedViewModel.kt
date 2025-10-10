package com.example.yeogi.core.presentation

import androidx.lifecycle.ViewModel
import com.example.yeogi.core.model.Accommodation
import com.example.yeogi.core.data.repository.SharedRepository
import com.example.yeogi.feature.hotel.data.Region
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import java.time.LocalDate

@HiltViewModel
open class SharedViewModel @Inject constructor(
    private val sharedRepository: SharedRepository
) : ViewModel() {

    val startDate = sharedRepository.reservationStartDate
    val endDate = sharedRepository.reservationEndDate
    val guest = sharedRepository.reservationGuest

    /**
     * 위 Accommodations와 다르게 날짜와 인원 선택은 SharedViewModel 형태가 적합한 것 같다.
     * 거의 모든 화면에서 역할을 하기 때문이다.
     */
    open fun setDateAndGuest(
        startDate: LocalDate,
        endDate: LocalDate,
        guest: Int
    ) {
        sharedRepository.setDates(startDate, endDate)
        sharedRepository.setGuest(guest)
    }

    open fun getRegions(): Map<String, List<Region>> {
        return sharedRepository.getRegions()
    }
}