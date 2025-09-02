package com.example.yeogi.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.yeogi.feature.AroundMeScreen
import com.example.yeogi.feature.favorite.FavoritesScreen
import com.example.yeogi.feature.home.HomeScreen
import com.example.yeogi.feature.MyInfoScreen

@Composable
fun Navigation(navController: NavHostController, innerPadding: PaddingValues) {
    NavHost(navController = navController, startDestination = BottomNavItem.Home.route) {
        composable(BottomNavItem.Home.route) {
            HomeScreen(innerPadding = innerPadding)
        }
        composable(BottomNavItem.AroundMe.route) {
            AroundMeScreen(innerPadding = innerPadding)
        }
        composable(BottomNavItem.Favorites.route) {
            FavoritesScreen(innerPadding = innerPadding)
        }
        composable(BottomNavItem.MyInfo.route) {
            MyInfoScreen()
        }
    }
}