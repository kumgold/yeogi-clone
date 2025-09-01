package com.example.yeogi

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Flight
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Kayaking
import androidx.compose.material.icons.filled.KingBed
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.yeogi.ui.theme.YeogiTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager


data class BottomNavItem(val label: String, val icon: ImageVector)
data class ServiceCategory(val name: String, val icon: ImageVector)
data class Accommodation(val id: Int, val name: String, val rating: Double, val reviewCount: Int, val price: String, val isSpecialPrice: Boolean = false)


@Composable
fun YeogiMainScreen() {
    Scaffold(
        bottomBar = { HomeBottomNavigationBar() }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // 상단 로고 및 아이콘
            item { HomeHeader() }

            // 위치/날짜 선택 바
            item { LocationDateSelector() }

            // 서비스 카테고리 그리드
            item { ServiceCategoryGrid() }

            // 프로모션 배너
            item { PromotionSection() }

            // 추천 숙소 섹션
            item {
                RecommendationSection(
                    title = "우리 동네 BEST",
                    accommodations = remember {
                        (1..5).map {
                            Accommodation(it, "역삼 호텔 $it", 4.8, 120, "120,000원")
                        }
                    }
                )
            }

            item {
                RecommendationSection(
                    title = "이번 주 특가",
                    accommodations = remember {
                        (6..10).map {
                            Accommodation(it, "강남 펜션 ${it-5}", 4.9, 250, "89,000원", isSpecialPrice = true)
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun HomeHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "여기어때.", fontSize = 22.sp, fontWeight = FontWeight.ExtraBold, color = MaterialTheme.colorScheme.primary)
        Row {
            IconButton(onClick = { /* 검색 */ }) {
                Icon(Icons.Default.Search, contentDescription = "검색")
            }
            IconButton(onClick = { /* 알림 */ }) {
                Icon(Icons.Default.NotificationsNone, contentDescription = "알림")
            }
        }
    }
}

@Composable
fun LocationDateSelector() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = "어디로 떠날까요?", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text(text = "10월 10일 ~ 10월 11일, 2명", fontSize = 14.sp, color = Color.Gray)
            }
            Icon(Icons.Default.ChevronRight, contentDescription = "선택하기")
        }
    }
}

@Composable
fun ServiceCategoryGrid() {
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

    Column(modifier = Modifier.padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            (0..3).forEach { index -> CategoryIcon(category = categories[index]) }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            (4..7).forEach { index -> CategoryIcon(category = categories[index]) }
        }
    }
}

@Composable
fun CategoryIcon(category: ServiceCategory) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(category.icon, contentDescription = category.name, modifier = Modifier.size(28.dp))
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = category.name, fontSize = 12.sp)
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun PromotionSection() {
    HorizontalPager(
        count = 3,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .height(150.dp),
        contentPadding = PaddingValues(end = 8.dp)
    ) { page ->
        Card(
            modifier = Modifier.fillMaxSize(),
            shape = RoundedCornerShape(12.dp)
        ) {
            Box(
                modifier = Modifier.background(Color.DarkGray),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "프로모션 ${page + 1}", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun RecommendationSection(title: String, accommodations: List<Accommodation>) {
    Column(modifier = Modifier.padding(vertical = 12.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
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
    Column(modifier = Modifier.width(150.dp)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.LightGray)
        ) {
            // 이미지 로딩 라이브러리 (Coil, Glide) 사용
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = accommodation.name,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Filled.Star, contentDescription = "별점", tint = Color(0xFFFFC107), modifier = Modifier.size(14.dp))
            Spacer(modifier = Modifier.width(2.dp))
            Text(text = "${accommodation.rating} (${accommodation.reviewCount})", fontSize = 12.sp, color = Color.Gray)
        }
        Row {
            if (accommodation.isSpecialPrice) {
                Text("특가", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Spacer(modifier = Modifier.width(4.dp))
            }
            Text(text = accommodation.price, fontWeight = FontWeight.ExtraBold, fontSize = 16.sp)
        }
    }
}

@Composable
fun HomeBottomNavigationBar() {
    var selectedItem by remember { mutableStateOf(0) }
    val navItems = listOf(
        BottomNavItem("홈", Icons.Filled.Home),
        BottomNavItem("내주변", Icons.Filled.LocationOn),
        BottomNavItem("찜", Icons.Filled.FavoriteBorder),
        BottomNavItem("내정보", Icons.Filled.Person)
    )

    NavigationBar {
        navItems.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                selected = selectedItem == index,
                onClick = { selectedItem = index }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun YeogiAppPreview() {
    YeogiTheme {
        YeogiMainScreen()
    }
}