package com.example.yeogi.feature.room

import androidx.lifecycle.ViewModel
import com.example.yeogi.data.model.Room
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class RoomSelectionViewModel : ViewModel() {
    private val _rooms = MutableStateFlow<List<Room>>(emptyList())
    val rooms = _rooms.asStateFlow()
}