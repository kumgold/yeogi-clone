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
    data object Payment: NavItem("payment/{accommodationId}/{roomId}", "결제") {
        fun createRoute(accommodationId: Int, roomId: Int) = "payment/$accommodationId/$roomId"
    }
    data object Hotel: NavItem("hotel", "호텔•리조트")
}