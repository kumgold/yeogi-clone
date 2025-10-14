package com.example.yeogi.feature.favorite

import androidx.lifecycle.ViewModel
import com.example.yeogi.core.data.repository.SharedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val sharedRepository: SharedRepository
) : ViewModel()