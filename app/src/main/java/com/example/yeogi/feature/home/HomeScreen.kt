package com.example.yeogi.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Flight
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Kayaking
import androidx.compose.material.icons.filled.KingBed
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.yeogi.SystemBarColor
import com.example.yeogi.dummy.Accommodation
import com.example.yeogi.dummy.dummyAccommodation
import com.example.yeogi.ui.theme.Background
import com.example.yeogi.ui.theme.YeogiTheme

data class ServiceCategory(val name: String, val icon: ImageVector)

@Composable
fun HomeScreen(innerPadding: PaddingValues) {
    SystemBarColor(color = Background)

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .background(Background),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item { HomeHeader() }

        item { ServiceCategorySection() }

        item {
            RecommendationSection(
                title = "우리 동네 BEST",
                accommodations = remember {
                    dummyAccommodation.subList(0, 5)
                }
            )
        }

        item {
            RecommendationSection(
                title = "이번 주 특가",
                accommodations = remember {
                    dummyAccommodation.subList(6, 10)
                }
            )
        }
        item { Spacer(modifier = Modifier.height(16.dp)) }
    }
}

@Composable
fun HomeHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Background)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "여기어때.", fontSize = 22.sp, fontWeight = FontWeight.ExtraBold, color = MaterialTheme.colorScheme.primary)
        Row {
            IconButton(onClick = { /* 검색 */ }) { Icon(Icons.Default.Search, contentDescription = "검색", tint = Color.Black) }
            IconButton(onClick = { /* 알림 */ }) { Icon(Icons.Default.NotificationsNone, contentDescription = "알림", tint = Color.Black) }
        }
    }
}

@Composable
fun ServiceCategorySection() {
    val categories = remember {
        listOf(
            ServiceCategory("호텔", Icons.Default.Business),
            ServiceCategory("펜션", Icons.Default.Home),
            ServiceCategory("모텔", Icons.Default.KingBed),
            ServiceCategory("항공", Icons.Default.Flight),
            ServiceCategory("렌터카", Icons.Default.DirectionsCar),
            ServiceCategory("액티비티", Icons.Default.Kayaking),
            ServiceCategory("맛집", Icons.Default.Restaurant),
            ServiceCategory("더보기", Icons.Default.MoreHoriz),
        )
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(vertical = 16.dp)) {
            categories.chunked(4).forEach { rowItems ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    rowItems.forEach { category ->
                        CategoryIcon(category = category)
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryIcon(category: ServiceCategory) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { /* 각 카테고리 화면으로 이동 */ }
    ) {
        Icon(
            category.icon,
            contentDescription = category.name,
            modifier = Modifier.size(28.dp),
            tint = Color(0xFF333333)
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(text = category.name, fontSize = 12.sp, color = Color.DarkGray)
    }
}

@Composable
fun RecommendationSection(title: String, accommodations: List<Accommodation>) {
    Column(modifier = Modifier.padding(top = 16.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
            )
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
                AccommodationItem(accommodation = accommodation)
            }
        }
    }
}

@Composable
fun AccommodationItem(accommodation: Accommodation) {
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
            elevation = CardDefaults.cardElevation(0.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFEEEEEE)
            )
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

@Preview(showBackground = true, backgroundColor = 0xFFF5F5F5)
@Composable
fun HomeScreenPreview() {
    YeogiTheme {
        HomeScreen(innerPadding = PaddingValues(0.dp))
    }
}