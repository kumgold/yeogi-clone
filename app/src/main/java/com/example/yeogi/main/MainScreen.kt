package com.example.yeogi.main

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.yeogi.navigation.Navigation

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Navigation(navController = navController)
}