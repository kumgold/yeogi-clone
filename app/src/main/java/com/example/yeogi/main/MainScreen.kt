package com.example.yeogi.main

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.yeogi.navigation.BottomNavItem
import com.example.yeogi.navigation.BottomNavigationBar
import com.example.yeogi.navigation.Navigation

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    if (currentRoute == BottomNavItem.Home.route) {
        Scaffold(
            bottomBar = { BottomNavigationBar(navController = navController) }
        ) { innerPadding ->
            Navigation(navController = navController, innerPadding = innerPadding)
        }
    } else {
        Navigation(navController = navController, innerPadding = PaddingValues())
    }
}