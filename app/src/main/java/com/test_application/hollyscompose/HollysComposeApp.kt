package com.test_application.hollyscompose

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.test_application.hollyscompose.ui.compose.HollysDrawer
import com.test_application.hollyscompose.ui.coupon.CouponScreen
import com.test_application.hollyscompose.ui.home.HomeScreen
import com.test_application.hollyscompose.ui.smart_order.SmartOrderScreen
import com.test_application.hollyscompose.ui.theme.HollysComposeTheme

@Composable
fun HollysComposeApp() {
    HollysComposeTheme {
        val scaffoldState = rememberScaffoldState()
        val navController = rememberNavController()
        val modifier = Modifier.fillMaxSize()

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
                startDestination = HollysDestinations.HOME
            ) {
                composable(HollysDestinations.HOME) {
                    HomeScreen(
                        modifier = modifier,
                        navController = navController,
                        scaffoldState = scaffoldState
                    )
                }

                composable(HollysDestinations.SMART_ORDER) {
                    SmartOrderScreen(
                        modifier = modifier,
                        navController = navController
                    )
                }

                composable(HollysDestinations.COUPON) {
                    CouponScreen()
                }
            }
        }
    }
}