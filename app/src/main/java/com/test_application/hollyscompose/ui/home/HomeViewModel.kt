package com.test_application.hollyscompose.ui.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {
    private val _stamp: MutableState<Int> = mutableStateOf(2)
    val stamp: State<Int> = _stamp
}