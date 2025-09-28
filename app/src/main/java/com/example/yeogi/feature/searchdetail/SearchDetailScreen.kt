package com.example.yeogi.feature.searchdetail

import androidx.compose.foundation.background
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
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.yeogi.feature.searchdetail.ui.VerticalAccommodationItem
import com.example.yeogi.shared.ui.RecommendationSection
import java.time.LocalDate

/**
 * 숙소 검색 결과 메인 화면
 */
@Composable
fun SearchDetailScreen(
    viewModel: SearchDetailViewModel = viewModel(),
    navigateToAccommodation: (Int) -> Unit,
    popBackStack: () -> Unit,
) {
    val recommendedAccommodation = viewModel.getAccommodations().first()
    val horizontalAccommodations = remember {
        viewModel.getAccommodations().subList(1, 6)
    }
    val verticalAccommodations = remember {
        viewModel.getAccommodations().toList()
    }

    LazyColumn(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding()
            .fillMaxSize()
    ) {
        item {
            SearchResultTopBar(
                searchQuery = "강남",
                startDate = LocalDate.now(),
                endDate = LocalDate.now().plusDays(1),
                guestCount = 2,
                onBackClick = { popBackStack() },
                onSearchClick = { /* 검색 화면으로 이동 */ },
                onKeywordSearchClick = { /* 키워드 검색 로직 */ }
            )
        }

        item {
            AccommodationTypeFilter()
        }

        item {
            FilterChips()
        }

        item {
            Column(Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)) {
                Text(
                    text = "여기어때 추천",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(12.dp))
                VerticalAccommodationItem(item = recommendedAccommodation)
            }
        }

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

        items(verticalAccommodations) { accommodation ->
            Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
                VerticalAccommodationItem(item = accommodation)
            }
        }
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
    onKeywordSearchClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
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
            modifier = Modifier.weight(3f),
            onClick = onKeywordSearchClick
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(
            modifier = Modifier.weight(1f)
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

@Composable
fun SearchKeywordField(
    modifier: Modifier,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(
                MaterialTheme.colorScheme.onBackground,
                RoundedCornerShape(8.dp)
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
            tint = MaterialTheme.colorScheme.background
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "숙소 이름으로 검색",
            color = MaterialTheme.colorScheme.background,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun AccommodationTypeFilter() {
    val types = listOf("전체", "호텔", "펜션", "리조트", "모텔", "캠핑", "게하")
    var selectedTypes by remember { mutableStateOf(setOf("전체")) }

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
                onClick = {
                    // "전체"를 누르면 다른 선택 해제, 다른 버튼 누르면 "전체" 선택 해제
                    val newSelection = if (type == "전체") {
                        setOf("전체")
                    } else {
                        if (isSelected) selectedTypes - type else selectedTypes + type - "전체"
                    }
                    selectedTypes = if (newSelection.isEmpty()) setOf("전체") else newSelection
                },
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
fun FilterChips() {
    val filters = listOf("예약가능", "쿠폰", "포인트 적립", "조식포함", "반려견")

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
            var selected by remember { mutableStateOf(false) }
            FilterChip(
                selected = selected,
                onClick = { selected = !selected },
                label = { Text(filter) }
            )
        }
    }
}

