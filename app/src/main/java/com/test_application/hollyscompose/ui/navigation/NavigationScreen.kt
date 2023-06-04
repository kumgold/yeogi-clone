package com.test_application.hollyscompose.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.test_application.hollyscompose.nav.HollysNavHost

@Composable
fun NavigationScreen() {
    val navController = rememberNavController()
    val modifier = Modifier.fillMaxSize()

    Scaffold() {
        HollysNavHost(
            modifier = modifier,
            navHostController = navController
        )
    }
}

