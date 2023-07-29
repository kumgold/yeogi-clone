package com.test_application.hollyscompose.ui.coupon

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.test_application.hollyscompose.R
import com.test_application.hollyscompose.ui.compose.HollysDefaultTopAppBar
import com.test_application.hollyscompose.ui.theme.HollysDefaultRoundShape
import com.test_application.hollyscompose.ui.theme.HollysTypography

@Composable
fun CouponScreen(
    modifier: Modifier,
    navController: NavHostController
) {
    Column(
        modifier = modifier
    ) {
        HollysDefaultTopAppBar { navController.popBackStack() }

        Text(
            modifier = Modifier.padding(vertical = 20.dp, horizontal = 10.dp),
            text = stringResource(id = R.string.my_coupon),
            style = HollysTypography.h2
        )

        CouponTabLayout()
    }
}

@Composable
private fun CouponTabLayout() {
    val list = listOf("사용가능쿠폰", "만료쿠폰")
    var selectedIndex by remember {
        mutableStateOf(0)
    }

    TabRow(
        modifier = Modifier.padding(horizontal = 8.dp)
            .clip(HollysDefaultRoundShape)
            .border(
                width = 1.dp,
                color = Color.LightGray,
                shape = HollysDefaultRoundShape
            )
            .padding(1.dp),
        selectedTabIndex = selectedIndex,
        backgroundColor = MaterialTheme.colors.background,
        contentColor = MaterialTheme.colors.primary,
        indicator = { }
    ) {
        list.forEachIndexed { index, item ->
            val selected = (selectedIndex == index)
            Tab(
                modifier = if (selected) {
                    Modifier
                        .clip(HollysDefaultRoundShape)
                        .background(MaterialTheme.colors.primary)
                        .padding(vertical = 10.dp)
                } else {
                    Modifier
                        .clip(HollysDefaultRoundShape)
                        .background(MaterialTheme.colors.background)
                        .padding(vertical = 10.dp)
                },
                selected = selected,
                onClick = {
                    selectedIndex = index
                }
            ) {
                Text(
                    text = item,
                    color = if (selected) {
                        MaterialTheme.colors.background
                    } else {
                        MaterialTheme.colors.surface
                    },
                )
            }
        }
    }
}

@Composable
@Preview
private fun CouponScreenPreview() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        HollysDefaultTopAppBar {  }

        Text(
            modifier = Modifier.padding(vertical = 20.dp, horizontal = 10.dp),
            text = stringResource(id = R.string.my_coupon),
            style = HollysTypography.h2
        )

        CouponTabLayout()
    }
}