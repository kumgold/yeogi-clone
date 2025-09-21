package com.example.yeogi.navigation.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.yeogi.feature.around.AroundMeScreen
import com.example.yeogi.feature.favorite.FavoritesScreen
import com.example.yeogi.feature.home.HomeScreen
import com.example.yeogi.feature.info.MyInfoScreen
import com.example.yeogi.feature.search.SearchScreen
import com.example.yeogi.navigation.BottomNavItem
import com.example.yeogi.navigation.Graph
import com.example.yeogi.navigation.horizontalSlideComposable

fun NavGraphBuilder.homeGraph(navController: NavHostController) {
    navigation(
        startDestination = BottomNavItem.Home.route,
        route = Graph.HOME
    ) {
        composable(BottomNavItem.Home.route) {
            HomeScreen(navController = navController)
        }

        horizontalSlideComposable(BottomNavItem.AroundMe.route) {
            AroundMeScreen(navController = navController)
        }
        horizontalSlideComposable(BottomNavItem.Favorites.route) {
            FavoritesScreen(navController = navController)
        }
        horizontalSlideComposable(BottomNavItem.MyInfo.route) {
            MyInfoScreen()
        }
        horizontalSlideComposable(BottomNavItem.Search.route) {
            SearchScreen(navController = navController)
        }
    }
}