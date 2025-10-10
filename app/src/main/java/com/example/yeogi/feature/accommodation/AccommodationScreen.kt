package com.example.yeogi.feature.accommodation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.yeogi.R
import com.example.yeogi.core.model.Accommodation
import com.example.yeogi.core.model.Facility
import com.example.yeogi.core.model.Review
import com.example.yeogi.core.util.toKRWString
import com.example.yeogi.core.ui.bottomsheet.DateGuestSelectionBottomSheet
import com.example.yeogi.ui.theme.Yellow
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccommodationScreen(
    accommodationId: Int,
    viewModel: AccommodationViewModel = hiltViewModel(),
    navigateToRoomSelection: (Int) -> Unit,
    popBackStack: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val dateGuestSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()

    LaunchedEffect(accommodationId) {
        scope.launch {
            delay(500)
            viewModel.getAccommodation(accommodationId)
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

    if (uiState.accommodation == null) {
        AccommodationScreenSkeleton(
            popBackStack = popBackStack
        )
    } else {
        AccommodationScreenContent(
            accommodation = uiState.accommodation!!,
            dateRangeString = uiState.dateRangeString,
            onDateGuestChangeListener = {
                viewModel.openDateGuestSheet()
            },
            navigateToRoomSelection = { id ->
                navigateToRoomSelection(id)
            },
            popBackStack = popBackStack
        )
    }
}

@Composable
private fun AccommodationScreenContent(
    accommodation: Accommodation,
    dateRangeString: String,
    onDateGuestChangeListener: () -> Unit,
    navigateToRoomSelection: (Int) -> Unit,
    popBackStack: () -> Unit
) {
    val listState = rememberLazyListState()
    val isScrolled = remember { derivedStateOf { listState.firstVisibleItemIndex > 0 } }

    Scaffold(
        modifier = Modifier.navigationBarsPadding(),
        bottomBar = {
            BookingBottomBar(
                price = accommodation.price.toKRWString(),
                navigateToRoomSelection = {
                    navigateToRoomSelection(accommodation.id)
                }
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()

                .padding(bottom = paddingValues.calculateBottomPadding()),
            state = listState
        ) {
            item {
                ImageHeader(
                    accommodation = accommodation
                )
            }
            item { MainInfoSection(accommodation) }
            item { SectionDivider() }
            item { FacilityInfoSection(accommodation.facilities) }
            item { SectionDivider() }
            item { LocationSection() }
            item { SectionDivider() }
            item {
                UsageInfoSection(
                    checkIn = accommodation.checkInTime,
                    checkOut = accommodation.checkOutTime,
                    usageInfo = accommodation.usageInfo
                )
            }
            item { SectionDivider() }
            item { NoticeSection(accommodation.notice) }
            item { SectionDivider() }
            item { ReviewSection(accommodation.rating, accommodation.reviews) }
        }
        AccommodationAppBar(
            dateRange = dateRangeString,
            isScrolled = isScrolled,
            onDateGuestChangeListener = {
                onDateGuestChangeListener()
            },
            popBackStack = popBackStack,
        )
    }
}

@Composable
fun ImageHeader(
    accommodation: Accommodation
) {
    Box(modifier = Modifier.fillMaxWidth().height(300.dp)) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(accommodation.imageUrl).crossfade(true).build(),
            contentDescription = accommodation.name,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccommodationAppBar(
    dateRange: String,
    isScrolled: State<Boolean>,
    onDateGuestChangeListener: () -> Unit,
    popBackStack: () -> Unit
) {
    val appBarColor by animateColorAsState(
        targetValue = if (isScrolled.value) MaterialTheme.colorScheme.background else Color.Transparent,
        label = "appBarColorAnimation"
    )

    TopAppBar(
        title = {
            AnimatedVisibility(visible = isScrolled.value) {
                Box(
                    modifier = Modifier.clickable {
                        onDateGuestChangeListener()
                    }
                ) {
                    Text(
                        text = dateRange,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        },
        navigationIcon = {
            IconButton(
                onClick = { popBackStack() },
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = if (isScrolled.value) Color.Transparent else MaterialTheme.colorScheme.background
                ),
                modifier = if (isScrolled.value) Modifier else Modifier.clip(CircleShape)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        },
        actions = {
            IconButton(
                onClick = { /* 공유하기 */ },
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = if (isScrolled.value) Color.Transparent else MaterialTheme.colorScheme.background
                ),
                modifier = if (isScrolled.value) Modifier else Modifier.clip(CircleShape)
            ) {
                Icon(
                    Icons.Default.Share,
                    contentDescription = "Share",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
            IconButton(
                onClick = { /* 찜하기 */ },
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = if (isScrolled.value) Color.Transparent else MaterialTheme.colorScheme.background
                ),
                modifier = if (isScrolled.value) Modifier else Modifier.clip(CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = "Favorite",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = appBarColor
        )
    )
}

@Composable
fun MainInfoSection(accommodation: Accommodation) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = accommodation.name,
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = accommodation.category,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(12.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                modifier = Modifier.size(18.dp),
                imageVector = Icons.Outlined.LocationOn,
                contentDescription = "Address",
                tint = MaterialTheme.colorScheme.onBackground,
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = accommodation.address,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier
                    .background(
                        shape = RoundedCornerShape(50.dp),
                        color = MaterialTheme.colorScheme.tertiaryContainer
                    )
                    .padding(horizontal = 4.dp, vertical = 2.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(18.dp),
                    imageVector = Icons.Default.StarRate,
                    contentDescription = "Rating",
                    tint = Yellow,
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = accommodation.rating.toString(),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "${accommodation.reviewCount}개 평가",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground
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
            imageVector = facility.icon,
            modifier = Modifier.size(20.dp),
            contentDescription = facility.name,
            tint = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = facility.name,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
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
                .background(
                    color = Color.LightGray,
                    shape = RoundedCornerShape(12.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "지도 표시 영역",
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

@Composable
fun UsageInfoSection(checkIn: String, checkOut: String, usageInfo: String) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = stringResource(R.string.introduce_accommodation),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
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
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
fun NoticeSection(notice: String) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            stringResource(R.string.notice),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = notice,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
fun ReviewSection(rating: Double, reviews: List<Review>) {
    Column(modifier = Modifier.padding(16.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                imageVector = Icons.Default.StarRate,
                contentDescription = "Rating",
                tint = Yellow,
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "$rating",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                "${reviews.size}개 평가",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        HorizontalDivider(
            modifier = Modifier.padding(vertical = 8.dp)
        )

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
            Text(
                text = review.userName,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = review.date,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 12.sp
            )
        }
        Row(
            modifier = Modifier.padding(vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            (1..5).forEach { index ->
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = null,
                    tint = if (index <= review.rating) Yellow else Color.LightGray,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
        Text(
            text = review.comment,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground
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
        Text(
            text = title,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
fun BookingBottomBar(
    price: String,
    navigateToRoomSelection: () -> Unit
) {
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
                    text = "1박당 최저가",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = price,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            Button(
                onClick = { navigateToRoomSelection() },
                modifier = Modifier.height(50.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "객실 선택하기",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AccommodationDetailScreenPreview() {
    MaterialTheme {
        AccommodationScreen(
            accommodationId = 1,
            navigateToRoomSelection = {},
            popBackStack = {}
        )
    }
}