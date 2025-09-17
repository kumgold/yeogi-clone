package com.example.yeogi.feature.accommodation

import androidx.lifecycle.ViewModel
import com.example.yeogi.data.repository.SharedRepository
import com.example.yeogi.util.getFormattedMonthDay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate

data class AccommodationUiState(
    val startDate: LocalDate = LocalDate.now(),
    val endDate: LocalDate = LocalDate.now().plusDays(1),
    val guestCount: Int = 2,
    val isDateGuestSheetOpen: Boolean = false
) {
    val dateRangeString: String
        get() = "${startDate.getFormattedMonthDay()} - ${endDate.getFormattedMonthDay()} ∙ 인원 ${guestCount}명"
}

class AccommodationViewModel : ViewModel() {
    private val repository = SharedRepository

    val startDate = repository.reservationStartDate
    val endDate = repository.reservationEndDate
    val guest = repository.reservationGuest

    private val _uiState = MutableStateFlow(
        AccommodationUiState(
            startDate = startDate,
            endDate = endDate,
            guestCount = guest
        )
    )
    val uiState = _uiState.asStateFlow()

    fun openDateGuestSheet() {
        _uiState.update { it.copy(isDateGuestSheetOpen = true) }
    }

    fun closeDateGuestSheet() {
        _uiState.update { it.copy(isDateGuestSheetOpen = false) }
    }

    fun setDateAndGuest(startDate: LocalDate, endDate: LocalDate, guest: Int) {
        _uiState.update {
            it.copy(
                startDate = startDate,
                endDate = endDate,
                guestCount = guest
            )
        }

        repository.setDates(startDate, endDate)
        repository.setGuest(guest)
    }
}