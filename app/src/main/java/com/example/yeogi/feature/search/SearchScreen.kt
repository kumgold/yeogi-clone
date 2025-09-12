package com.example.yeogi.feature.search

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.yeogi.SystemBarColor
import com.example.yeogi.feature.search.view.DomesticAccommodationContent
import com.example.yeogi.feature.search.view.FlightContent
import com.example.yeogi.feature.search.view.LeisureTicketContent
import com.example.yeogi.feature.search.view.OverseasAccommodationContent
import com.example.yeogi.navigation.NavItem
import com.example.yeogi.ui.theme.YeogiTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navController: NavController) {
    val viewModel = viewModel<SearchViewModel>()
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf("국내숙소", "해외숙소", "항공", "레저·티켓")

    SystemBarColor(color = White)

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("검색", style = MaterialTheme.typography.bodyMedium) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "뒤로가기")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = White
                )
            )
        },
        containerColor = White
    ) { scaffoldPadding ->
        Column(modifier = Modifier.padding(scaffoldPadding)) {
            CustomSearchTabs(
                tabs = tabs,
                selectedTabIndex = selectedTabIndex,
                onTabClick = { selectedTabIndex = it }
            )

            AnimatedContent(
                targetState = selectedTabIndex,
                transitionSpec = {
                    if (targetState > initialState) {
                        slideInHorizontally { fullWidth -> fullWidth } togetherWith
                                slideOutHorizontally { fullWidth -> -fullWidth }
                    } else {
                        slideInHorizontally { fullWidth -> -fullWidth } togetherWith
                                slideOutHorizontally { fullWidth -> fullWidth }
                    }
                }
            ) { targetIndex ->
                when (targetIndex) {
                    0 -> DomesticAccommodationContent(
                        viewModel = viewModel,
                        navigateToDetail = {
                            navController.navigate(NavItem.SearchDetail.route)
                        }
                    )
                    1 -> OverseasAccommodationContent()
                    2 -> FlightContent()
                    3 -> LeisureTicketContent()
                }
            }
        }
    }
}

@Composable
fun CustomSearchTabs(tabs: List<String>, selectedTabIndex: Int, onTabClick: (Int) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .background(
                MaterialTheme.colorScheme.background,
                shape = RoundedCornerShape(50)
            )
            .clip(RoundedCornerShape(50)),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        tabs.forEachIndexed { index, title ->
            Box(
                modifier = Modifier
                    .padding(6.dp)
                    .weight(1f)
                    .clip(RoundedCornerShape(50))
                    .then(
                        if (selectedTabIndex == index) {
                            Modifier.background(White, shape = RoundedCornerShape(50))
                        } else {
                            Modifier
                        }
                    )
                    .clickable { onTabClick(index) }
                    .padding(vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = title,
                    color = if (selectedTabIndex == index) MaterialTheme.colorScheme.primary else Color.Gray,
                    fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Normal,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchScreenPreview() {
    YeogiTheme {
        SearchScreen(navController = rememberNavController())
    }
}