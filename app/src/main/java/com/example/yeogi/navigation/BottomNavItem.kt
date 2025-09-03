package com.example.yeogi.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    data object Home : BottomNavItem("home", "홈", Icons.Filled.Home)
    data object Search : BottomNavItem("search", "검색", Icons.Default.Search)
    data object AroundMe : BottomNavItem("around_me", "주변", Icons.Filled.LocationOn)
    data object Favorites : BottomNavItem("favorites", "찜 목록", Icons.Filled.FavoriteBorder)
    data object MyInfo : BottomNavItem("my_info", "내정보", Icons.Filled.Person)

}