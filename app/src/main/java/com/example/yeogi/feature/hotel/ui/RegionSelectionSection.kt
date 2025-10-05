package com.example.yeogi.feature.hotel.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun RegionSelectionSection(
    title: String,
    regions: Map<String, List<String>>,
    navigateToSearchDetail: (String) -> Unit
) {
    var expandedRegion by remember { mutableStateOf<String?>(null) }

    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text(title, style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))

        val chunkedRegions = regions.keys.chunked(3)

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(BorderStroke(1.dp, Color.LightGray), RoundedCornerShape(8.dp))
                .clip(RoundedCornerShape(8.dp))
        ) {
            chunkedRegions.forEachIndexed { rowIndex, rowItems ->
                Row(Modifier.fillMaxWidth()) {
                    rowItems.forEachIndexed { itemIndex, region ->
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clickable {
                                    expandedRegion = if (expandedRegion == region) null else region
                                }
                                .padding(vertical = 12.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = region,
                                style = MaterialTheme.typography.labelMedium
                            )
                        }

                        if (itemIndex < rowItems.size - 1) {
                            Box(
                                modifier = Modifier
                                    .width(1.dp)
                                    .height(42.dp)
                                    .background(Color.LightGray)
                            )
                        }
                    }
                }

                AnimatedVisibility(
                    visible = rowItems.contains(expandedRegion),
                    enter = expandVertically(animationSpec = tween(300)) + fadeIn(animationSpec = tween(300)),
                    exit = shrinkVertically(animationSpec = tween(300)) + fadeOut(animationSpec = tween(300))
                ) {
                    val details = regions[expandedRegion].orEmpty()
                    ExpandedDetailView(
                        details = details,
                        navigateToSearchDetail = navigateToSearchDetail
                    )
                }

                if (rowIndex < chunkedRegions.size - 1) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(Color.LightGray)
                    )
                }
            }
        }
    }
}

/**
 * 확장되었을 때 보여줄 상세 지역 그리드 Composable
 */
@Composable
private fun ExpandedDetailView(
    details: List<String>,
    navigateToSearchDetail: (String) -> Unit
) {
    val chunkedDetails = details.chunked(4)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Gray.copy(alpha = 0.05f))
            .padding(8.dp)
    ) {
        chunkedDetails.forEach { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                rowItems.forEach { detail ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(4.dp))
                            .clickable { navigateToSearchDetail(detail) }
                            .padding(vertical = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = detail, style = MaterialTheme.typography.labelMedium)
                    }
                }
                repeat(4 - rowItems.size) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}