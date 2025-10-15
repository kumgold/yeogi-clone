package com.example.yeogi.feature.around

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.yeogi.SystemBarColor
import com.example.yeogi.core.model.Accommodation
import com.example.yeogi.core.util.toKRWString
import com.example.yeogi.ui.theme.YeogiTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AroundMeScreen(
    navController: NavController,
    viewModel: AroundViewModel = hiltViewModel()
) {
    val accommodations = emptyList<Accommodation>()
    val sheetState = rememberStandardBottomSheetState(
        skipHiddenState = false
    )
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = sheetState
    )
    val scope = rememberCoroutineScope()

    SystemBarColor(color = MaterialTheme.colorScheme.background)

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            BottomSheetContent(
                accommodations = accommodations,
                onClose = {
                    scope.launch { sheetState.hide() }
                }
            )
        },
        sheetPeekHeight = 200.dp,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetShadowElevation = 8.dp
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            MapBackground()
            MapOverlayUI(navController)
        }
    }
}

@Composable
fun MapOverlayUI(navController: NavController) {
    var selectedType by remember { mutableStateOf(0) } // 0: 숙박, 1: 대실

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(WindowInsets.statusBars.asPaddingValues())
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        AroundMeAppBar(navController = navController)
        Spacer(modifier = Modifier.height(12.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterChipButton(
                text = "숙박",
                isSelected = selectedType == 0,
                onClick = { selectedType = 0 }
            )
            FilterChipButton(
                text = "대실",
                isSelected = selectedType == 1,
                onClick = { selectedType = 1 }
            )
        }
    }
}

/**
 * 숙박/대실 등을 위한 흰색 원형 필터 버튼
 */
@Composable
fun FilterChipButton(text: String, isSelected: Boolean, onClick: () -> Unit) {
    val backgroundColor = if (isSelected) MaterialTheme.colorScheme.primary else Color.White
    val contentColor = if (isSelected) Color.White else Color.Black

    Button(
        onClick = onClick,
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = contentColor
        ),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp)
    ) {
        Text(text, fontWeight = FontWeight.Bold, fontSize = 14.sp)
    }
}

@Composable
fun AroundMeAppBar(navController: NavController) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "뒤로가기")
            }

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // 지역, 숙소 검색
                Row(
                    modifier = Modifier.clickable { /* 지역/숙소 검색 */ },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Search, contentDescription = "검색", tint = Color.Gray)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("지역, 숙소 검색", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }

                HorizontalDivider(color = Color.LightGray)

                Row(
                    modifier = Modifier.clickable { /* 날짜/인원 선택 */ },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.CalendarToday, contentDescription = "날짜", tint = Color.Gray, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("9월 10일 ~ 9월 11일", fontSize = 14.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(Icons.Default.Person, contentDescription = "인원", tint = Color.Gray, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("2명", fontSize = 14.sp)
                }
            }
        }
    }
}

/**
 * 실제 네이버 지도 SDK를 연동하기 전의 Placeholder
 */
@Composable
fun MapBackground() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE0F7FA)),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Naver Map Area", color = Color.Gray, fontWeight = FontWeight.Bold)
    }
}

/**
 * Bottom Sheet에 들어갈 콘텐츠 (현재는 Placeholder)
 */
@Composable
fun BottomSheetContent(
    accommodations: List<Accommodation>,
    onClose: () -> Unit
) {
    var selectedFilterIndex by remember { mutableStateOf(0) }
    val filters = listOf("추천순", "거리순", "가격순", "인기순", "평점순")
    val nearbyAccommodations = remember { accommodations.shuffled().take(15) }

    Column(modifier = Modifier.fillMaxSize()) {
        BottomSheetHeader(
            locationName = "역삼동",
            details = "9월 10일 ~ 9월 11일, 2명",
            onClose = onClose
        )

        FilterChipsRow(
            filters = filters,
            selectedFilterIndex = selectedFilterIndex,
            onFilterSelected = { selectedFilterIndex = it }
        )

        HorizontalDivider(color = Color.LightGray.copy(alpha = 0.5f))

        LazyColumn(
            contentPadding = PaddingValues(16.dp)
        ) {
            items(nearbyAccommodations) { accommodation ->
                AccommodationListItem(item = accommodation)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun BottomSheetHeader(locationName: String, details: String, onClose: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onClose) {
            Icon(Icons.Default.ArrowDropDown, contentDescription = "Bottom Sheet 닫기")
        }
        Spacer(modifier = Modifier.width(8.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(locationName, fontWeight = FontWeight.ExtraBold, fontSize = 18.sp)
            Text(details, color = Color.Gray, fontSize = 12.sp)
        }
        // 필요 시 오른쪽에 다른 아이콘 추가 (예: 정렬, 지도)
        IconButton(onClick = { /* 지도 아이콘 기능 */ }) {
            Icon(Icons.Default.Map, contentDescription = "지도로 보기")
        }
    }
}

@Composable
fun FilterChipsRow(
    filters: List<String>,
    selectedFilterIndex: Int,
    onFilterSelected: (Int) -> Unit
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(filters.size) { index ->
            val isSelected = selectedFilterIndex == index
            FilterChip(
                text = filters[index],
                isSelected = isSelected,
                onClick = { onFilterSelected(index) }
            )
        }
    }
}

@Composable
fun FilterChip(text: String, isSelected: Boolean, onClick: () -> Unit) {
    val backgroundColor = if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.1f) else Color.Transparent
    val contentColor = if (isSelected) MaterialTheme.colorScheme.primary else Color.Gray
    val borderColor = if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.3f) else Color.LightGray

    Box(
        modifier = Modifier
            .clip(CircleShape)
            .border(1.dp, borderColor, CircleShape)
            .background(backgroundColor)
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text, color = contentColor, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
fun AccommodationListItem(item: Accommodation) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* 상세 페이지로 이동 */ },
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
                .size(90.dp)
                .clip(RoundedCornerShape(8.dp))
        )
        Spacer(modifier = Modifier.width(16.dp))
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
            Text(text = item.price.toKRWString(), fontWeight = FontWeight.ExtraBold, fontSize = 16.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AroundMeScreenPreview() {
    YeogiTheme {
        AroundMeScreen(navController = rememberNavController())
    }
}