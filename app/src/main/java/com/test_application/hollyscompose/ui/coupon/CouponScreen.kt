package com.test_application.hollyscompose.ui.coupon

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
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
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CouponScreen(
    modifier: Modifier,
    navController: NavHostController
) {
    val pagerState = rememberPagerState()

    Column(
        modifier = modifier
    ) {
        HollysDefaultTopAppBar { navController.popBackStack() }

        Text(
            modifier = Modifier.padding(vertical = 20.dp, horizontal = 10.dp),
            text = stringResource(id = R.string.my_coupon),
            style = HollysTypography.h2
        )

        CouponTabLayout(
            pagerState = pagerState
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun CouponTabLayout(
    pagerState: PagerState
) {
    val coroutineScope = rememberCoroutineScope()
    val list = listOf("사용가능쿠폰", "만료쿠폰")
    val selectedIndex = pagerState.currentPage

    // Tab row
    TabRow(
        modifier = Modifier
            .padding(horizontal = 8.dp)
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
        list.forEachIndexed { index, page ->
            val selected = (pagerState.currentPage == index)
            Tab(
                modifier = if (selected) {
                    Modifier
                        .clip(HollysDefaultRoundShape)
                        .background(MaterialTheme.colors.primary)
                } else {
                    Modifier
                        .clip(HollysDefaultRoundShape)
                        .background(MaterialTheme.colors.background)
                },
                selected = selected,
                onClick = {
                    coroutineScope.launch { pagerState.animateScrollToPage(index) }
                }
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 10.dp),
                    text = page,
                    color = if (selected) {
                        MaterialTheme.colors.background
                    } else {
                        MaterialTheme.colors.surface
                    },
                )
            }
        }
    }

    // Page
    HorizontalPager(
        modifier = Modifier.background(MaterialTheme.colors.background),
        pageCount = list.size,
        state = pagerState,
        verticalAlignment = Alignment.Top
    ) { index ->
        when (list[index]) {
            "사용가능쿠폰" -> {
                Text(
                    text = "사용가능쿠폰"
                )
            }
            "만료쿠폰" -> {
                Text(
                    text = "만료쿠폰"
                )
            }
        }
    }
}

@Composable
private fun AvailableCouponListScreen() {

}

@Composable
private fun ExpiredCouponListScreen() {

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
@Preview
private fun CouponScreenPreview() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        val pagerState = rememberPagerState()

        HollysDefaultTopAppBar {  }

        Text(
            modifier = Modifier.padding(vertical = 20.dp, horizontal = 10.dp),
            text = stringResource(id = R.string.my_coupon),
            style = HollysTypography.h2
        )

        CouponTabLayout(pagerState = pagerState)
    }
}