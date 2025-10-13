package com.example.yeogi.feature.search.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.yeogi.core.ui.section.RecentHistorySection
import com.example.yeogi.core.util.getFormattedMonthDay
import com.example.yeogi.feature.search.SearchViewModel

@Composable
fun OverseasAccommodationContent(
    viewModel: SearchViewModel = hiltViewModel(),
    navigateToDetail: (String) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        // 검색 컨트롤 영역 (지역, 날짜, 인원, 검색 버튼)
        Column(
            modifier = Modifier
                .background(Color.White)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // 지역 검색 영역
            OverseasSearchField(
                icon = Icons.Default.LocationOn,
                label = "지역",
                value = "도쿄, 일본",
                onClick = { /* 지역 선택 화면으로 이동 */ }
            )

            // 날짜 선택 영역
            val dateRange = "${uiState.startDate.getFormattedMonthDay()} - ${uiState.endDate.getFormattedMonthDay()}"

            OverseasSearchField(
                icon = Icons.Default.CalendarToday,
                label = "날짜",
                value = dateRange,
                onClick = { /* 캘린더 다이얼로그 표시 */ }
            )

            // 인원 선택 영역
            OverseasSearchField(
                icon = Icons.Default.Person,
                label = "인원",
                value = "성인 2명, 아동 0명",
                onClick = { /* 인원 선택 다이얼로그 표시 */ }
            )

            // 검색 버튼
            Button(
                onClick = { /* 해외숙소 검색 실행 */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text("해외숙소 검색", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (uiState.overseaRecentSearches.isNotEmpty()) {
            RecentHistorySection(
                items = uiState.overseaRecentSearches,
                onClearAll = { viewModel.clearOverseasRecentSearches() },
                onDeleteItem = { item -> viewModel.removeOverseasRecentSearch(item.id) },
                onClick = {}
            )
        }
    }
}

/**
 * 해외숙소 탭에서 사용되는 검색 필드 Composable
 */
@Composable
fun OverseasSearchField(
    icon: ImageVector,
    label: String,
    value: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp)
            .border(1.dp, Color(0xFFE0E0E0), RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = label, tint = Color.Gray)
        Spacer(modifier = Modifier.width(12.dp))
        Text(value, style = MaterialTheme.typography.bodyMedium,)
    }
}