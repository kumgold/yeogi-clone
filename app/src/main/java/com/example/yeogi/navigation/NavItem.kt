package com.example.yeogi.navigation

sealed class NavItem(
    val route: String,
    val title: String,
) {
    data object SearchDetail: NavItem("search_detail", "상세 검색")
    data object AccommodationDetail: NavItem("accommodation/{accommodationId}", "숙소") {
        fun createRoute(accommodationId: Int) = "accommodation/$accommodationId"
    }
    data object RoomSelection: NavItem("roomSelection/{accommodationId}", "방 선택") {
        fun createRoute(accommodationId: Int) = "roomSelection/$accommodationId"
    }
    data object Payment: NavItem("payment", "결제")
    data object Hotel: NavItem("hotel", "호텔 검색")
}