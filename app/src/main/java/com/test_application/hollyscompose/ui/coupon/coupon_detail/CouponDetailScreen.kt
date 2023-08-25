package com.test_application.hollyscompose.ui.coupon.coupon_detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.test_application.hollyscompose.R
import com.test_application.hollyscompose.data.Coupon
import com.test_application.hollyscompose.ui.compose.HollysDefaultTopAppBar
import com.test_application.hollyscompose.ui.theme.HollysTypography

@Composable
fun CouponDetailScreen(
    viewModel: CouponDetailViewModel = viewModel(),
    navController: NavHostController,
    modifier: Modifier,
    couponId: Long
) {
    val coupon = viewModel.coupon.observeAsState(viewModel.getCoupon(couponId))

    CouponDetailScreen(
        navController = navController,
        modifier = modifier,
        coupon = coupon.value as Coupon
    )
}

@Composable
private fun CouponDetailScreen(
    navController: NavHostController,
    modifier: Modifier,
    coupon: Coupon
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
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .background(color = Color.Black)
                .height(1.dp)
        )
        Image(
            modifier = Modifier
                .padding(top = 50.dp)
                .align(Alignment.CenterHorizontally)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colors.primary
                ),
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = null
        )
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 20.dp),
            text = stringResource(id = R.string.barcode),
            style = HollysTypography.h2
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .background(color = Color.Black)
                .height(1.dp)
        )
        Text(
            modifier = Modifier
                .padding(top = 20.dp)
                .padding(horizontal = 20.dp),
            text = coupon.name,
            style = HollysTypography.body1
        )
    }
}