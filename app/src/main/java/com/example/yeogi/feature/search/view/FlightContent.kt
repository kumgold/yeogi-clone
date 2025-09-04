package com.example.yeogi.feature.search.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.FlightLand
import androidx.compose.material.icons.filled.FlightTakeoff
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FlightContent() {
    var selectedFlightTabIndex by remember { mutableStateOf(0) }
    val flightTabs = listOf("왕복", "편도")

    var departure by remember { mutableStateOf("서울 (SEL)") }
    var arrival by remember { mutableStateOf("도쿄 (NRT)") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // 왕복, 편도 탭
        TabRow(
            selectedTabIndex = selectedFlightTabIndex,
            containerColor = Color.White,
            contentColor = MaterialTheme.colorScheme.primary,
        ) {
            flightTabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedFlightTabIndex == index,
                    onClick = { selectedFlightTabIndex = index },
                    text = { Text(text = title, fontWeight = FontWeight.Bold) }
                )
            }
        }
        Spacer(modifier = Modifier.height(24.dp))

        // 출발지, 도착지, 스위치 버튼 영역
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                // 출발지
                FlightSearchField(
                    icon = Icons.Default.FlightTakeoff,
                    label = "출발",
                    value = departure,
                    onClick = { /* 출발지 선택 */ }
                )
                // 도착지
                FlightSearchField(
                    icon = Icons.Default.FlightLand,
                    label = "도착",
                    value = arrival,
                    onClick = { /* 도착지 선택 */ }
                )
            }

            // 스위치 버튼
            Card(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 16.dp), // 오른쪽 여백
                shape = CircleShape,
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                IconButton(
                    onClick = {
                        // 출발지와 도착지 값을 서로 교환
                        val temp = departure
                        departure = arrival
                        arrival = temp
                    },
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        Icons.Default.SwapVert,
                        contentDescription = "출발/도착 전환",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        // 날짜 선택
        FlightSearchField(
            icon = Icons.Default.CalendarToday,
            label = "가는날 - 오는날",
            value = "9월 10일(화) ~ 9월 15일(일)",
            onClick = { /* 날짜 선택 */ }
        )
        Spacer(modifier = Modifier.height(8.dp))

        // 인원 수 선택
        FlightSearchField(
            icon = Icons.Default.Person,
            label = "탑승객",
            value = "성인 1명",
            onClick = { /* 인원 수 선택 */ }
        )
        Spacer(modifier = Modifier.height(16.dp))

        // 항공권 검색 버튼
        Button(
            onClick = { /* 항공권 검색 실행 */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text("항공권 검색", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
    }
}

/**
 * 항공 탭에서 사용하는 검색 필드 Composable
 */
@Composable
fun FlightSearchField(
    icon: ImageVector,
    label: String,
    value: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .border(1.dp, Color(0xFFE0E0E0), RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = label, tint = Color.Gray)
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(label, fontSize = 12.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(2.dp))
            Text(value, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}