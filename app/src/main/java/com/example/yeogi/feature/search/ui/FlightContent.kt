package com.example.yeogi.feature.search.ui

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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun FlightContent() {
    var selectedFlightTabIndex by remember { mutableIntStateOf(0) }
    val flightTabs = listOf("왕복", "편도")

    var departure by remember { mutableStateOf("서울 (SEL)") }
    var arrival by remember { mutableStateOf("도쿄 (NRT)") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        TabRow(
            selectedTabIndex = selectedFlightTabIndex,
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.primary,
        ) {
            flightTabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedFlightTabIndex == index,
                    onClick = { selectedFlightTabIndex = index },
                    text = {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                )
            }
        }
        Spacer(modifier = Modifier.height(24.dp))

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
                    .padding(end = 16.dp),
                shape = CircleShape,
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {
                IconButton(
                    onClick = {
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
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Text(
                text = "항공권 검색",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )
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
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onBackground,
                shape = RoundedCornerShape(8.dp)
            )
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}