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
import com.example.yeogi.feature.accommodation.AccommodationScreen
import com.example.yeogi.feature.hotel.HotelScreen
import com.example.yeogi.feature.searchdetail.domestic.DomesticSearchDetailScreen
import com.example.yeogi.feature.searchdetail.oversea.OverseaSearchDetailScreen
import com.example.yeogi.navigation.graph.Graph
import com.example.yeogi.navigation.graph.homeGraph
import com.example.yeogi.navigation.graph.paymentGraph

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Graph.HOME) {
        homeGraph(navController = navController)
        paymentGraph(navController = navController)

        horizontalSlideComposable(
            route = NavItem.DomesticSearchDetail.route,
            arguments = listOf(
                navArgument("query") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val query = NavItem.DomesticSearchDetail.decodeQuery(backStackEntry)

            DomesticSearchDetailScreen(
                query = query,
                navigateToAccommodation = { id ->
                    navController.navigate(NavItem.AccommodationDetail.createRoute(id))
                },
                popBackStack = {
                    navController.popBackStack()
                }
            )
        }

        horizontalSlideComposable(
            route = NavItem.OverseaSearchDetail.route,
            arguments = listOf(
                navArgument("query") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val query = NavItem.OverseaSearchDetail.decodeQuery(backStackEntry)

            OverseaSearchDetailScreen(
                query = query,
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

            AccommodationScreen(
                accommodationId = accommodationId,
                navigateToRoomSelection = { id ->
                    navController.navigate(NavItem.RoomSelection.createRoute(id))
                },
                popBackStack = {
                    navController.popBackStack()
                }
            )
        }

        horizontalSlideComposable(
            route = NavItem.Hotel.route
        ) {
            HotelScreen(
                navigateToAccommodation = { id ->
                    navController.navigate(NavItem.AccommodationDetail.createRoute(id))
                },
                navigateToSearchDetail = { query ->
                    navController.navigate(NavItem.DomesticSearchDetail.createRoute(query))
                },
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