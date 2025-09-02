package com.example.yeogi.main

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.yeogi.navigation.BottomNavigationBar
import com.example.yeogi.navigation.Navigation

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigationBar(navController = navController) }
    ) { innerPadding ->
        Navigation(navController = navController, innerPadding = innerPadding)
    }
}