package com.example.yeogi.feature.room

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yeogi.feature.room.data.remote.Room
import com.example.yeogi.feature.room.data.repository.RoomRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RoomSelectionViewModel : ViewModel() {
    private val repository = RoomRepository()

    private val _rooms = MutableStateFlow<List<Room>>(emptyList())
    val rooms = _rooms.asStateFlow()

    fun loadRooms(accommodationId: Int) {
        viewModelScope.launch {
            val filteredRooms = repository.getRoomList(accommodationId)
            _rooms.value = filteredRooms
        }
    }
}