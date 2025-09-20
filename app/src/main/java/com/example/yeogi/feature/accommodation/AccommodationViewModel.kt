package com.example.yeogi.feature.accommodation

import com.example.yeogi.core.model.Accommodation
import com.example.yeogi.core.presentation.SharedViewModel
import com.example.yeogi.core.util.getFormattedMonthDay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate

data class AccommodationUiState(
    val accommodation: Accommodation? = null,
    val startDate: LocalDate = LocalDate.now(),
    val endDate: LocalDate = LocalDate.now().plusDays(1),
    val guestCount: Int = 2,
    val isDateGuestSheetOpen: Boolean = false
) {
    val dateRangeString: String
        get() = "${startDate.getFormattedMonthDay()} - ${endDate.getFormattedMonthDay()} ∙ 인원 ${guestCount}명"
}

class AccommodationViewModel : SharedViewModel() {
    private val _uiState = MutableStateFlow(
        AccommodationUiState(
            startDate = startDate,
            endDate = endDate,
            guestCount = guest
        )
    )
    val uiState = _uiState.asStateFlow()

    fun getAccommodation(accommodationId: Int) {
        val accommodation = super.getAccommodations().find { it.id == accommodationId }

        _uiState.update {
            it.copy(accommodation = accommodation)
        }
    }

    fun openDateGuestSheet() {
        _uiState.update { it.copy(isDateGuestSheetOpen = true) }
    }

    fun closeDateGuestSheet() {
        _uiState.update { it.copy(isDateGuestSheetOpen = false) }
    }

    override fun setDateAndGuest(startDate: LocalDate, endDate: LocalDate, guest: Int) {
        _uiState.update {
            it.copy(
                startDate = startDate,
                endDate = endDate,
                guestCount = guest
            )
        }

        super.setDateAndGuest(startDate, endDate, guest)
    }
}