package com.example.yeogi.feature.search.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.yeogi.dummy.dummyKeywords
import com.example.yeogi.dummy.dummyRegions
import com.example.yeogi.ui.theme.Background

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun LeisureTicketContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .padding(16.dp)
        ) {
            // 여행지 검색 영역
            SearchInputField(onClick = { /* 레저/티켓 검색 화면으로 이동 */ })
            Spacer(modifier = Modifier.height(24.dp))

            // 추천 검색어
            Text("추천 검색어", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(16.dp))
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                dummyKeywords.forEach { keyword ->
                    KeywordChip(text = keyword, onClick = { /* 키워드로 검색 */ })
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // 추천 지역
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(vertical = 24.dp) // 상하 패딩을 더 줌
        ) {
            Text(
                "추천 지역",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(horizontal = 16.dp) // 좌우 패딩만 적용
            )
            Spacer(modifier = Modifier.height(16.dp))
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                items(dummyRegions) { region ->
                    RegionCircleItem(
                        name = region.name,
                        imageUrl = region.imageUrl,
                        onClick = { /* 지역으로 검색 */ }
                    )
                }
            }
        }
    }
}

/**
 * 추천 검색어를 위한 직사각형 Chip Composable
 */
@Composable
fun KeywordChip(text: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .background(Background, RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Text(text, color = Color.DarkGray, fontSize = 14.sp)
    }
}

/**
 * 추천 지역을 위한 원형 아이템 Composable
 */
@Composable
fun RegionCircleItem(name: String, imageUrl: String, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = name,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}
