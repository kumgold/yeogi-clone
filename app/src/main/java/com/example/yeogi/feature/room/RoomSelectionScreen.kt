package com.example.yeogi.feature.room

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.yeogi.core.presentation.SharedPaymentViewModel
import com.example.yeogi.core.ui.appbar.DateGuestSelectionTopAppBar
import com.example.yeogi.core.ui.bottomsheet.DateGuestSelectionBottomSheet
import com.example.yeogi.core.util.toKRWString
import com.example.yeogi.feature.room.data.remote.Room
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoomSelectionScreen(
    accommodationId: Int,
    sharedPaymentViewModel: SharedPaymentViewModel,
    viewModel: RoomSelectionViewModel = hiltViewModel(),
    navigateToPayment: (Int) -> Unit,
    popBackStack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val dateGuestSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()

    LaunchedEffect(accommodationId) {
        scope.launch {
            delay(500)
            viewModel.loadRooms()
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
        RoomScreenSkeleton(
            popBackStack = popBackStack
        )
    } else {
        RoomSelectionContent(
            dateRangeString = uiState.dateRangeString,
            rooms = uiState.rooms,
            onDateGuestChangeListener = { viewModel.openDateGuestSheet() },
            navigateToPayment = { roomId ->
                sharedPaymentViewModel.selectedRoom = uiState.rooms.find { it.id == roomId }
                navigateToPayment(roomId)
            },
            popBackStack = popBackStack
        )
    }
}

@Composable
private fun RoomSelectionContent(
    dateRangeString: String,
    rooms: List<Room>,
    onDateGuestChangeListener: () -> Unit,
    navigateToPayment: (Int) -> Unit,
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
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(rooms) { room ->
                RoomItem(
                    room = room,
                    onBookingClick = { navigateToPayment(room.id) }
                )
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
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
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
                Text(
                    text = room.name,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = room.capacity,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    room.features.forEach { feature ->
                        Text(
                            text = "· $feature",
                            style = MaterialTheme.typography.labelMedium,
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
                                buildAnnotatedString {
                                    append(room.originalPrice.toKRWString())
                                    addStyle(
                                        SpanStyle(textDecoration = TextDecoration.LineThrough),
                                        0,
                                        room.originalPrice.toKRWString().length
                                    )
                                },
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                        Text(
                            text = room.price.toKRWString(),
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                    Button(
                        onClick = { onBookingClick(room.id) },
                        colors = ButtonDefaults.buttonColors().copy(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text(
                            text = room.bookingStatus,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            }
        }
    }
}