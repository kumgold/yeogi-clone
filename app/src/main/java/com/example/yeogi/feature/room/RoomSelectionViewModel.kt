package com.example.yeogi.feature.room

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.yeogi.core.presentation.SharedViewModel
import com.example.yeogi.feature.room.data.remote.Room
import com.example.yeogi.feature.room.data.repository.RoomRepository
import com.example.yeogi.core.util.getFormattedMonthDay
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate

data class RoomSelectionUiState(
    val startDate: LocalDate = LocalDate.now(),
    val endDate: LocalDate = LocalDate.now().plusDays(1),
    val guestCount: Int = 2,
    val isDateGuestSheetOpen: Boolean = false,
    val rooms: List<Room> = emptyList()
) {
    val dateRangeString: String
        get() = "${startDate.getFormattedMonthDay()} - ${endDate.getFormattedMonthDay()} ∙ 인원 ${guestCount}명"
}

@HiltViewModel
class RoomSelectionViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : SharedViewModel() {
    private val repository = RoomRepository()
    private val accommodationId: Int? = savedStateHandle.get<Int>("accommodationId")

    private val _uiState = MutableStateFlow(
        RoomSelectionUiState(
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

    fun loadRooms() {
        viewModelScope.launch {
            val rooms = accommodationId?.let {
                repository.getRoomList(it)
            } ?: emptyList()

            _uiState.update {
                it.copy(rooms = rooms)
            }
        }
    }
}