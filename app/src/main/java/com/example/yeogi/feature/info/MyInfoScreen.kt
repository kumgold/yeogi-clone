package com.example.yeogi.feature.info

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.yeogi.SystemBarColor

@Composable
fun MyInfoScreen() {
    SystemBarColor(color = MaterialTheme.colorScheme.background)
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "내 정보 화면")
    }
}