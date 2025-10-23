package com.example.yeogi.feature.searchdetail.flight

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// 임시 데이터 클래스
data class FlightTicket(
    val airline: String,
    val departureTime: String,
    val departureAirport: String,
    val arrivalTime: String,
    val arrivalAirport: String,
    val duration: String,
    val price: String,
    val type: String // 예: "특가"
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlightSearchDetailScreen(
    departure: String,
    arrival: String,
    onBackClick: () -> Unit
) {
    val dummyTickets = listOf(
        FlightTicket("대한항공", "08:00", "SEL", "10:25", "NRT", "2시간 25분", "250,000원", "일반석"),
        FlightTicket("아시아나", "09:10", "SEL", "11:40", "NRT", "2시간 30분", "235,000원", "특가"),
        FlightTicket("제주항공", "10:05", "SEL", "12:35", "NRT", "2시간 30분", "180,000원", "특가"),
        FlightTicket("티웨이항공", "11:20", "SEL", "13:45", "NRT", "2시간 25분", "175,500원", "일반석")
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = departure, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        Icon(
                            imageVector = Icons.Default.SwapHoriz,
                            contentDescription = "방향 전환",
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )
                        Text(text = arrival, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "뒤로가기")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFF5F5F5))
        ) {
            SearchConditionChangeButton(
                date = "9월 10일(화) ~ 9월 15일(일)",
                passengers = "성인 1명",
                onClick = { /* 검색 조건 변경 로직 */ }
            )

            // 항공권 목록
            LazyColumn(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item { Spacer(modifier = Modifier.height(4.dp)) }

                items(dummyTickets) { ticket ->
                    FlightTicketItem(ticket = ticket)
                }

                item { Spacer(modifier = Modifier.height(16.dp)) }
            }
        }
    }
}

@Composable
fun SearchConditionChangeButton(date: String, passengers: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        TextButton(onClick = onClick) {
            Text(
                text = "$date, $passengers",
                color = Color.White,
                fontSize = 16.sp
            )
        }
    }
}


@Composable
fun FlightTicketItem(ticket: FlightTicket) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = ticket.airline, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Spacer(modifier = Modifier.width(8.dp))
                if (ticket.type == "특가") {
                    Box(
                        modifier = Modifier
                            .background(
                                color = MaterialTheme.colorScheme.secondary,
                                shape = RoundedCornerShape(4.dp)
                            )
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = ticket.type,
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(text = ticket.departureTime, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Text(text = ticket.departureAirport, color = Color.Gray)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        Icons.Default.SwapHoriz,
                        contentDescription = "비행 시간",
                        tint = Color.Gray,
                        modifier = Modifier.size(24.dp)
                    )
                    Text(text = ticket.duration, fontSize = 12.sp, color = Color.Gray)
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(text = ticket.arrivalTime, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Text(text = ticket.arrivalAirport, color = Color.Gray)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.End
            ) {
                Text(text = "편도 총액", fontSize = 14.sp)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = ticket.price,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}