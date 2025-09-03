package com.example.yeogi.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.IntOffset
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.yeogi.feature.around.AroundMeScreen
import com.example.yeogi.feature.favorite.FavoritesScreen
import com.example.yeogi.feature.home.HomeScreen
import com.example.yeogi.feature.info.MyInfoScreen
import com.example.yeogi.feature.search.SearchScreen

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = BottomNavItem.Home.route) {
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

fun NavGraphBuilder.horizontalSlideComposable(
    route: String,
    durationMillis: Int = 350,
    content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit
) {
    composable(
        route = route,
        enterTransition = {
            slideInHorizontally(
                initialOffsetX = { fullWidth -> fullWidth },
                animationSpec = tween(durationMillis)
            )
        },
        exitTransition = {
            slideOutHorizontally(
                targetOffsetX = { fullWidth -> -fullWidth },
                animationSpec = tween(durationMillis)
            )
        },
        popEnterTransition = {
            slideInHorizontally(
                initialOffsetX = { fullWidth -> -fullWidth },
                animationSpec = tween(durationMillis)
            )
        },
        popExitTransition = {
            slideOutHorizontally(
                targetOffsetX = { fullWidth -> fullWidth },
                animationSpec = tween(durationMillis)
            )
        },
        content = content
    )
}