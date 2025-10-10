package com.example.yeogi.feature.favorite

import com.example.yeogi.core.data.repository.SharedRepository
import com.example.yeogi.core.presentation.SharedViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val sharedRepository: SharedRepository
) : SharedViewModel(sharedRepository)