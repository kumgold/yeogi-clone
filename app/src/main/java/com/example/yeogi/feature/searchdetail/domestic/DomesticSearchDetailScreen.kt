package com.example.yeogi.feature.searchdetail.domestic

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.yeogi.core.model.Accommodation
import com.example.yeogi.core.ui.bottomsheet.DateGuestSelectionBottomSheet
import com.example.yeogi.core.ui.bottomsheet.SearchBottomSheet
import com.example.yeogi.core.ui.section.RecommendationSection
import com.example.yeogi.feature.searchdetail.ui.VerticalAccommodationItem
import kotlinx.coroutines.launch
import java.time.LocalDate

/**
 * 숙소 검색 결과 메인 화면
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DomesticSearchDetailScreen(
    query: String?,
    viewModel: DomesticSearchDetailViewModel = hiltViewModel(),
    navigateToAccommodation: (Int) -> Unit,
    popBackStack: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()
    var isShowSearchBottomSheet by remember { mutableStateOf(false) }
    var isShowDateBottomSheet by remember { mutableStateOf(false) }

    val recommendedAccommodation: Accommodation? =  try {
        uiState.displayedAccommodations.first()
    } catch (e: Exception) {
        null
    }
    val horizontalAccommodations = try {
        uiState.displayedAccommodations.subList(1, 6)
    } catch (e: Exception) {
        emptyList()
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
                onSearchClick = { isShowSearchBottomSheet = true },
                onDateClick = { isShowDateBottomSheet = true }
            )
        }

        item {
            AccommodationTypeFilter(
                selectedTypes = uiState.selectedAccommodationTypes,
                onTypeSelected = { type -> viewModel.selectAccommodationType(type) }
            )
        }

        item {
            FilterChips(
                selectedFilters = uiState.selectedDetailFilters,
                onFilterToggled = { filter -> viewModel.toggleDetailFilter(filter) }
            )
        }

        if (recommendedAccommodation != null) {
            item {
                Column(Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)) {
                    Text(
                        text = "여기어때 추천",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    VerticalAccommodationItem(
                        item = recommendedAccommodation,
                        onClick = { id -> navigateToAccommodation(id) }
                    )
                }
            }
        }

        if (horizontalAccommodations.isNotEmpty()) {
            item {
                RecommendationSection(
                    title = "지금 지역에서 주목할 숙소",
                    accommodations = horizontalAccommodations,
                    isAd = true,
                    onItemClick = { id ->
                        navigateToAccommodation(id)
                    }
                )
            }
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
                viewModel.searchAccommodationsByQueryString(searchQuery)
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
}

@Composable
fun SearchResultTopBar(
    searchQuery: String,
    startDate: LocalDate,
    endDate: LocalDate,
    guestCount: Int,
    onBackClick: () -> Unit,
    onSearchClick: () -> Unit,
    onDateClick: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(end = 16.dp)
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBackClick) {
            Icon(
                Icons.AutoMirrored.Filled.ArrowBack,
                tint = MaterialTheme.colorScheme.onBackground,
                contentDescription = "뒤로가기"
            )
        }
        SearchKeywordField(
            modifier = Modifier.weight(2.5f),
            query = searchQuery,
            onClick = onSearchClick
        )
        Spacer(modifier = Modifier.width(8.dp))
        Surface(
            modifier = Modifier.weight(1.5f)
                .height(48.dp)
                .clip(RoundedCornerShape(8.dp))
                .clickable {
                    onDateClick()
                },
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onBackground
            )
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 8.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "${startDate.monthValue}.${startDate.dayOfMonth} - ${endDate.monthValue}.${endDate.dayOfMonth}",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = "$guestCount 명",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }

    }
}

@Composable
fun SearchKeywordField(
    modifier: Modifier,
    query: String?,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(MaterialTheme.colorScheme.background)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onBackground,
                shape = RoundedCornerShape(8.dp)
            )
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(20.dp),
            imageVector = Icons.Default.Search,
            contentDescription = "검색 아이콘",
            tint = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = query ?: "숙소, 지역 이름으로 검색",
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun AccommodationTypeFilter(
    selectedTypes: Set<String>,
    onTypeSelected: (String) -> Unit
) {
    val types = listOf("전체", "호텔", "펜션", "리조트", "모텔", "캠핑", "게하")

    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Text(
                text = "숙소유형",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
        items (types) { type ->
            val isSelected = selectedTypes.contains(type)
            TextButton(
                onClick = { onTypeSelected(type) },
                colors = ButtonDefaults.textButtonColors(
                    contentColor = if (isSelected) MaterialTheme.colorScheme.primary else Color.Black
                )
            ) {
                Text(
                    text = type,
                    color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                )
            }
        }
    }
}

@Composable
fun FilterChips(
    selectedFilters: Set<String>,
    onFilterToggled: (String) -> Unit
) {
    val filters = listOf("특가", "예약가능", "쿠폰", "조식포함", "오션뷰", "시티뷰", "마운틴뷰")

    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            FilterChip(
                selected = false,
                onClick = { /* 전체 필터 모달 표시 */ },
                label = { Text("전체 필터") },
                leadingIcon = { Icon(Icons.Default.FilterList, contentDescription = "전체 필터") }
            )
        }
        items(filters) { filter ->
            val selected = selectedFilters.contains(filter)
            FilterChip(
                selected = selected,
                onClick = { onFilterToggled(filter) },
                label = { Text(filter) }
            )
        }
    }
}

