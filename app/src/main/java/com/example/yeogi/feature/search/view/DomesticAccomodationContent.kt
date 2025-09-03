package com.example.yeogi.feature.search.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.yeogi.dummy.dummyDomesticRecentSearch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun DomesticAccommodationContent() {
    // 국내숙소 탭에 필요한 데이터 (더미)
    val today = LocalDate.now()
    val tomorrow = today.plusDays(1)
    val formatter = DateTimeFormatter.ofPattern("MM.dd")
    val todayDayOfWeek = today.toString()
    val tomorrowDayOfWeek = tomorrow.toString()
    val dateRangeText = "${today.format(formatter)}($todayDayOfWeek) - ${tomorrow.format(formatter)}($tomorrowDayOfWeek)"
    val guestText = "성인 2"
    val recentSearches = remember { dummyDomesticRecentSearch.toMutableStateList() }
    val searchRankings = listOf(
        "제주도", "강릉", "부산", "여수", "경주", "속초", "서울", "가평", "해운대", "전주"
    )

    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            SearchInputField(onClick = { /* 검색 화면으로 이동 */ })
            Spacer(modifier = Modifier.height(12.dp))
            DateAndGuestPicker(dateRangeText, guestText)
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = { /* 검색 결과 표시 */ },
                    modifier = Modifier.weight(1f).height(50.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text("숙소 검색", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.width(8.dp))
                TextButton(
                    onClick = { /* 지도 화면으로 이동 */ },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.height(50.dp),
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Map, contentDescription = "지도 아이콘", tint = Color.Gray)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("지도로 찾기", color = Color.Gray, fontWeight = FontWeight.Bold)
                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (recentSearches.isNotEmpty()) {
            RecentHistorySection(
                items = recentSearches,
                onClearAll = { recentSearches.clear() },
                onDeleteItem = { item -> recentSearches.remove(item) }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        SearchRankingSection(rankings = searchRankings)
    }
}

@Composable
fun SearchInputField(onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(Icons.Default.Search, contentDescription = "검색 아이콘", tint = Color.Gray)
        Spacer(modifier = Modifier.width(8.dp))
        Text("지역, 숙소 검색", color = Color.Gray, fontSize = 16.sp)
    }
}

@Composable
fun DateAndGuestPicker(dateRangeText: String, guestText: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier
                .weight(2f)
                .fillMaxHeight()
                .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
                .clip(RoundedCornerShape(8.dp))
                .clickable { /* 날짜 선택 다이얼로그 */ }
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.CalendarToday, contentDescription = "달력 아이콘", modifier = Modifier.size(20.dp), tint = Color.Gray)
            Spacer(modifier = Modifier.width(8.dp))
            Text(dateRangeText, fontSize = 14.sp, fontWeight = FontWeight.Bold, maxLines = 1)
        }
        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
                .clip(RoundedCornerShape(8.dp))
                .clickable { /* 인원 선택 다이얼로그 */ }
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.Person, contentDescription = "인원 아이콘", modifier = Modifier.size(20.dp), tint = Color.Gray)
            Spacer(modifier = Modifier.width(8.dp))
            Text(guestText, fontSize = 14.sp, fontWeight = FontWeight.Bold, maxLines = 1)
        }
    }
}