package com.example.yeogi.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.yeogi.feature.around.AroundMeScreen
import com.example.yeogi.feature.favorite.FavoritesScreen
import com.example.yeogi.feature.home.HomeScreen
import com.example.yeogi.feature.info.MyInfoScreen
import com.example.yeogi.feature.search.SearchScreen

@Composable
fun Navigation(navController: NavHostController, innerPadding: PaddingValues) {
    NavHost(navController = navController, startDestination = BottomNavItem.Home.route) {
        composable(BottomNavItem.Home.route) {
            HomeScreen(innerPadding = innerPadding)
        }
        composable(BottomNavItem.AroundMe.route) {
            AroundMeScreen(navController = navController)
        }
        composable(BottomNavItem.Favorites.route) {
            FavoritesScreen(navController = navController)
        }
        composable(BottomNavItem.MyInfo.route) {
            MyInfoScreen()
        }
        composable(BottomNavItem.Search.route) {
            SearchScreen(navController = navController)
        }
    }
}