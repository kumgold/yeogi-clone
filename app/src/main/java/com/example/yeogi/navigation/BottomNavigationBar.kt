package com.example.yeogi.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.yeogi.ui.theme.Background

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Search,
        BottomNavItem.AroundMe,
        BottomNavItem.Favorites,
        BottomNavItem.MyInfo
    )

    NavigationBar(
        containerColor = Background
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.title) },
                label = { Text(item.title) },
                selected = currentRoute == item.route,
                onClick = {
                    if (item != BottomNavItem.Home) {
                        navController.navigate(item.route)
                    } else {
                        navController.navigate(item.route) {
                            // 백스택에 화면이 쌓이는 것을 방지
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            // 같은 화면을 다시 눌렀을 때 재실행 방지
                            launchSingleTop = true
                            // 이전에 선택했던 화면으로 돌아갈 때 상태 복원
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}