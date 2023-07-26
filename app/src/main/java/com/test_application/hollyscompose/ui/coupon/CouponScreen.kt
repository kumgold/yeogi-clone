package com.test_application.hollyscompose.ui.coupon

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.test_application.hollyscompose.R
import com.test_application.hollyscompose.ui.compose.HollysDefaultTopAppBar
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
            text = "나의 쿠폰",
            style = HollysTypography.h2
        )

    }
}