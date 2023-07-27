package com.test_application.hollyscompose.ui.coupon

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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

        TabRow(selectedTabIndex = 0) {

        }
    }
}

@Composable
private fun CouponTabLayout() {

}

@Composable
@Preview
private fun CouponScreenPreview() {
    val list = listOf("사용가능쿠폰", "만료쿠폰")
    var selectedIndex by remember {
        mutableStateOf(0)
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        HollysDefaultTopAppBar {  }

        Text(
            modifier = Modifier.padding(vertical = 20.dp, horizontal = 10.dp),
            text = stringResource(id = R.string.my_coupon),
            style = HollysTypography.h2
        )
        
        TabRow(
            modifier = Modifier.fillMaxWidth(),
            selectedTabIndex = selectedIndex,
            indicator = { tabPositions ->
                Box {}
            }
        ) {
            list.forEachIndexed { index, item ->
                val selected = (selectedIndex == index)
                Tab(
                    modifier = Modifier
                        .clip(RoundedCornerShape(50)),
                    selected = selected,
                    onClick = {
                        selectedIndex = index
                    }
                ) {
                    Text(text = item)
                }
            }
        }
    }
}