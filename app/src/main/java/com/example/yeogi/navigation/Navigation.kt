package com.example.yeogi.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.yeogi.data.model.dummyAccommodations
import com.example.yeogi.feature.accommodation.AccommodationScreen
import com.example.yeogi.feature.around.AroundMeScreen
import com.example.yeogi.feature.favorite.FavoritesScreen
import com.example.yeogi.feature.home.HomeScreen
import com.example.yeogi.feature.info.MyInfoScreen
import com.example.yeogi.feature.room.RoomSelectionScreen
import com.example.yeogi.feature.search.SearchScreen
import com.example.yeogi.feature.searchdetail.SearchDetailScreen

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
        horizontalSlideComposable(NavItem.SearchDetail.route) {
            SearchDetailScreen(
                navigateToAccommodation = { id ->
                    navController.navigate(NavItem.AccommodationDetail.createRoute(id))
                },
                popBackStack = {
                    navController.popBackStack()
                }
            )
        }
        horizontalSlideComposable(
            route = NavItem.AccommodationDetail.route,
            arguments = listOf(
                navArgument("accommodationId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val accommodationId = backStackEntry.arguments?.getInt("accommodationId")

            requireNotNull(accommodationId) { "Accommodation ID is required as an argument" }
            val accommodation = dummyAccommodations.find { it.id == accommodationId }
            requireNotNull(accommodation) { "Accommodation not found for ID: $accommodationId" }

            AccommodationScreen(
                accommodation = accommodation,
                navigateToRoomSelection = { id ->
                    navController.navigate(NavItem.RoomSelection.createRoute(id))
                },
                popBackStack = {
                    navController.popBackStack()
                }
            )
        }
        horizontalSlideComposable(
            route = NavItem.RoomSelection.route,
            arguments = listOf(
                navArgument("accommodationId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val accommodationId = backStackEntry.arguments?.getInt("accommodationId")

            requireNotNull(accommodationId) { "Accommodation ID is required as an argument" }
            val accommodation = dummyAccommodations.find { it.id == accommodationId }
            requireNotNull(accommodation) { "Accommodation not found for ID: $accommodationId" }

            RoomSelectionScreen(
                accommodation = accommodation,
                popBackStack = {
                    navController.popBackStack()
                }
            )
        }
    }
}

fun NavGraphBuilder.horizontalSlideComposable(
    route: String,
    durationMillis: Int = 350,
    arguments: List<NamedNavArgument> = emptyList(),
    content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit
) {
    composable(
        route = route,
        arguments = arguments,
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