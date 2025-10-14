package com.example.yeogi.navigation

import androidx.navigation.NavBackStackEntry
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

sealed class NavItem(
    val route: String,
    val title: String,
) {
    data object DomesticSearchDetail: NavItem("domestic_search_detail/{query}", "상세 검색") {
        fun createRoute(query: String): String {
            val encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8.toString())
            return "domestic_search_detail/$encodedQuery"
        }

        fun decodeQuery(backStackEntry: NavBackStackEntry): String? {
            val encodedQuery = backStackEntry.arguments?.getString("query")
            val query = if (encodedQuery?.contains("전체") == true) {
                encodedQuery.replace("전체", "")
            } else {
                encodedQuery
            }

            return encodedQuery?.let {
                URLDecoder.decode(query, StandardCharsets.UTF_8.toString())
            }
        }
    }
    data object OverseaSearchDetail: NavItem("oversea_search_detail/{query}", "상세 검색") {
        fun createRoute(query: String): String {
            val encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8.toString())
            return "oversea_search_detail/$encodedQuery"
        }

        fun decodeQuery(backStackEntry: NavBackStackEntry): String? {
            val encodedQuery = backStackEntry.arguments?.getString("query")
            val query = if (encodedQuery?.contains("전체") == true) {
                encodedQuery.replace("전체", "")
            } else {
                encodedQuery
            }

            return encodedQuery?.let {
                URLDecoder.decode(query, StandardCharsets.UTF_8.toString())
            }
        }
    }
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