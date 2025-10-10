package com.example.yeogi.feature.payment

import com.example.yeogi.core.data.repository.SharedRepository
import com.example.yeogi.core.model.Accommodation
import com.example.yeogi.core.presentation.SharedViewModel
import com.example.yeogi.feature.room.data.remote.Room
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate

data class PaymentUiState(
    val accommodation: Accommodation? = null,
    val room: Room? = null,
    val userName: String? = null,
    val phoneNumber: String? = null,
    val paymentId: Int = 0,
    val startDate: LocalDate = LocalDate.now(),
    val endDate: LocalDate = LocalDate.now().plusDays(1),
    val agreed: Boolean = false
)

@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val sharedRepository: SharedRepository
) : SharedViewModel(sharedRepository) {
    private val _uiState = MutableStateFlow(PaymentUiState())
    val uiState: StateFlow<PaymentUiState> = _uiState

    fun setUiState(
        accommodation: Accommodation?,
        room: Room?
    ) {
        _uiState.update {
            it.copy(
                accommodation = accommodation,
                room = room,
                startDate = startDate,
                endDate = endDate
            )
        }
    }
}