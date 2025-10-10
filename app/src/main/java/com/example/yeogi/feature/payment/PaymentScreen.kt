package com.example.yeogi.feature.payment

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.yeogi.R
import com.example.yeogi.core.model.Accommodation
import com.example.yeogi.core.presentation.SharedPaymentViewModel
import com.example.yeogi.core.util.getFormattedMonthDay
import com.example.yeogi.core.util.toKRWString
import com.example.yeogi.feature.room.data.remote.Room
import kotlinx.coroutines.delay
import java.time.LocalDate


@Composable
fun PaymentScreen(
    sharedPaymentViewModel: SharedPaymentViewModel,
    viewModel: PaymentViewModel = hiltViewModel(),
    accommodationId: Int,
    roomId: Int,
    popBackStack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(null) {
        delay(500)
        viewModel.setUiState(
            accommodation = sharedPaymentViewModel.accommodation,
            room = sharedPaymentViewModel.selectedRoom
        )
    }

    if (uiState.accommodation == null && uiState.room == null) {
        PaymentScreenSkeleton()
    } else {
        PaymentContent(
            accommodation = uiState.accommodation!!,
            room = uiState.room!!,
            startDate = uiState.startDate,
            endDate = uiState.endDate,
            agreed = uiState.agreed,
            popBackStack = popBackStack
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PaymentContent(
    accommodation: Accommodation,
    room: Room,
    startDate: LocalDate,
    endDate: LocalDate,
    agreed: Boolean,
    popBackStack: () -> Unit
) {
    var isOpenPaymentPopup by remember { mutableStateOf(false) }
    var bookerName by remember { mutableStateOf("") }
    var bookerPhone by remember { mutableStateOf("") }
    var paymentMethod by remember { mutableStateOf("신용카드") }
    var allAgreed by remember { mutableStateOf(agreed) }

    if (isOpenPaymentPopup) {
        PaymentConfirmationPopup(
            accommodationName = accommodation.name,
            roomName = room.name,
            startDate = startDate,
            endDate = endDate,
            onDismissRequest = { isOpenPaymentPopup = false },
            onConfirmClick = {
                isOpenPaymentPopup = false
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.reservation)) },
                navigationIcon = {
                    IconButton(onClick = { popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
                ReservationInfo(
                    accommodationName = accommodation.name,
                    roomName = room.name,
                    startDate = startDate,
                    endDate = endDate
                )
                SectionDivider()
            }

            item {
                SectionTitle(title = stringResource(R.string.reservation_user_info))
                BookerInfoSection(
                    name = bookerName,
                    onNameChange = { bookerName = it },
                    phone = bookerPhone,
                    onPhoneChange = { bookerPhone = it }
                )
                SectionDivider()
            }

            item {
                SectionTitle(title = "할인 및 결제 정보")
                PriceSummarySection(
                    price = room.price
                )
                SectionDivider()
            }

            item {
                SectionTitle(title = stringResource(R.string.mean_of_payment))
                PaymentMethodSection(
                    selectedMethod = paymentMethod,
                    onMethodSelected = { paymentMethod = it }
                )
                SectionDivider()
            }

            item {
                TermsAgreementSection(
                    isChecked = allAgreed,
                    onCheckedChange = { allAgreed = it }
                )
                Spacer(modifier = Modifier.height(24.dp))
            }

            item {
                PaymentBottomBar(
                    price = room.price,
                    isButtonEnabled = bookerName.isNotBlank() && bookerPhone.isNotBlank() && allAgreed,
                    onPaymentClick = {
                        isOpenPaymentPopup = true
                    }
                )
            }
        }
    }
}

@Composable
fun PaymentConfirmationPopup(
    accommodationName: String,
    roomName: String,
    startDate: LocalDate,
    endDate: LocalDate,
    onDismissRequest: () -> Unit,
    onConfirmClick: () -> Unit
) {
    var agreedToCancellation by remember { mutableStateOf(false) }

    AlertDialog(
        containerColor = MaterialTheme.colorScheme.background,
        onDismissRequest = onDismissRequest,
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "예약 내역 확인",
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.height(16.dp))
                PopupInfoRow(
                    title = stringResource(R.string.accommodation),
                    value = accommodationName
                )
                PopupInfoRow(
                    title = stringResource(R.string.room),
                    value = roomName
                )
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                PopupInfoRow(
                    title = stringResource(R.string.check_in),
                    value = "${startDate.getFormattedMonthDay()} 15:00"
                )
                PopupInfoRow(
                    title = stringResource(R.string.check_out),
                    value = "${endDate.getFormattedMonthDay()} 11:00"
                )
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                Text(
                    text = "예약 내용을 반드시 확인해주세요.\n취소 및 변경이 어려울 수 있습니다.",
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.error,
                    lineHeight = 18.sp
                )

                Row(
                    modifier = Modifier.clickable { agreedToCancellation = !agreedToCancellation },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = agreedToCancellation,
                        onCheckedChange = { agreedToCancellation = it }
                    )
                    Text(text = "취소 규정 동의 (필수)", fontSize = 14.sp)
                }
            }
        },
        confirmButton = {
            Button(
                onClick = onConfirmClick,
                enabled = agreedToCancellation
            ) {
                Text(stringResource(R.string.payment))
            }
        },
        dismissButton = {
            Button(
                onClick = onDismissRequest,
                colors = ButtonDefaults.outlinedButtonColors()
            ) {
                Text(stringResource(R.string.cancel))
            }
        }
    )
}

@Composable
private fun PopupInfoRow(title: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            modifier = Modifier.weight(0.3f),
            color = Color.Gray,
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = value,
            modifier = Modifier.weight(0.7f),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        modifier = Modifier.padding(vertical = 12.dp, horizontal = 16.dp),
        text = title,
        style = MaterialTheme.typography.titleLarge,
    )
}

@Composable
fun ReservationInfo(
    accommodationName: String?,
    roomName: String?,
    startDate: LocalDate,
    endDate: LocalDate
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Text("$accommodationName", fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(4.dp))
        Text("$roomName", color = Color.Gray)
        HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))
        InfoRow(
            title = stringResource(R.string.check_in),
            value = startDate.getFormattedMonthDay()
        )
        Spacer(modifier = Modifier.height(8.dp))
        InfoRow(
            title = stringResource(R.string.check_out),
            value = endDate.getFormattedMonthDay()
        )
    }
}

@Composable
fun BookerInfoSection(
    name: String,
    onNameChange: (String) -> Unit,
    phone: String,
    onPhoneChange: (String) -> Unit
) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = onNameChange,
            label = { Text(stringResource(R.string.name)) },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = phone,
            onValueChange = onPhoneChange,
            label = { Text(stringResource(R.string.phone_number)) },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun PaymentMethodSection(selectedMethod: String, onMethodSelected: (String) -> Unit) {
    val methods = listOf("신용카드", "네이버페이", "카카오페이", "토스페이")
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        methods.forEach { method ->
            val isSelected = method == selectedMethod
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp)
                    .border(
                        width = 1.dp,
                        color = if (isSelected) MaterialTheme.colorScheme.primary else Color.LightGray,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .background(
                        color = if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.1f) else Color.Transparent,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clickable { onMethodSelected(method) }
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = method,
                    color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Black,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
fun PriceSummarySection(
    price: Int
) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val fee = price / 10

        InfoRow(title = "객실 요금", value = price.toKRWString())
        InfoRow(title = "수수료", value = fee.toKRWString())
        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("총 결제 금액", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text(
                (price + fee).toKRWString(),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun TermsAgreementSection(isChecked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clickable { onCheckedChange(!isChecked) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(checked = isChecked, onCheckedChange = onCheckedChange)
        Text("전체 동의 (필수)", modifier = Modifier.padding(start = 8.dp))
    }
}

@Composable
fun InfoRow(title: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(title, color = Color.Gray)
        Text(value, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
fun SectionDivider() {
    HorizontalDivider(
        modifier = Modifier.padding(vertical = 24.dp),
        thickness = 8.dp,
        color = Color.LightGray.copy(alpha = 0.3f)
    )
}

@Composable
fun PaymentBottomBar(
    price: Int,
    isButtonEnabled: Boolean,
    onPaymentClick: () -> Unit
) {
    Surface(
        modifier = Modifier.padding(vertical = 16.dp),
        shadowElevation = 4.dp
    ) {
        Box(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Button(
                onClick = onPaymentClick,
                enabled = isButtonEnabled,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("${price.toKRWString()} 결제하기", fontSize = 16.sp)
            }
        }
    }
}
