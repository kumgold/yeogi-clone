package com.example.yeogi.feature.searchdetail.oversea

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.yeogi.core.ui.bottomsheet.DateGuestSelectionBottomSheet
import com.example.yeogi.feature.searchdetail.domestic.SearchResultTopBar
import com.example.yeogi.feature.searchdetail.ui.VerticalAccommodationItem
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OverseaSearchDetailScreen(
    viewModel: OverseaSearchDetailViewModel = hiltViewModel(),
    navigateToAccommodation: (Int) -> Unit,
    popBackStack: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()
    var isShowDateBottomSheet by remember { mutableStateOf(false) }

    if (isShowDateBottomSheet) {
        DateGuestSelectionBottomSheet(
            initialStartDate = uiState.startDate,
            initialEndDate = uiState.endDate,
            initialGuestCount = uiState.guestCount,
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

    LazyColumn(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding()
            .fillMaxSize()
    ) {
        item {
            SearchResultTopBar(
                searchQuery = uiState.query,
                startDate = uiState.startDate,
                endDate = uiState.endDate,
                guestCount = uiState.guestCount,
                onBackClick = { popBackStack() },
                onSearchClick = { },
                onDateClick = { isShowDateBottomSheet = true }
            )
        }

        item {
            Column(Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)) {
                Text(
                    text = "호텔 목록",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
        }

        items(uiState.displayedAccommodations) { accommodation ->
            Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
                VerticalAccommodationItem(
                    item = accommodation,
                    onClick = { id -> navigateToAccommodation(id) }
                )
            }
        }
    }
}