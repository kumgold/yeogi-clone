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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.yeogi.core.ui.bottomsheet.DateGuestSelectionBottomSheet
import com.example.yeogi.core.ui.bottomsheet.SearchBottomSheet
import com.example.yeogi.core.ui.section.RecentHistorySection
import com.example.yeogi.core.util.getFormattedMonthDay
import com.example.yeogi.feature.search.SearchViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OverseasAccommodationContent(
    viewModel: SearchViewModel = hiltViewModel(),
    navigateToDetail: (String) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()
    var isShowSearchBottomSheet by remember { mutableStateOf(false) }
    var isShowDateBottomSheet by remember { mutableStateOf(false) }

    if (isShowSearchBottomSheet) {
        SearchBottomSheet(
            title = "목적지 검색",
            sheetState = sheetState,
            onDismiss = {
                scope.launch { sheetState.hide() }.invokeOnCompletion {
                    if (!sheetState.isVisible) {
                        isShowSearchBottomSheet = false
                    }
                }
            },
            onSearch = { searchQuery ->
                navigateToDetail(searchQuery)
                scope.launch { sheetState.hide() }.invokeOnCompletion {
                    if (!sheetState.isVisible) {
                        isShowSearchBottomSheet = false
                    }
                }
            }
        )
    }

    if (isShowDateBottomSheet) {
        DateGuestSelectionBottomSheet(
            initialStartDate = uiState.startDate,
            initialEndDate = uiState.endDate,
            initialGuestCount = uiState.guest,
            sheetState = sheetState,
            onDismiss = {
                scope.launch { sheetState.hide() }.invokeOnCompletion {
                    if (!sheetState.isVisible) isShowDateBottomSheet = false
                }
            },
            onApply = { newStart, newEnd, newGuests ->
                viewModel.setDateAndGuest(
                    startDate = newStart,
                    endDate = newEnd,
                    guest = newGuests
                )
            },
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OverseasSearchField(
                icon = Icons.Default.LocationOn,
                label = "지역",
                value = "도쿄, 일본",
                onClick = {
                    isShowSearchBottomSheet = true
                }
            )

            val dateRange = "${uiState.startDate.getFormattedMonthDay()} - ${uiState.endDate.getFormattedMonthDay()}"

            OverseasSearchField(
                icon = Icons.Default.CalendarToday,
                label = "날짜",
                value = dateRange,
                onClick = {
                    isShowDateBottomSheet = true
                }
            )

            OverseasSearchField(
                icon = Icons.Default.Person,
                label = "인원",
                value = "성인 2명, 아동 0명",
                onClick = {
                    isShowDateBottomSheet = true
                }
            )

            Button(
                onClick = {

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    text = "해외숙소 검색",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onPrimary
                )
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
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onBackground,
                shape= RoundedCornerShape(8.dp)
            )
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}