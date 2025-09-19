package com.example.yeogi.feature.hotel

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
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.yeogi.feature.hotel.data.HotelCategory
import com.example.yeogi.feature.hotel.data.RecommendedHotel
import com.example.yeogi.ui.theme.YeogiTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HotelScreen(
    popBackStack: () -> Unit
) {
    val categories = remember {
        listOf(
            HotelCategory("전체", "https://..."),
            HotelCategory("5성급", "https://..."),
            HotelCategory("리조트", "https://..."),
            HotelCategory("가성비", "https://..."),
            HotelCategory("호캉스", "https://..."),
            HotelCategory("반려견", "https://..."),
            HotelCategory("신규", "https://...")
        )
    }
    val regions = remember {
        listOf("서울", "경기", "인천", "강원", "부산", "제주", "여수", "경주", "전주")
    }
    val popularHotels = remember {
        listOf(
            RecommendedHotel(1, "조선 팰리스 서울 강남", "https://images.unsplash.com/photo-1566073771259-6a8506099945", 4.9, "450,000원"),
            RecommendedHotel(2, "시그니엘 서울", "https://images.unsplash.com/photo-1582719508461-905c673771fd", 4.9, "550,000원"),
            RecommendedHotel(3, "파라다이스시티", "https://images.unsplash.com/photo-1549294413-26f195200c16", 4.8, "380,000원"),
            RecommendedHotel(4, "롯데호텔 제주", "https://images.unsplash.com/photo-1618773928121-c32242e63f39", 4.7, "320,000원")
        )
    }
    val premiumHotels = remember { popularHotels.shuffled() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("호텔") },
                navigationIcon = {
                    IconButton(onClick = { popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "뒤로가기")
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
            item { HotelCategorySection(categories = categories) }
            item { HotelSearchSection() }
            item { RegionSelectionSection(regions = regions) }
            item { AdBannerSection() }
            item {
                HotelRecommendationSection(
                    title = "요즘 뜨는 호텔",
                    hotels = popularHotels,
                    onItemClick = { /* 호텔 상세로 이동 */ },
                    onViewAllClick = { /* 전체보기 화면으로 이동 */ }
                )
            }
            item {
                HotelRecommendationSection(
                    title = "프리미엄 블랙",
                    hotels = premiumHotels,
                    onItemClick = { /* 호텔 상세로 이동 */ },
                    onViewAllClick = { /* 전체보기 화면으로 이동 */ }
                )
            }
            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}


@Composable
private fun HotelCategorySection(categories: List<HotelCategory>) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(categories) { category ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.clickable {  }
            ) {
                AsyncImage(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray),
                    model = category.imageUrl,
                    contentDescription = category.name,
                    contentScale = ContentScale.Crop,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = category.name, fontSize = 13.sp)
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
private fun RegionSelectionSection(regions: List<String>) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text("어디로 떠나볼까요?", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))
        LazyVerticalGrid(
            modifier = Modifier.height(150.dp),
            columns = GridCells.Fixed(3),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            userScrollEnabled = false
        ) {
            items(regions) { region ->
                Box(
                    modifier = Modifier
                        .border(BorderStroke(1.dp, Color.LightGray), shape = RectangleShape)
                        .clickable { /* TODO: 해당 지역으로 검색 */ }
                        .padding(vertical = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = region, fontSize = 14.sp)
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
                .background(Color.Gray.copy(alpha = 0.3f)), // Placeholder
            contentAlignment = Alignment.Center
        ) {
            Text("광고 배너 영역", color = Color.White)
        }
    }
}

@Composable
private fun HotelRecommendationSection(
    title: String,
    hotels: List<RecommendedHotel>,
    onItemClick: (Int) -> Unit,
    onViewAllClick: () -> Unit
) {
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
            modifier = Modifier.height(480.dp),
            userScrollEnabled = false
        ) {
            items(hotels) { hotel ->
                HotelCard(hotel = hotel, onClick = { onItemClick(hotel.id) })
            }
        }
    }
}

@Composable
private fun HotelCard(hotel: RecommendedHotel, onClick: () -> Unit) {
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
        Text(text = hotel.price, fontWeight = FontWeight.ExtraBold, fontSize = 16.sp)
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
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = content,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun HotelScreenPreview() {
    YeogiTheme {
        HotelScreen(
            popBackStack = {}
        )
    }
}