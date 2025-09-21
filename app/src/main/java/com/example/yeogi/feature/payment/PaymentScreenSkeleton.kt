package com.example.yeogi.feature.payment // 본인의 패키지 경로에 맞게 수정

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.yeogi.core.util.skeletonBackground

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentScreenSkeleton() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = { }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = {
            PaymentBottomBarSkeleton()
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            userScrollEnabled = false
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
                ReservationInfoSkeleton()
                SectionDivider()
            }
            item {
                SectionTitleSkeleton()
                BookerInfoSectionSkeleton()
                SectionDivider()
            }
            item {
                SectionTitleSkeleton()
                PriceSummarySectionSkeleton()
                SectionDivider()
            }
            item {
                SectionTitleSkeleton()
                PaymentMethodSectionSkeleton()
                SectionDivider()
            }
            item {
                TermsAgreementSectionSkeleton()
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun ReservationInfoSkeleton() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Spacer(
            modifier = Modifier
                .height(24.dp)
                .fillMaxWidth(0.6f)
                .skeletonBackground()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Spacer(
            modifier = Modifier
                .height(18.dp)
                .fillMaxWidth(0.4f)
                .skeletonBackground()
        )
        HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.height(20.dp).fillMaxWidth(0.2f).skeletonBackground())
            Spacer(modifier = Modifier.height(20.dp).fillMaxWidth(0.3f).skeletonBackground())
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.height(20.dp).fillMaxWidth(0.2f).skeletonBackground())
            Spacer(modifier = Modifier.height(20.dp).fillMaxWidth(0.3f).skeletonBackground())
        }
    }
}

@Composable
private fun SectionTitleSkeleton() {
    Spacer(
        modifier = Modifier
            .padding(vertical = 12.dp, horizontal = 16.dp)
            .height(30.dp)
            .fillMaxWidth(0.5f)
            .skeletonBackground()
    )
}

@Composable
private fun BookerInfoSectionSkeleton() {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Spacer(modifier = Modifier.fillMaxWidth().height(56.dp).skeletonBackground())
        Spacer(modifier = Modifier.fillMaxWidth().height(56.dp).skeletonBackground())
    }
}

@Composable
private fun PriceSummarySectionSkeleton() {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.height(20.dp).fillMaxWidth(0.3f).skeletonBackground())
            Spacer(modifier = Modifier.height(20.dp).fillMaxWidth(0.25f).skeletonBackground())
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.height(20.dp).fillMaxWidth(0.2f).skeletonBackground())
            Spacer(modifier = Modifier.height(20.dp).fillMaxWidth(0.2f).skeletonBackground())
        }
        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.height(24.dp).fillMaxWidth(0.4f).skeletonBackground())
            Spacer(modifier = Modifier.height(28.dp).fillMaxWidth(0.35f).skeletonBackground())
        }
    }
}

@Composable
private fun PaymentMethodSectionSkeleton() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        repeat(4) {
            Spacer(
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp)
                    .skeletonBackground()
            )
        }
    }
}

@Composable
private fun TermsAgreementSectionSkeleton() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.size(24.dp).skeletonBackground(RoundedCornerShape(2.dp)))
        Spacer(modifier = Modifier.width(12.dp))
        Spacer(modifier = Modifier.height(20.dp).fillMaxWidth(0.4f).skeletonBackground())
    }
}


@Composable
private fun PaymentBottomBarSkeleton() {
    Surface(
        modifier = Modifier.padding(16.dp),
        shadowElevation = 8.dp,
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(
                modifier = Modifier
                    .height(32.dp)
                    .fillMaxWidth(0.3f)
                    .skeletonBackground()
            )
            Spacer(
                modifier = Modifier
                    .height(50.dp)
                    .fillMaxWidth(0.6f)
                    .skeletonBackground(RoundedCornerShape(12.dp))
            )
        }
    }
}