package com.example.yeogi.feature.search.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.yeogi.core.model.RecentSearch
import com.example.yeogi.feature.search.SearchViewModel
import com.example.yeogi.core.ui.DateGuestSelectionBottomSheet
import com.example.yeogi.core.ui.RecentHistorySection
import com.example.yeogi.core.util.getFormattedMonthDay
import kotlinx.coroutines.launch
import java.time.LocalDate


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DomesticAccommodationContent(
    viewModel: SearchViewModel,
    navigateToDetail: (String) -> Unit,
) {
    val recentSearches = remember {
        viewModel.domesticRecentSearches.toMutableStateList()
    }
    val searchRankings = listOf(
        "제주도", "강릉", "부산", "여수", "경주", "속초", "서울", "가평", "해운대", "전주"
    )

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()
    var isSearchSheetOpen by remember { mutableStateOf(false) }

    val dateGuestSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var isDateGuestSheetOpen by remember { mutableStateOf(false) }

    val startDate by remember { mutableStateOf(viewModel.startDate) }
    val endDate by remember { mutableStateOf(viewModel.endDate) }
    val guestCount by remember { mutableIntStateOf(viewModel.guest) }

    if (isSearchSheetOpen) {
        ModalBottomSheet(
            onDismissRequest = { isSearchSheetOpen = false },
            sheetState = sheetState,
            modifier = Modifier.fillMaxSize(),
            containerColor = Color.White
        ) {
            SearchBottomSheetContent(
                recentSearches = recentSearches,
                searchRankings = searchRankings,
                onClearAll = { recentSearches.clear() },
                onDeleteItem = { item -> recentSearches.remove(item) },
                onDismiss = {
                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            isSearchSheetOpen = false
                        }
                    }
                },
                navigateToDetail = { query ->
                    navigateToDetail(query)
                }
            )
        }
    }

    if (isDateGuestSheetOpen) {
        DateGuestSelectionBottomSheet(
            initialStartDate = startDate,
            initialEndDate = endDate,
            initialGuestCount = guestCount,
            sheetState = dateGuestSheetState,
            onDismiss = {
                scope.launch { dateGuestSheetState.hide() }.invokeOnCompletion {
                    if (!dateGuestSheetState.isVisible) isDateGuestSheetOpen = false
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

    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            SearchInputField(onClick = { isSearchSheetOpen = true })
            Spacer(modifier = Modifier.height(12.dp))
            DateAndGuestPicker(
                startDate = startDate,
                endDate = endDate,
                guestCount = guestCount,
                onClick = { isDateGuestSheetOpen = true }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = { isSearchSheetOpen = true },
                    modifier = Modifier.weight(1f).height(50.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text(
                        text = "숙소 검색",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                TextButton(
                    onClick = { /* 지도 화면으로 이동 */ },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.height(50.dp),
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Map,
                            contentDescription = "지도 아이콘",
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "지도로 찾기",
                            color = MaterialTheme.colorScheme.onBackground,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (recentSearches.isNotEmpty()) {
            RecentHistorySection(
                items = recentSearches,
                onClearAll = { recentSearches.clear() },
                onDeleteItem = { item -> recentSearches.remove(item) },
                onClick = navigateToDetail
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        SearchRankingSection(rankings = searchRankings)
    }
}

/**
 * 숙소 검색 바텀 시트
 */
@Composable
fun SearchBottomSheetContent(
    recentSearches: MutableList<RecentSearch>,
    searchRankings: List<String>,
    onClearAll: () -> Unit,
    onDeleteItem: (RecentSearch) -> Unit,
    onDismiss: () -> Unit,
    navigateToDetail: (String) -> Unit
) {
    var searchText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text(
                text = "국내 숙소",
                modifier = Modifier.align(Alignment.Center),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
            IconButton(
                onClick = onDismiss,
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "닫기",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = searchText,
            onValueChange = { searchText = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(
                    text = "지역, 숙소 검색",
                    color = MaterialTheme.colorScheme.onBackground
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "검색 아이콘",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            },
            shape = RoundedCornerShape(8.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.background,
                unfocusedContainerColor = MaterialTheme.colorScheme.background,
                focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.onBackground
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(24.dp))

        if (recentSearches.isNotEmpty()) {
            RecentHistorySection(
                items = recentSearches,
                onClearAll = onClearAll,
                onDeleteItem = onDeleteItem,
                onClick = { query ->
                    navigateToDetail(query)
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        SearchRankingSection(rankings = searchRankings)
    }
}


@Composable
fun SearchInputField(onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onBackground,
                shape = RoundedCornerShape(8.dp)
            )
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.background)
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = "검색 아이콘",
            tint = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = "지역, 숙소 검색",
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}


@Composable
fun DateAndGuestPicker(
    startDate: LocalDate,
    endDate: LocalDate,
    guestCount: Int,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val dateRange = "${startDate.getFormattedMonthDay()} - ${endDate.getFormattedMonthDay()}"

        Row(
            modifier = Modifier
                .weight(2f)
                .fillMaxHeight()
                .border(1.dp, MaterialTheme.colorScheme.onBackground, RoundedCornerShape(8.dp))
                .clip(RoundedCornerShape(8.dp))
                .clickable { onClick() }
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(20.dp),
                imageVector = Icons.Default.CalendarToday,
                contentDescription = "달력 아이콘",
                tint = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = dateRange,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .border(1.dp, MaterialTheme.colorScheme.onBackground, RoundedCornerShape(8.dp))
                .clip(RoundedCornerShape(8.dp))
                .clickable { onClick() }
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "인원 아이콘",
                modifier = Modifier.size(20.dp),
                tint = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "성인 $guestCount",
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

