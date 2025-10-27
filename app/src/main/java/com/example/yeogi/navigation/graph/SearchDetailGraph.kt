package com.example.yeogi.navigation.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.yeogi.feature.searchdetail.domestic.DomesticSearchDetailScreen
import com.example.yeogi.feature.searchdetail.flight.FlightSearchDetailScreen
import com.example.yeogi.feature.searchdetail.oversea.OverseaSearchDetailScreen
import com.example.yeogi.navigation.BottomNavItem
import com.example.yeogi.navigation.NavItem
import com.example.yeogi.navigation.horizontalSlideComposable

fun NavGraphBuilder.searchDetailGraph(navController: NavHostController) {
    navigation(
        startDestination = BottomNavItem.Search.route,
        route = Graph.SEARCH_DETAIL
    ) {
        horizontalSlideComposable(
            route = NavItem.DomesticSearchDetail.route,
            arguments = listOf(
                navArgument("query") { type = NavType.StringType }
            )
        ) {
            DomesticSearchDetailScreen(
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
        ) {
            OverseaSearchDetailScreen(
                navigateToAccommodation = { id ->
                    navController.navigate(NavItem.AccommodationDetail.createRoute(id))
                },
                popBackStack = {
                    navController.popBackStack()
                }
            )
        }

        horizontalSlideComposable(
            route = NavItem.FlightSearchDetail.route,
            arguments = listOf(
                navArgument("departure") { type = NavType.StringType },
                navArgument("arrival") { type = NavType.StringType },
                navArgument("type") { type = NavType.StringType }
            )
        ) {
            FlightSearchDetailScreen(
                popBackStack = {
                    navController.popBackStack()
                }
            )
        }
    }
}