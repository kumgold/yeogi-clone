package com.test_application.hollyscompose.nav

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.test_application.hollyscompose.ui.home.HomeScreen

@Composable
fun HollysNavHost(
    modifier: Modifier,
    navHostController: NavHostController
) {
    NavHost(
        modifier = modifier,
        navController = navHostController,
        startDestination = HollysScreen.Home.route
    ) {
        composable(HollysScreen.Home.route) {
            HomeScreen()
        }
    }
}