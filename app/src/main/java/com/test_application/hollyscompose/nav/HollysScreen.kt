package com.test_application.hollyscompose.nav

sealed class HollysScreen(val route: String) {
    object Home: HollysScreen(route = HollysDestinations.Home)
}

object HollysDestinations {
    const val Home = "Home"
}