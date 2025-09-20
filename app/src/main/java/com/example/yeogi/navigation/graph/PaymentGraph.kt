package com.example.yeogi.navigation.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.yeogi.core.presentation.SharedPaymentViewModel
import com.example.yeogi.feature.payment.PaymentScreen
import com.example.yeogi.feature.room.RoomSelectionScreen
import com.example.yeogi.navigation.Graph
import com.example.yeogi.navigation.NavItem
import com.example.yeogi.navigation.horizontalSlideComposable

fun NavGraphBuilder.paymentGraph(navController: NavHostController) {
    val sharedPaymentViewModel = SharedPaymentViewModel()

    navigation(
        startDestination = NavItem.RoomSelection.route,
        route = Graph.PAYMENT
    ) {
        horizontalSlideComposable(
            route = NavItem.RoomSelection.route,
            arguments = listOf(
                navArgument("accommodationId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val accommodationId = backStackEntry.arguments?.getInt("accommodationId")

            requireNotNull(accommodationId) { "Accommodation ID is required as an argument" }

            sharedPaymentViewModel.setAccommodation(accommodationId)

            RoomSelectionScreen(
                accommodationId = accommodationId,
                sharedPaymentViewModel = sharedPaymentViewModel,
                navigateToPayment = { roomId ->
                    navController.navigate(NavItem.Payment.createRoute(
                        accommodationId = accommodationId,
                        roomId = roomId
                    ))
                },
                popBackStack = {
                    navController.popBackStack()
                }
            )
        }
        horizontalSlideComposable(
            route = NavItem.Payment.route,
            arguments = listOf(
                navArgument("accommodationId") { type = NavType.IntType },
                navArgument("roomId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val accommodationId = backStackEntry.arguments?.getInt("accommodationId")
            val roomId = backStackEntry.arguments?.getInt("roomId")

            requireNotNull(accommodationId) { "Accommodation ID is required as an argument" }
            requireNotNull(roomId) { "room ID is required as an argument" }

            PaymentScreen(
                sharedPaymentViewModel = sharedPaymentViewModel,
                accommodationId = accommodationId,
                roomId = roomId,
                popBackStack = {
                    navController.popBackStack()
                }
            )
        }
    }
}