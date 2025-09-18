package com.example.yeogi.shared.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.yeogi.core.model.RecentSearch
import com.example.yeogi.util.getFormattedMonthDay

@Composable
fun RecentHistorySection(
    items: MutableList<RecentSearch>,
    onClearAll: () -> Unit,
    onDeleteItem: (RecentSearch) -> Unit,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .background(Color.White)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("최근 검색 기록", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text(
                "전체삭제",
                color = Color.Gray,
                fontSize = 14.sp,
                modifier = Modifier.clickable { onClearAll() }
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        items.forEach { item ->
            RecentHistoryItem(
                item = item,
                onDelete = { onDeleteItem(item) },
                onClick = onClick
            )
        }
    }
}

@Composable
private fun RecentHistoryItem(
    item: RecentSearch,
    onDelete: () -> Unit,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            Icons.Default.History,
            contentDescription = "검색 기록 아이콘",
            tint = Color.Gray,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(item.keyword, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text(
                "${item.startDate.getFormattedMonthDay()} - ${item.endDate.getFormattedMonthDay()}",
                color = Color.Gray,
                fontSize = 12.sp
            )
        }
        IconButton(onClick = onDelete) {
            Icon(Icons.Default.Close, contentDescription = "삭제", tint = Color.LightGray)
        }
    }
}