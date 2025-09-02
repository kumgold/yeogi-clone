package com.example.yeogi.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Home : BottomNavItem("home", "홈", Icons.Filled.Home)
    object AroundMe : BottomNavItem("around_me", "내주변", Icons.Filled.LocationOn)
    object Favorites : BottomNavItem("favorites", "찜", Icons.Filled.FavoriteBorder)
    object MyInfo : BottomNavItem("my_info", "내정보", Icons.Filled.Person)
}