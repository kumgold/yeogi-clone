package com.example.yeogi.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.yeogi.util.getFormattedMonthDay
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.time.temporal.ChronoUnit
import java.util.Locale

/**
 * 날짜, 인원 설정 바텀 시트
 */
@Composable
fun DateGuestBottomSheetContent(
    initialStartDate: LocalDate,
    initialEndDate: LocalDate,
    initialGuestCount: Int,
    onDismiss: () -> Unit,
    onApply: (LocalDate, LocalDate, Int) -> Unit
) {
    var tempStartDate by remember { mutableStateOf<LocalDate?>(initialStartDate) }
    var tempEndDate by remember { mutableStateOf<LocalDate?>(initialEndDate) }
    var tempGuestCount by remember { mutableIntStateOf(initialGuestCount) }

    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = 4.dp)
        ) {
            Text(
                text = "날짜 및 인원 선택",
                modifier = Modifier.align(Alignment.Center),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            IconButton(
                onClick = onDismiss,
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(Icons.Default.Close, contentDescription = "닫기")
            }
        }

        Column(
            Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            var isDateSelectionExpanded by remember { mutableStateOf(true) }
            var isGuestSelectionExpanded by remember { mutableStateOf(false) }

            DateSelectionView(
                startDate = tempStartDate,
                endDate = tempEndDate,
                isExpanded = isDateSelectionExpanded,
                onExpand = {
                    isDateSelectionExpanded = !isDateSelectionExpanded
                    isGuestSelectionExpanded = false
                },
                onDatesSelected = { start, end ->
                    tempStartDate = start
                    tempEndDate = end
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            GuestSelectionView(
                guestCount = tempGuestCount,
                isExpanded = isGuestSelectionExpanded,
                onExpand = {
                    isGuestSelectionExpanded = !isGuestSelectionExpanded
                    isDateSelectionExpanded = false
                },
                onGuestCountChange = { tempGuestCount = it }
            )
        }

        Surface(
            shadowElevation = 8.dp,
            color = Color.White
        ) {
            Button(
                onClick = {
                    if (tempStartDate != null && tempEndDate != null) {
                        onApply(tempStartDate!!, tempEndDate!!, tempGuestCount)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(50.dp),
                shape = RoundedCornerShape(8.dp),
                enabled = tempStartDate != null && tempEndDate != null
            ) {
                Text("적용", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}


@Composable
fun DateSelectionView(
    startDate: LocalDate?,
    endDate: LocalDate?,
    isExpanded: Boolean,
    onExpand: () -> Unit,
    onDatesSelected: (LocalDate?, LocalDate?) -> Unit
) {
    val nights = if (startDate != null && endDate != null) ChronoUnit.DAYS.between(startDate, endDate) else 0

    val headerText = if (startDate != null && endDate != null) {
        val start = startDate.getFormattedMonthDay()
        val end = endDate.getFormattedMonthDay()
        "$start - $end"
    } else if (startDate != null) {
        "종료 날짜를 선택해주세요."
    } else {
        "시작 날짜를 선택해주세요."
    }

    Column {
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth().clickable { onExpand() }.padding(vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.CalendarToday, contentDescription = "달력 아이콘", tint = Color.Gray)
                Spacer(modifier = Modifier.width(8.dp))
                Text(headerText, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                if (nights > 0) {
                    Text("$nights" + "박", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                }
            }
        }

        if (isExpanded) {
            VerticalCalendarView(
                selectedStartDate = startDate,
                selectedEndDate = endDate,
                onDateClick = { clickedDate ->
                    val (newStart, newEnd) = when {
                        // 시작일만 선택된 상태일 때
                        startDate != null && endDate == null -> {
                            if (clickedDate.isBefore(startDate)) {
                                // 시작일보다 이전 날짜를 클릭 -> 새 시작일로 지정
                                clickedDate to null
                            } else {
                                // 시작일보다 이후 날짜를 클릭 -> 종료일로 지정
                                startDate to clickedDate
                            }
                        }
                        // 시작/종료일이 모두 선택되었거나, 아무것도 선택되지 않았을 때
                        else -> {
                            // -> 선택을 초기화하고 새 시작일로 지정
                            clickedDate to null
                        }
                    }
                    onDatesSelected(newStart, newEnd)
                }
            )
        }
    }
}


@Composable
fun VerticalCalendarView(
    selectedStartDate: LocalDate?,
    selectedEndDate: LocalDate?,
    onDateClick: (LocalDate) -> Unit
) {
    val currentMonth = YearMonth.now()
    val months = remember { List(12) { currentMonth.plusMonths(it.toLong()) } }

    LazyColumn(
        modifier = Modifier.fillMaxWidth().height(400.dp)
    ) {
        items(months) { month ->
            MonthItem(
                month = month,
                selectedStartDate = selectedStartDate,
                selectedEndDate = selectedEndDate,
                onDateClick = onDateClick
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}


@Composable
fun MonthItem(
    month: YearMonth,
    selectedStartDate: LocalDate?,
    selectedEndDate: LocalDate?,
    onDateClick: (LocalDate) -> Unit
) {
    val firstDayOfMonth = month.atDay(1)
    val firstDayOfWeek = firstDayOfMonth.dayOfWeek.value % 7
    val daysInMonth = month.lengthOfMonth()

    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text(text = "${month.year}년 ${month.monthValue}월", fontWeight = FontWeight.Bold, fontSize = 18.sp, modifier = Modifier.padding(bottom = 16.dp, start = 8.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = DayOfWeek.SUNDAY.getDisplayName(TextStyle.SHORT, Locale.KOREAN),
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f),
                fontWeight = FontWeight.Bold,
                color = Color.Red
            )

            for (day in DayOfWeek.entries.subList(0, 6)) {
                Text(
                    text = day.getDisplayName(TextStyle.SHORT, Locale.KOREAN),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f),
                    fontWeight = FontWeight.Bold,
                    color = when (day) {
                        DayOfWeek.SATURDAY -> Color.Blue
                        else -> Color.Black
                    }
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        var dayOfMonth = 1
        repeat(6) {
            if (dayOfMonth > daysInMonth) return@repeat
            Row(modifier = Modifier.fillMaxWidth()) {
                repeat(7) { dayOfWeekIndex ->
                    if (it == 0 && dayOfWeekIndex < firstDayOfWeek || dayOfMonth > daysInMonth) {
                        Box(modifier = Modifier.weight(1f))
                    } else {
                        val date = month.atDay(dayOfMonth)
                        DayCell(
                            date = date,
                            selectedStartDate = selectedStartDate,
                            selectedEndDate = selectedEndDate,
                            onClick = { onDateClick(date) },
                            modifier = Modifier.weight(1f)
                        )
                        dayOfMonth++
                    }
                }
            }
        }
    }
}


@Composable
fun DayCell(
    date: LocalDate,
    selectedStartDate: LocalDate?,
    selectedEndDate: LocalDate?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val today = LocalDate.now()
    val isEnabled = !date.isBefore(today)
    val isSelectedStart = date == selectedStartDate
    val isSelectedEnd = date == selectedEndDate
    val isInRange = selectedStartDate != null && selectedEndDate != null && date.isAfter(selectedStartDate) && date.isBefore(selectedEndDate)

    val backgroundColor = when {
        isSelectedStart || isSelectedEnd -> MaterialTheme.colorScheme.primary
        isInRange -> MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
        else -> Color.Transparent
    }
    val textColor = when {
        isSelectedStart || isSelectedEnd -> Color.White
        !isEnabled -> Color.LightGray
        else -> Color.Black
    }

    Box(
        modifier = modifier
            .aspectRatio(1f)
            .clickable(enabled = isEnabled, onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier.fillMaxSize().background(backgroundColor, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "${date.dayOfMonth}", color = textColor, fontSize = 14.sp)
        }
    }
}


@Composable
fun GuestSelectionView(
    guestCount: Int,
    isExpanded: Boolean,
    onExpand: () -> Unit,
    onGuestCountChange: (Int) -> Unit
) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onExpand() }
                .padding(vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.Person, contentDescription = "인원 아이콘", tint = Color.Gray)
            Spacer(modifier = Modifier.width(8.dp))
            Text("인원", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
            Text("$guestCount" + "명", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
        }

        if (isExpanded) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("성인", fontSize = 16.sp)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = { if (guestCount > 1) onGuestCountChange(guestCount - 1) },
                        colors = IconButtonDefaults.iconButtonColors(contentColor = Color.Gray)
                    ) {
                        Icon(Icons.Default.Remove, "감소")
                    }
                    Text(
                        text = guestCount.toString(),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.width(40.dp),
                        textAlign = TextAlign.Center
                    )
                    IconButton(
                        onClick = { onGuestCountChange(guestCount + 1) },
                        colors = IconButtonDefaults.iconButtonColors(contentColor = Color.Gray)
                    ) {
                        Icon(Icons.Default.Add, "증가")
                    }
                }
            }
        }
    }
}