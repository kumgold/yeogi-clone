package com.example.yeogi.feature.searchdetail

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import java.time.LocalDate

// 데이터 모델 (임시)
data class Accommodation(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val rating: Double,
    val reviewCount: Int,
    val price: String,
    val isSpecialPrice: Boolean = false,
    val category: String,
    val address: String
)

/**
 * 숙소 검색 결과 메인 화면
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchDetailScreen() {
    // 임시 더미 데이터
    val recommendedAccommodation = Accommodation(0, "여기어때 추천 호텔", "https://picsum.photos/id/10/200/300", 4.9, 1203, "189,000원", true, "호텔", "서울 강남구 테헤란로")
    val horizontalAccommodations = remember {
        listOf(
            Accommodation(1, "[광고] 강남 호텔 A", "https://picsum.photos/id/20/200/300", 4.8, 987, "150,000원", true, "호텔", "서울 강남구"),
            Accommodation(2, "[광고] 역삼 펜션 B", "https://picsum.photos/id/30/200/300", 4.5, 543, "120,000원", false, "펜션", "서울 강남구"),
            Accommodation(3, "[광고] 삼성 리조트 C", "https://picsum.photos/id/40/200/300", 4.7, 876, "220,000원", true, "리조트", "서울 강남구"),
            Accommodation(4, "[광고] 선릉 모텔 D", "https://picsum.photos/id/50/200/300", 4.3, 321, "80,000원", false, "모텔", "서울 강남구")
        )
    }
    val verticalAccommodations = remember {
        listOf(
            Accommodation(5, "제주 신라 호텔", "https://picsum.photos/id/110/200/300", 4.9, 2345, "350,000원", true, "호텔", "제주 서귀포시"),
            Accommodation(6, "강릉 씨마크 호텔", "https://picsum.photos/id/120/200/300", 4.8, 1876, "280,000원", true, "호텔", "강원 강릉시"),
            Accommodation(7, "부산 파라다이스 호텔", "https://picsum.photos/id/130/200/300", 4.7, 1543, "250,000원", false, "호텔", "부산 해운대구"),
            Accommodation(8, "여수 베네치아 호텔", "https://picsum.photos/id/140/200/300", 4.6, 1234, "180,000원", true, "호텔", "전남 여수시"),
            Accommodation(9, "경주 힐튼 호텔", "https://picsum.photos/id/150/200/300", 4.5, 1111, "160,000원", false, "호텔", "경북 경주시")
        )
    }

    Scaffold(
        topBar = {
            SearchResultTopBar(
                searchQuery = "강남",
                startDate = LocalDate.now(),
                endDate = LocalDate.now().plusDays(1),
                guestCount = 2,
                onBackClick = { /* 뒤로가기 */ },
                onSearchClick = { /* 검색 화면으로 이동 */ },
                onKeywordSearchClick = { /* 키워드 검색 로직 */ }
            )
        },
        containerColor = Color(0xFFF5F5F5)
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            item {
                AccommodationTypeFilter()
            }

            item {
                FilterChips()
            }

            // 여기어때 추천 숙소
            item {
                Column(Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)) {
                    Text(
                        text = "여기어때 추천",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.ExtraBold,
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    VerticalAccommodationItem(item = recommendedAccommodation)
                }
            }

            // 지금 지역에서 주목할 숙소 (가로 리스트)
            item {
                RecommendationSection(
                    title = "지금 지역에서 주목할 숙소",
                    accommodations = horizontalAccommodations
                )
            }

            // 전체 숙소 목록 (세로 리스트)
            items(verticalAccommodations) { accommodation ->
                Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
                    VerticalAccommodationItem(item = accommodation)
                }
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
    Surface(
        color = Color.White,
        shadowElevation = 2.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBackClick) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "뒤로가기")
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
                        color = Color.Gray
                    )
                    Text(
                        text = "$guestCount 명",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
            }
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
            .background(Color(0xFFF5F5F5), RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(Icons.Default.Search, contentDescription = "검색 아이콘", tint = Color.Gray, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text("숙소 이름으로 검색", color = Color.Gray, fontSize = 15.sp)
    }
}


@Composable
fun AccommodationTypeFilter() {
    val types = listOf("전체", "호텔", "펜션", "리조트", "모텔", "캠핑", "게하")
    var selectedTypes by remember { mutableStateOf(setOf("전체")) }

    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Text(
                text = "숙소유형",
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                fontSize = 14.sp
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
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
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

/**
 * 가로 스크롤 숙소 리스트 섹션
 */
@Composable
fun RecommendationSection(title: String, accommodations: List<Accommodation>) {
    Column(modifier = Modifier.padding(top = 24.dp, bottom = 12.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.ExtraBold,
                )
                Spacer(modifier = Modifier.width(4.dp))
                // 광고 표시
                if (title.contains("주목할 숙소")) {
                    Text(
                        text = "AD",
                        color = Color.Gray,
                        fontSize = 12.sp,
                        modifier = Modifier
                            .background(Color(0xFFEEEEEE), CircleShape)
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            TextButton(onClick = { /* 전체보기 */ }) {
                Text("전체보기", fontWeight = FontWeight.SemiBold, color = Color.Gray)
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(accommodations) { accommodation ->
                HorizontalAccommodationItem(accommodation = accommodation)
            }
        }
    }
}

/**
 * 가로 스크롤에 사용될 숙소 아이템
 */
@Composable
fun HorizontalAccommodationItem(accommodation: Accommodation) {
    Column(
        modifier = Modifier
            .width(150.dp)
            .clickable { /* 상세 페이지로 이동 */ }
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(0.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(accommodation.imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = accommodation.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = accommodation.name,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.height(2.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Filled.Star, contentDescription = "별점", tint = Color(0xFFFFC107), modifier = Modifier.size(14.dp))
            Spacer(modifier = Modifier.width(2.dp))
            Text(text = "${accommodation.rating} (${accommodation.reviewCount})", fontSize = 12.sp, color = Color.Gray)
        }
        Spacer(modifier = Modifier.height(4.dp))
        Row(verticalAlignment = Alignment.Bottom) {
            if (accommodation.isSpecialPrice) {
                Text("특가", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Spacer(modifier = Modifier.width(4.dp))
            }
            Text(text = accommodation.price, fontWeight = FontWeight.ExtraBold, fontSize = 16.sp)
        }
    }
}

/**
 * 세로 스크롤에 사용될 숙소 아이템
 */
@Composable
fun VerticalAccommodationItem(item: Accommodation) {
    var isFavorite by remember { mutableStateOf(true) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* 상세 페이지로 이동 */ },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(item.imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = item.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .weight(0.3f)
                    .aspectRatio(3f / 4f)
                    .clip(RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(0.7f)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = item.category,
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(
                        onClick = { isFavorite = !isFavorite },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Favorite,
                            contentDescription = "찜하기",
                            tint = if (isFavorite) Color.Red else Color.LightGray
                        )
                    }
                }

                Text(
                    text = item.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = item.address,
                    fontSize = 13.sp,
                    color = Color.Gray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(vertical = 4.dp)
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Filled.Star,
                        contentDescription = "별점",
                        tint = Color(0xFFFFC107),
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = "${item.rating}",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = " (${item.reviewCount})",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = "1박",
                        fontSize = 12.sp,
                        color = Color.Gray,
                        modifier = Modifier.align(Alignment.Bottom)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = item.price,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 18.sp,
                    )
                }
            }
        }
    }
}