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
import androidx.compose.material3.Button
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.yeogi.feature.payment.data.PaymentDetails


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentScreen(
    viewModel: PaymentViewModel = viewModel(),
    accommodationId: Int,
    roomId: Int,
    popBackStack: () -> Unit
) {
    var bookerName by remember { mutableStateOf("") }
    var bookerPhone by remember { mutableStateOf("") }
    var paymentMethod by remember { mutableStateOf("신용/체크카드") }
    var allAgreed by remember { mutableStateOf(false) }

    val paymentDetails = remember { PaymentDetails() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("결제하기") },
                navigationIcon = {
                    IconButton(onClick = { popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = {
            PaymentBottomBar(
                price = "${paymentDetails.totalPrice}원",
                isButtonEnabled = bookerName.isNotBlank() && bookerPhone.isNotBlank() && allAgreed,
                onPaymentClick = {
                    // 결제 로직 실행
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
                SectionTitle(title = "예약 정보")
                ReservationInfo(details = paymentDetails)
                SectionDivider()
            }

            item {
                SectionTitle(title = "예약자 정보")
                BookerInfoSection(
                    name = bookerName,
                    onNameChange = { bookerName = it },
                    phone = bookerPhone,
                    onPhoneChange = { bookerPhone = it }
                )
                SectionDivider()
            }

            item {
                SectionTitle(title = "결제 수단")
                PaymentMethodSection(
                    selectedMethod = paymentMethod,
                    onMethodSelected = { paymentMethod = it }
                )
                SectionDivider()
            }

            item {
                SectionTitle(title = "최종 결제 금액")
                PriceSummarySection(details = paymentDetails)
                SectionDivider()
            }

            item {
                TermsAgreementSection(
                    isChecked = allAgreed,
                    onCheckedChange = { allAgreed = it }
                )
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}


@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier.padding(vertical = 12.dp)
    )
}


@Composable
fun ReservationInfo(details: PaymentDetails) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Text(details.accommodationName, fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(4.dp))
        Text(details.roomName, color = Color.Gray)
        HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))
        InfoRow(title = "체크인", value = details.checkInDate)
        Spacer(modifier = Modifier.height(8.dp))
        InfoRow(title = "체크아웃", value = details.checkOutDate)
    }
}


@Composable
fun BookerInfoSection(
    name: String,
    onNameChange: (String) -> Unit,
    phone: String,
    onPhoneChange: (String) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        OutlinedTextField(
            value = name,
            onValueChange = onNameChange,
            label = { Text("이름") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = phone,
            onValueChange = onPhoneChange,
            label = { Text("휴대폰 번호") },
            modifier = Modifier.fillMaxWidth()
        )
    }
}


@Composable
fun PaymentMethodSection(selectedMethod: String, onMethodSelected: (String) -> Unit) {
    val methods = listOf("신용/체크카드", "네이버페이", "카카오페이", "토스페이")
    Row(
        modifier = Modifier.fillMaxWidth(),
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
fun PriceSummarySection(details: PaymentDetails) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        InfoRow(title = "객실 요금", value = "${details.roomPrice}원")
        InfoRow(title = "수수료", value = "${details.fees}원")
        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("총 결제 금액", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text(
                "${details.totalPrice}원",
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
        modifier = Modifier.fillMaxWidth(),
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
fun PaymentBottomBar(price: String, isButtonEnabled: Boolean, onPaymentClick: () -> Unit) {
    Surface(shadowElevation = 8.dp) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = price,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Button(
                onClick = onPaymentClick,
                enabled = isButtonEnabled,
                modifier = Modifier
                    .height(50.dp)
                    .fillMaxWidth(0.6f),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("결제하기", fontSize = 16.sp)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PaymentScreenPreview() {
    MaterialTheme {
        PaymentScreen(
            accommodationId = 0,
            roomId = 0,
            popBackStack = {}
        )
    }
}