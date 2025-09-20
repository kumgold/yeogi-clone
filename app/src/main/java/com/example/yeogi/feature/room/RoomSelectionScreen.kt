package com.example.yeogi.feature.room

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.yeogi.core.model.Accommodation
import com.example.yeogi.core.util.skeletonBackground
import com.example.yeogi.feature.room.data.remote.Room
import com.example.yeogi.shared.ui.DateGuestSelectionBottomSheet
import com.example.yeogi.shared.ui.DateGuestSelectionTopAppBar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoomSelectionScreen(
    accommodationId: Int,
    viewModel: RoomSelectionViewModel = viewModel(),
    navigateToPayment: () -> Unit,
    popBackStack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val dateGuestSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()

    LaunchedEffect(accommodationId) {
        scope.launch {
            delay(500)
            viewModel.loadRooms(accommodationId)
        }
    }

    if (uiState.isDateGuestSheetOpen) {
        DateGuestSelectionBottomSheet(
            initialStartDate = uiState.startDate,
            initialEndDate = uiState.endDate,
            initialGuestCount = uiState.guestCount,
            sheetState = dateGuestSheetState,
            onDismiss = {
                scope.launch { dateGuestSheetState.hide() }.invokeOnCompletion {
                    if (!dateGuestSheetState.isVisible) viewModel.closeDateGuestSheet()
                }
            },
            onApply = { newStart, newEnd, newGuests ->
                viewModel.setDateAndGuest(
                    startDate = newStart,
                    endDate = newEnd,
                    guest = newGuests
                )
            },
        )
    }

    if (uiState.rooms.isEmpty()) {
        RoomItemSkeleton(
            popBackStack = popBackStack
        )
    } else {
        RoomSelectionContent(
            dateRangeString = uiState.dateRangeString,
            rooms = uiState.rooms,
            onDateGuestChangeListener = { viewModel.openDateGuestSheet() },
            navigateToPayment = navigateToPayment,
            popBackStack = popBackStack
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RoomItemSkeleton(
    popBackStack: () -> Unit
) {
    Column {
        TopAppBar(
            title = {},
            navigationIcon = {
                IconButton(
                    modifier = Modifier,
                    onClick = { popBackStack() },
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = White
                    )
                ) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.Black
                    )
                }
            }
        )
        Card(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            colors = CardDefaults.cardColors(containerColor = White)
        ) {
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
                        .skeletonBackground(),
                )
                Column(Modifier.padding(16.dp)) {
                    Box(
                        modifier = Modifier
                            .height(20.dp)
                            .width(50.dp)
                            .skeletonBackground()
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Box(
                        modifier = Modifier
                            .height(20.dp)
                            .width(50.dp)
                            .skeletonBackground()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        repeat(5) {
                            Box(
                                modifier = Modifier
                                    .height(12.dp)
                                    .width(30.dp)
                                    .skeletonBackground()
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(horizontalAlignment = Alignment.End) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .height(12.dp)
                                        .width(30.dp)
                                        .skeletonBackground()
                                )
                                Spacer(Modifier.width(4.dp))
                                Box(
                                    modifier = Modifier
                                        .height(16.dp)
                                        .width(40.dp)
                                        .skeletonBackground()
                                )
                            }
                        }
                        Box(
                            modifier = Modifier
                                .height(24.dp)
                                .width(80.dp)
                                .skeletonBackground()
                        )
                    }
                }
            }
        }
    }
}


@Composable
private fun RoomSelectionContent(
    dateRangeString: String,
    rooms: List<Room>,
    onDateGuestChangeListener: () -> Unit,
    navigateToPayment: () -> Unit,
    popBackStack: () -> Unit
) {
    Scaffold(
        topBar = {
            DateGuestSelectionTopAppBar(
                dateRange = dateRangeString,
                onDateGuestChangeListener = {
                    onDateGuestChangeListener()
                },
                popBackStack = popBackStack
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(rooms) { room ->
                RoomItem(room = room, onBookingClick = { navigateToPayment() })
            }
        }
    }
}


@Composable
private fun RoomItem(
    room: Room,
    onBookingClick: (roomId: Int) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = White)
    ) {
        Column {
            AsyncImage(
                model = room.imageUrl,
                contentDescription = room.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)),
                contentScale = ContentScale.Crop,
            )
            Column(Modifier.padding(16.dp)) {
                Text(room.name, style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(4.dp))
                Text(room.capacity, style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
                Spacer(modifier = Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    room.features.forEach { feature ->
                        Text(
                            text = "· $feature",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(horizontalAlignment = Alignment.End) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = room.discountRate,
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    color = MaterialTheme.colorScheme.error
                                ),
                            )
                            Spacer(Modifier.width(4.dp))
                            Text(
                                text = room.originalPrice,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                        Text(room.price, style = MaterialTheme.typography.bodyLarge)
                    }
                    Button(onClick = { onBookingClick(room.id) }) {
                        Text(room.bookingStatus)
                    }
                }
            }
        }
    }
}