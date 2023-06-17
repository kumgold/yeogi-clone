package com.test_application.hollyscompose

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.test_application.hollyscompose.ui.compose.HollysDrawer
import com.test_application.hollyscompose.ui.compose.HollysTopAppBar
import com.test_application.hollyscompose.ui.home.HomeScreen
import com.test_application.hollyscompose.ui.theme.HollysComposeTheme
import kotlinx.coroutines.launch

@Composable
fun HollysComposeApp() {
    HollysComposeTheme {
        val scaffoldState = rememberScaffoldState()
        val coroutineScope = rememberCoroutineScope()
        val navController = rememberNavController()
        val modifier = Modifier.fillMaxSize()

        Scaffold(
            modifier = modifier,
            scaffoldState = scaffoldState,
            backgroundColor = MaterialTheme.colors.background,
            topBar = {
                HollysTopAppBar {
                        coroutineScope.launch { scaffoldState.drawerState.open()
                    }
                }
            },
            drawerContent = { HollysDrawer(modifier = modifier) },
            drawerGesturesEnabled = scaffoldState.drawerState.isOpen
        ) {
            NavHost(
                modifier = Modifier.fillMaxSize(),
                navController = navController,
                startDestination = HollysDestinations.Home
            ) {
                composable(HollysDestinations.Home) {
                    HomeScreen(modifier = modifier)
                }
            }
        }
    }
}