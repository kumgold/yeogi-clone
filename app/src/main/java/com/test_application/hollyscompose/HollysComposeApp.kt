package com.test_application.hollyscompose

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.test_application.hollyscompose.ui.compose.HollysTopAppBar
import com.test_application.hollyscompose.ui.home.HomeScreen
import com.test_application.hollyscompose.ui.theme.HollysComposeTheme

@Composable
fun HollysComposeApp() {
    HollysComposeTheme {
        val navController = rememberNavController()

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            backgroundColor = MaterialTheme.colors.background,
            topBar = { HollysTopAppBar() },
        ) {
            NavHost(
                modifier = Modifier.fillMaxSize(),
                navController = navController,
                startDestination = HollysDestinations.Home
            ) {
                composable(HollysDestinations.Home) {
                    HomeScreen()
                }
            }
        }
    }
}