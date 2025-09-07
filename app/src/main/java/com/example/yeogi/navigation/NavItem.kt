package com.example.yeogi.navigation

sealed class NavItem(
    val route: String,
    val title: String,
) {
    data object SearchDetail : NavItem("search_detail", "상세 검색")
}