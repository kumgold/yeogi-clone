package com.test_application.hollyscompose.ui.coupon

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.test_application.hollyscompose.R
import com.test_application.hollyscompose.data.Coupon
import com.test_application.hollyscompose.ui.theme.BlackAlpha50
import com.test_application.hollyscompose.ui.theme.HollysTypography

@Composable
fun CouponItemView(
    coupon: Coupon,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.background)
            .padding(10.dp)
            .height(100.dp)
            .clickable(onClick = onClick),
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .padding(horizontal = 10.dp)
        ) {
            Image(
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colors.primary
                    ),
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = null
            )

            if (coupon.isExpired) {
                Box(
                    modifier = Modifier
                        .size(width = 100.dp, height = 100.dp)
                        .padding(1.dp)
                        .background(BlackAlpha50)
                ) {
                   Text(
                       modifier = Modifier.align(Alignment.Center),
                       text = "기간만료",
                       textAlign = TextAlign.Center,
                       style = HollysTypography.body1,
                       color = Color.White
                   )
                }
            }
        }

        Column {
            Text(
                text = coupon.title,
                style = HollysTypography.body1,
                color = Color.LightGray
            )
            Text(
                text = coupon.name,
                style = HollysTypography.body2,
            )
            Row {
                Text(
                    text = "유효기간",
                    style = HollysTypography.button,
                )
                Text(
                    modifier = Modifier.padding(start = 45.5.dp),
                    text = "| ${coupon.startDate}~${coupon.expiredDate}",
                    style = HollysTypography.button,
                )
            }
            Row {
                Text(
                    text = "사용가능매장",
                    style = HollysTypography.button,
                )
                Text(
                    modifier = Modifier.padding(start = 20.dp),
                    text = "| ${coupon.store}",
                    style = HollysTypography.button,
                )
            }
        }
    }
}

@Preview
@Composable
private fun CouponItemPreview() {
    CouponItemView(
        coupon = Coupon("", "", "2023-08-08", "2024-08-08", "", false, false)
    ) {

    }
}