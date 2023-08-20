package com.test_application.hollyscompose.ui.coupon

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.test_application.hollyscompose.R
import com.test_application.hollyscompose.data.Coupon
import com.test_application.hollyscompose.ui.compose.HollysDefaultTopAppBar
import com.test_application.hollyscompose.ui.theme.HollysDefaultRoundShape
import com.test_application.hollyscompose.ui.theme.HollysTypography
import com.test_application.hollyscompose.util.HollysDestination
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CouponScreen(
    modifier: Modifier,
    navController: NavHostController
) {
    val pagerState = rememberPagerState()
    val viewModel = viewModel<CouponViewModel>()

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
            pagerState = pagerState,
            viewModel = viewModel,
            navController = navController
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun CouponTabLayout(
    pagerState: PagerState,
    viewModel: CouponViewModel,
    navController: NavHostController
) {
    val coroutineScope = rememberCoroutineScope()
    val list = listOf(
        stringResource(id = R.string.enable_coupon),
        stringResource(id = R.string.disable_coupon)
    )
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
            stringResource(id = R.string.enable_coupon) -> {
                CouponListView(
                    viewModel.couponList.filter {
                        !it.isExpired
                    },
                    navController = navController
                )
            }
            stringResource(id = R.string.disable_coupon) -> {
                CouponListView(
                    viewModel.couponList.filter {
                        it.isExpired
                    },
                    navController = navController
                )
            }
        }
    }
}

@Composable
private fun CouponListView(
    couponList: List<Coupon>,
    navController: NavHostController
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        items(couponList) {coupon ->
            CouponItemView(
                coupon = coupon,
                onClick = {
                    navController.navigate("${HollysDestination.COUPON}/${coupon.id}")
                }
            )
        }
    }
}
