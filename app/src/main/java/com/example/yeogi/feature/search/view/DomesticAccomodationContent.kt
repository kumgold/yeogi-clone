package com.example.yeogi.feature.search.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.yeogi.dummy.RecentSearch
import com.example.yeogi.dummy.dummyDomesticRecentSearch
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.time.temporal.ChronoUnit
import java.util.Locale

val monthDayFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("MM.dd")
val dayOfWeekFormatter: (LocalDate) -> String = { date -> date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.KOREAN) }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DomesticAccommodationContent() {
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

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()
    var isSheetOpen by remember { mutableStateOf(false) }

    val dateGuestSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var isDateGuestSheetOpen by remember { mutableStateOf(false) }

    var startDate by remember { mutableStateOf(LocalDate.now()) }
    var endDate by remember { mutableStateOf(LocalDate.now().plusDays(1)) }
    var guestCount by remember { mutableStateOf(2) }

    if (isSheetOpen) {
        ModalBottomSheet(
            onDismissRequest = { isSheetOpen = false },
            sheetState = sheetState,
            modifier = Modifier.fillMaxSize(),
            containerColor = Color.White
        ) {
            SearchBottomSheetContent(
                recentSearches = recentSearches,
                searchRankings = searchRankings,
                onClearAll = { recentSearches.clear() },
                onDeleteItem = { item -> recentSearches.remove(item) },
                onDismiss = {
                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            isSheetOpen = false
                        }
                    }
                }
            )
        }
    }

    if (isDateGuestSheetOpen) {
        ModalBottomSheet(
            onDismissRequest = { isDateGuestSheetOpen = false },
            sheetState = dateGuestSheetState,
            modifier = Modifier.fillMaxSize(),
            containerColor = Color.White
        ) {
            DateGuestBottomSheetContent(
                initialStartDate = startDate,
                initialEndDate = endDate,
                initialGuestCount = guestCount,
                onDismiss = {
                    scope.launch { dateGuestSheetState.hide() }.invokeOnCompletion {
                        if (!dateGuestSheetState.isVisible) isDateGuestSheetOpen = false
                    }
                },
                onApply = { newStart, newEnd, newGuests ->
                    startDate = newStart
                    endDate = newEnd
                    guestCount = newGuests
                    scope.launch { dateGuestSheetState.hide() }.invokeOnCompletion {
                        if (!dateGuestSheetState.isVisible) isDateGuestSheetOpen = false
                    }
                }
            )
        }
    }

    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            SearchInputField(onClick = { isSheetOpen = true })
            Spacer(modifier = Modifier.height(12.dp))
            DateAndGuestPicker(
                dateRangeText = dateRangeText,
                guestText = guestText,
                onClick = { isDateGuestSheetOpen = true }
            )
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

/**
 * 숙소 검색 바텀 시트
 */
@Composable
fun SearchBottomSheetContent(
    recentSearches: SnapshotStateList<RecentSearch>,
    searchRankings: List<String>,
    onClearAll: () -> Unit,
    onDeleteItem: (RecentSearch) -> Unit,
    onDismiss: () -> Unit
) {
    var searchText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text(
                text = "국내 숙소",
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

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = searchText,
            onValueChange = { searchText = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("지역, 숙소 검색", color = Color.Gray) },
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = "검색 아이콘", tint = Color.Gray)
            },
            shape = RoundedCornerShape(8.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                unfocusedIndicatorColor = Color.LightGray
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(24.dp))

        if (recentSearches.isNotEmpty()) {
            RecentHistorySection(
                items = recentSearches,
                onClearAll = onClearAll,
                onDeleteItem = onDeleteItem
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
fun DateAndGuestPicker(
    dateRangeText: String,
    guestText: String,
    onClick: () -> Unit
) {
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
                .clickable { onClick() }
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
                .clickable { onClick() }
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.Person, contentDescription = "인원 아이콘", modifier = Modifier.size(20.dp), tint = Color.Gray)
            Spacer(modifier = Modifier.width(8.dp))
            Text(guestText, fontSize = 14.sp, fontWeight = FontWeight.Bold, maxLines = 1)
        }
    }
}

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
    var tempGuestCount by remember { mutableStateOf(initialGuestCount) }

    Column(modifier = Modifier.fillMaxSize()) {
        // 헤더
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
                .verticalScroll(rememberScrollState()) // 전체 스크롤을 위해 추가
        ) {
            DateSelectionView(
                startDate = tempStartDate,
                endDate = tempEndDate,
                onDatesSelected = { start, end ->
                    tempStartDate = start
                    tempEndDate = end
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            GuestSelectionView(
                guestCount = tempGuestCount,
                onGuestCountChange = { tempGuestCount = it }
            )
        }

        Surface(shadowElevation = 8.dp) {
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
    onDatesSelected: (LocalDate?, LocalDate?) -> Unit
) {
    var isExpanded by remember { mutableStateOf(true) }
    val nights = if (startDate != null && endDate != null) ChronoUnit.DAYS.between(startDate, endDate) else 0

    val headerText = if (startDate != null && endDate != null) {
        val start = "${startDate.format(monthDayFormatter)}(${dayOfWeekFormatter(startDate)})"
        val end = "${endDate.format(monthDayFormatter)}(${dayOfWeekFormatter(endDate)})"
        "$start - $end"
    } else if (startDate != null) {
        "종료 날짜를 선택해주세요."
    } else {
        "시작 날짜를 선택해주세요."
    }

    Column {
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth().clickable { isExpanded = !isExpanded }.padding(vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.CalendarToday, contentDescription = "달력 아이콘", tint = Color.Gray)
                Spacer(modifier = Modifier.width(8.dp))
                Text(headerText, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                if (nights > 0) {
                    Text("($nights" + "박)", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
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
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedButton(onClick = { onDatesSelected(null, null) }) { Text("초기화") }
                Button(onClick = { isExpanded = false }) { Text("선택 완료") }
            }
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
            DayOfWeek.entries.forEach {
                Text(
                    text = it.getDisplayName(TextStyle.SHORT, Locale.KOREAN),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f),
                    fontWeight = FontWeight.Bold,
                    color = when (it) {
                        DayOfWeek.SUNDAY -> Color.Red
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
    onGuestCountChange: (Int) -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { isExpanded = !isExpanded }
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