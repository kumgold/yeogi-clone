package com.test_application.hollyscompose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.test_application.hollyscompose.ui.compose.HollysDrawer
import com.test_application.hollyscompose.ui.coupon.CouponScreen
import com.test_application.hollyscompose.ui.coupon.coupon_detail.CouponDetailScreen
import com.test_application.hollyscompose.ui.home.HomeScreen
import com.test_application.hollyscompose.ui.smart_order.SmartOrderScreen
import com.test_application.hollyscompose.ui.theme.HollysComposeTheme
import com.test_application.hollyscompose.util.HollysDestination

@Composable
fun HollysComposeApp() {
    HollysComposeTheme {
        val scaffoldState = rememberScaffoldState()
        val navController = rememberNavController()
        val modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)

        Scaffold(
            modifier = modifier,
            scaffoldState = scaffoldState,
            backgroundColor = MaterialTheme.colors.background,
            drawerContent = {
                HollysDrawer(
                    modifier = modifier,
                    navController = navController,
                    scaffoldState = scaffoldState
                )
            },
            drawerGesturesEnabled = scaffoldState.drawerState.isOpen
        ) {
            NavHost(
                modifier = Modifier.fillMaxSize(),
                navController = navController,
                startDestination = HollysDestination.HOME
            ) {
                composable(HollysDestination.HOME) {
                    HomeScreen(
                        modifier = modifier,
                        navController = navController,
                        scaffoldState = scaffoldState
                    )
                }

                composable(HollysDestination.SMART_ORDER) {
                    SmartOrderScreen(
                        modifier = modifier,
                        navController = navController
                    )
                }

                composable(HollysDestination.COUPON) {
                    CouponScreen(
                        modifier = modifier,
                        navController = navController
                    )
                }

                composable(
                    route = "${HollysDestination.COUPON}/{${HollysDestination.COUPON_ID_KEY}}",
                    arguments = listOf(navArgument(HollysDestination.COUPON_ID_KEY) { type = NavType.LongType})
                ) { backStackEntry ->
                    val arguments = requireNotNull(backStackEntry.arguments)
                    val couponId = arguments.getLong(HollysDestination.COUPON_ID_KEY)
                    CouponDetailScreen(
                        modifier = modifier,
                        navController = navController,
                        couponId = couponId
                    )
                }
            }
        }
    }
}