package com.test_application.hollyscompose.ui.smart_order

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.test_application.hollyscompose.ui.compose.HollysDefaultTopAppBar

@Composable
fun SmartOrderScreen(
    modifier: Modifier,
    navController: NavHostController
) {
    Column(
        modifier = modifier
    ) {
        HollysDefaultTopAppBar { navController.popBackStack() }

    }
}