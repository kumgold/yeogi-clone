package com.example.yeogi.feature.hotel

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
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
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.yeogi.core.model.Accommodation
import com.example.yeogi.core.util.toKRWString
import com.example.yeogi.feature.hotel.data.HotelCategory
import com.example.yeogi.ui.theme.YeogiTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HotelScreen(
    viewModel: HotelViewModel = viewModel(),
    navigateToAccommodation: (Int) -> Unit,
    navigateToSearchDetail: () -> Unit,
    popBackStack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "호텔•리조트",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "뒤로가기",
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item {
                HotelCategorySection(
                    categories = uiState.categories,
                    navigateToSearchDetail = navigateToSearchDetail
                )
            }
            item { HotelSearchSection() }
            item { RegionSelectionSection(regions = uiState.regions) }
            item { AdBannerSection() }
            item {
                HotelRecommendationSection(
                    title = "요즘 뜨는 호텔",
                    hotels = uiState.popularHotels,
                    onItemClick = { id -> navigateToAccommodation(id) },
                    onViewAllClick = { /* 전체보기 화면으로 이동 */ }
                )
            }
            item {
                HotelRecommendationSection(
                    title = "프리미엄 블랙",
                    hotels = uiState.premiumHotels,
                    onItemClick = { id -> navigateToAccommodation(id) },
                    onViewAllClick = { /* 전체보기 화면으로 이동 */ }
                )
            }
        }
    }
}

@Composable
private fun HotelCategorySection(
    categories: List<HotelCategory>,
    navigateToSearchDetail: () -> Unit
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(categories) { category ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.clickable { navigateToSearchDetail() }
            ) {
                AsyncImage(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.LightGray),
                    model = category.imageUrl,
                    contentDescription = category.name,
                    contentScale = ContentScale.Crop,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = category.name,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}

@Composable
private fun HotelSearchSection() {
    Card(
        modifier = Modifier.padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        SearchInfoRow(
            icon = Icons.Default.LocationOn,
            title = "어디로 떠나시나요?",
            content = "숙소, 지역, 지하철역 검색",
            onClick = { /* TODO: 지역 선택 화면으로 이동 */ }
        )
    }
}

@Composable
private fun RegionSelectionSection(regions: Map<String, List<String>>) {
    var expandedRegion by remember { mutableStateOf<String?>(null) }

    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text("어디로 떠나볼까요?", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))

        val chunkedRegions = regions.keys.chunked(3)

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(BorderStroke(1.dp, Color.LightGray))
        ) {
            chunkedRegions.forEachIndexed { rowIndex, rowItems ->
                Row(Modifier.fillMaxWidth()) {
                    rowItems.forEachIndexed { itemIndex, region ->
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clickable {
                                    expandedRegion = if (expandedRegion == region) null else region
                                }
                                .padding(vertical = 12.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = region,
                                style = MaterialTheme.typography.labelMedium
                            )
                        }

                        if (itemIndex < rowItems.size - 1) {
                            Box(
                                modifier = Modifier
                                    .width(1.dp)
                                    .height(42.dp)
                                    .background(Color.LightGray)
                            )
                        }
                    }
                }

                AnimatedVisibility(
                    visible = rowItems.contains(expandedRegion),
                    enter = expandVertically(animationSpec = tween(300)) + fadeIn(animationSpec = tween(300)),
                    exit = shrinkVertically(animationSpec = tween(300)) + fadeOut(animationSpec = tween(300))
                ) {
                    val details = regions[expandedRegion].orEmpty()
                    ExpandedDetailView(details = details)
                }

                if (rowIndex < chunkedRegions.size - 1) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(Color.LightGray)
                    )
                }
            }
        }
    }
}

/**
 * 확장되었을 때 보여줄 상세 지역 그리드 Composable
 */
@Composable
private fun ExpandedDetailView(details: List<String>) {
    val chunkedDetails = details.chunked(4)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Gray.copy(alpha = 0.05f))
            .padding(8.dp)
    ) {
        chunkedDetails.forEach { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                rowItems.forEach { detail ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(4.dp))
                            .clickable { /* TODO: 상세 지역($detail)으로 검색 */ }
                            .padding(vertical = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = detail, style = MaterialTheme.typography.labelMedium)
                    }
                }
                repeat(4 - rowItems.size) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
private fun AdBannerSection() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Gray.copy(alpha = 0.3f)),
            contentAlignment = Alignment.Center
        ) {
            Text("광고 배너 영역", color = Color.White)
        }
    }
}

@Composable
private fun HotelRecommendationSection(
    title: String,
    hotels: List<Accommodation>,
    onItemClick: (Int) -> Unit,
    onViewAllClick: () -> Unit
) {
    if (hotels.isEmpty()) return

    // 화면 너비를 기준으로 그리드의 동적 높이를 계산
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    // 그리드 아이템 하나의 너비 계산
    // 양쪽 패딩(16*2)과 아이템 사이 간격(12)을 제외한 공간을 2로 나눔
    val itemWidth = (screenWidth - 32.dp - 12.dp) / 2

    // 아이템 하나의 높이 추정
    // 이미지 높이(너비와 동일) + 하단 텍스트 영역 높이(여유있게 80dp로 설정)
    val itemHeight = itemWidth + 80.dp

    // 총 행(row)의 개수 계산 (2열 그리드이므로 아이템 개수를 2로 나눈 올림)
    val numberOfRows = (hotels.size + 1) / 2

    // 그리드의 전체 높이 계산
    // (아이템 높이 * 행 개수) + (행 사이 간격 * (행 개수 - 1))
    val gridHeight = (itemHeight * numberOfRows) + (12.dp * (numberOfRows - 1))

    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(title, style = MaterialTheme.typography.titleLarge)
            Row(
                modifier = Modifier.clickable(onClick = onViewAllClick),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("전체보기", fontSize = 14.sp, color = Color.Gray)
                Icon(
                    modifier = Modifier.size(20.dp),
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = "전체보기",
                    tint = Color.Gray
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.height(gridHeight),
            userScrollEnabled = false
        ) {
            items(hotels) { hotel ->
                HotelCard(hotel = hotel, onClick = { onItemClick(hotel.id) })
            }
        }
    }
}

@Composable
private fun HotelCard(hotel: Accommodation, onClick: () -> Unit) {
    Column(modifier = Modifier.clickable(onClick = onClick)) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(8.dp)),
            model = hotel.imageUrl,
            contentDescription = hotel.name,
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = hotel.name,
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
            maxLines = 1
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                modifier = Modifier.size(16.dp),
                imageVector = Icons.Default.Star,
                contentDescription = "Rating",
                tint = Color(0xFFFFC107)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = hotel.rating.toString(), fontSize = 14.sp)
        }
        Text(
            text = hotel.price.toKRWString(),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
private fun SearchInfoRow(
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    title: String,
    content: String,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(12.dp))
        }
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = content,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HotelScreenPreview() {
    YeogiTheme {
        HotelScreen(
            navigateToAccommodation = {},
            navigateToSearchDetail = {},
            popBackStack = {}
        )
    }
}