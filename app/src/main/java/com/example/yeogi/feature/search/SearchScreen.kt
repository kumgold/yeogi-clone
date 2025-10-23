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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.yeogi.SystemBarColor
import com.example.yeogi.feature.search.ui.DomesticAccommodationContent
import com.example.yeogi.feature.search.ui.FlightContent
import com.example.yeogi.feature.search.ui.LeisureTicketContent
import com.example.yeogi.feature.search.ui.OverseasAccommodationContent
import com.example.yeogi.navigation.NavItem
import com.example.yeogi.ui.theme.YeogiTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var selectedTabIndex by remember { mutableIntStateOf(uiState.selectedIndex) }
    val tabs = listOf("국내숙소", "해외숙소", "항공", "레저·티켓")

    SystemBarColor(color = MaterialTheme.colorScheme.background)

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "검색",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "뒤로가기",
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
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
                        navigateToDetail = { query ->
                            navController.navigate(NavItem.DomesticSearchDetail.createRoute(query))
                        }
                    )
                    1 -> OverseasAccommodationContent(
                        navigateToDetail = { query ->
                            navController.navigate(NavItem.OverseaSearchDetail.createRoute(query))
                        }
                    )
                    2 -> FlightContent(
                        navigateToDetail = {
                            navController.navigate(NavItem.FlightSearchDetail.route)
                        }
                    )
                    3 -> LeisureTicketContent()
                }

                viewModel.onTabSelected(targetIndex)
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
                            Modifier.background(
                                color = MaterialTheme.colorScheme.primary,
                                shape = RoundedCornerShape(50)
                            )
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
                    color = if (selectedTabIndex == index) MaterialTheme.colorScheme.onPrimary
                            else MaterialTheme.colorScheme.onBackground,
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