package com.example.yeogi.feature.accommodation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.BusinessCenter
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.LocalParking
import androidx.compose.material.icons.filled.Pool
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.SmokeFree
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.yeogi.R
import com.example.yeogi.SystemBarColor
import com.example.yeogi.data.Accommodation
import com.example.yeogi.data.Facility
import com.example.yeogi.data.Review
import com.example.yeogi.ui.theme.Yellow


@Composable
fun AccommodationDetailScreen(
    accommodation: Accommodation,
    popBackStack: () -> Unit,
) {
    SystemBarColor(White)
    Scaffold(
        bottomBar = { BookingBottomBar(accommodation.price) }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(bottom = paddingValues.calculateBottomPadding())
        ) {
            item {
                ImageHeader(
                    accommodation = accommodation,
                    popBackStack = popBackStack
                )
            }
            item { MainInfoSection(accommodation) }
            item { SectionDivider() }
            item { FacilityInfoSection(accommodation.facilities) }
            item { SectionDivider() }
            item { LocationSection() }
            item { SectionDivider() }
            item { UsageInfoSection(accommodation.checkInTime, accommodation.checkOutTime, accommodation.usageInfo) }
            item { SectionDivider() }
            item { NoticeSection(accommodation.notice) }
            item { SectionDivider() }
            item { ReviewSection(accommodation.rating, accommodation.reviews) }
        }
    }
}


@Composable
fun MainInfoSection(accommodation: Accommodation) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = accommodation.name,
            style = MaterialTheme.typography.headlineLarge
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Filled.Star, contentDescription = "Rating", tint = Yellow)
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "${accommodation.rating}",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Text(
                text = "리뷰 ${accommodation.reviewCount}개",
                color = Color.Gray,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Outlined.LocationOn,
                contentDescription = "Address",
                tint = Color.Gray,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = accommodation.address,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.DarkGray
            )
        }
    }
}


@Composable
fun FacilityInfoSection(facilities: List<Facility>) {
    LazyRow(
        modifier = Modifier.padding(all = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(facilities) { facility ->
            FacilityItem(facility)
        }
    }
}


@Composable
fun FacilityItem(facility: Facility) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            facility.icon,
            modifier = Modifier.size(20.dp),
            contentDescription = facility.name,
            tint = Color.Black
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(facility.name, fontSize = 12.sp)
    }
}


@Composable
fun LocationSection() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            stringResource(R.string.location_and_peripheral_information),
            style = MaterialTheme.typography.titleLarge
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .padding(top = 16.dp)
                .background(Color.LightGray, RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text("지도 표시 영역")
        }
    }
}


@Composable
fun UsageInfoSection(checkIn: String, checkOut: String, usageInfo: String) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(stringResource(R.string.introduce_accommodation), style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))
        InfoRow(stringResource(R.string.check_in), checkIn)
        HorizontalDivider(
            modifier = Modifier.padding(vertical = 8.dp),
        )
        InfoRow(stringResource(R.string.check_out), checkOut)
        HorizontalDivider(
            modifier = Modifier.padding(vertical = 8.dp),
        )
        Text(
            text = usageInfo,
            modifier = Modifier.padding(top = 8.dp),
            style = MaterialTheme.typography.bodyMedium,
            color = Color.DarkGray
        )
    }
}


@Composable
fun NoticeSection(notice: String) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            stringResource(R.string.notice),
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = notice,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.DarkGray
        )
    }
}

@Composable
fun ReviewSection(rating: Double, reviews: List<Review>) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            "리뷰 ${reviews.size}개",
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        stringResource(R.string.total_rating),
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        text = "$rating",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        reviews.forEach { review ->
            ReviewItem(review)
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 8.dp),
            )
        }
    }
}

@Composable
fun ReviewItem(review: Review) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(review.userName, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.width(8.dp))
            Text(review.date, color = Color.Gray, fontSize = 12.sp)
        }
        Row(
            modifier = Modifier.padding(vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            (1..5).forEach { index ->
                Icon(
                    Icons.Filled.Star,
                    contentDescription = null,
                    tint = if (index <= review.rating) Yellow else Color.LightGray,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
        Text(
            text = review.comment,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}


@Composable
fun SectionDivider() {
    HorizontalDivider(
        thickness = 8.dp,
        color = Color.LightGray.copy(alpha = 0.3f)
    )
}


@Composable
fun InfoRow(title: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(title, color = Color.Gray, fontSize = 16.sp)
        Text(value, fontWeight = FontWeight.Bold, fontSize = 16.sp)
    }
}


@Composable
fun ImageHeader(
    accommodation: Accommodation,
    popBackStack: () -> Unit
) {
    Box(modifier = Modifier.fillMaxWidth().height(300.dp)) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(accommodation.imageUrl).crossfade(true).build(),
            contentDescription = accommodation.name,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                .align(Alignment.TopCenter),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = { popBackStack() },
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Color.Black.copy(alpha = 0.3f))
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = White
                )
            }
            Row {
                IconButton(
                    onClick = { /* 공유하기 */ },
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Color.Black.copy(alpha = 0.3f))
                ) {
                    Icon(
                        Icons.Default.Share,
                        contentDescription = "Share",
                        tint = White
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(
                    onClick = { /* 찜하기 */ },
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Color.Black.copy(alpha = 0.3f))
                ) {
                    Icon(
                        Icons.Default.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = White
                    )
                }
            }
        }
    }
}

@Composable
fun BookingBottomBar(price: String) {
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        shadowElevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    "1박당 최저가",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
                Text(
                    text = price,
                    style = MaterialTheme.typography.titleLarge
                )
            }
            Button(
                onClick = { /* 객실 선택 페이지로 이동 */ },
                modifier = Modifier.height(50.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("객실 선택하기", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun AccommodationDetailScreenPreview() {
    val sampleAccommodation = Accommodation(
        id = 1,
        name = "서울 시그니처 호텔",
        rating = 4.9,
        reviewCount = 2108,
        price = "125,000원",
        imageUrl = "https://images.unsplash.com/photo-1566073771259-6a8506099945",
        category = "5성급 호텔",
        address = "서울특별시 강남구 테헤란로 123",
        isSpecialPrice = true,
        facilities = listOf(
            Facility(Icons.Default.Wifi, "무료 Wifi"),
            Facility(Icons.Default.LocalParking, "주차가능"),
            Facility(Icons.Default.Restaurant, "레스토랑"),
            Facility(Icons.Default.Pool, "수영장"),
            Facility(Icons.Default.FitnessCenter, "피트니스"),
            Facility(Icons.Default.BusinessCenter, "비즈니스"),
            Facility(Icons.Default.SmokeFree, "금연"),
            Facility(Icons.Default.AcUnit, "에어컨")
        ),
        checkInTime = "15:00",
        checkOutTime = "11:00",
        notice = "코로나19 방역 수칙을 준수하고 있습니다. 일부 부대시설 이용이 제한될 수 있습니다.",
        usageInfo = "• 모든 객실은 금연입니다.\n• 반려동물 동반 입실은 불가합니다.\n• 미성년자는 보호자 동반 없이 이용할 수 없습니다.",
        reviews = listOf(
            Review("여행가", 5.0, "정말 깨끗하고 좋았어요! 직원분들도 친절하고 위치도 최고입니다.", "2025.09.09"),
            Review("김철수", 4.0, "전반적으로 만족하지만, 조식 메뉴가 조금 아쉬웠습니다.", "2025.09.05")
        )
    )
    MaterialTheme {
        AccommodationDetailScreen(accommodation = sampleAccommodation, popBackStack = {})
    }
}