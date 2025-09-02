package com.example.yeogi.feature.favorite

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.yeogi.feature.home.Accommodation
import com.example.yeogi.ui.theme.YeogiTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(innerPadding: PaddingValues) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("숙소", "맛집", "액티비티")

    val favoriteAccommodations = remember {
        (1..5).map {
            Accommodation(it, "찜한 호텔 $it", 4.9, 310, "135,000원", "https://picsum.photos/seed/fav${it}/400", isSpecialPrice = true)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .background(Color(0xFFF5F5F5))
    ) {
        CenterAlignedTopAppBar(
            title = { Text("찜", fontWeight = FontWeight.Bold) },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = Color(0xFFF5F5F5)
            )
        )

        TabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = Color(0xFFF5F5F5),
            contentColor = MaterialTheme.colorScheme.primary
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = { Text(text = title, fontWeight = FontWeight.Bold) }
                )
            }
        }

        when (selectedTabIndex) {
            0 -> { 
                if (favoriteAccommodations.isNotEmpty()) {
                    FavoriteList(items = favoriteAccommodations)
                } else {
                    EmptyFavoritesView()
                }
            }
            1 -> EmptyFavoritesView(message = "찜한 맛집이 없어요.")
            2 -> EmptyFavoritesView(message = "찜한 액티비티가 없어요.")
        }
    }
}

@Composable
fun FavoriteList(items: List<Accommodation>) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(items) { item ->
            FavoriteItemCard(item = item)
        }
    }
}

@Composable
fun FavoriteItemCard(item: Accommodation) {
    var isFavorite by remember { mutableStateOf(true) } // 찜 목록이므로 기본값은 true

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* 상세 페이지로 이동 */ },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(item.imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = item.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.width(16.dp))

            // 텍스트 정보
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.Star, contentDescription = "별점", tint = Color(0xFFFFC107), modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(text = "${item.rating} (${item.reviewCount})", fontSize = 12.sp, color = Color.Gray)
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.Bottom) {
                    if (item.isSpecialPrice) {
                        Text("특가", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Spacer(modifier = Modifier.width(4.dp))
                    }
                    Text(text = item.price, fontWeight = FontWeight.ExtraBold, fontSize = 16.sp)
                }
            }

            // 찜하기 버튼 (찜 해제 기능)
            IconButton(onClick = { isFavorite = !isFavorite }) {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = "찜하기",
                    tint = Color.Red,
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    }
}

@Composable
fun EmptyFavoritesView(message: String = "찜 내역이 없어요.") {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = Icons.Outlined.FavoriteBorder,
                contentDescription = "찜 내역 없음",
                modifier = Modifier.size(64.dp),
                tint = Color.Gray
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = message,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.DarkGray
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "관심 있는 상품을 찜 해보세요!",
                fontSize = 14.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FavoritesScreenPreview() {
    YeogiTheme {
        FavoritesScreen(innerPadding = PaddingValues(0.dp))
    }
}