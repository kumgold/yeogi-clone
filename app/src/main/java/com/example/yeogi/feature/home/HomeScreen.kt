package com.example.yeogi.feature.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.yeogi.SystemBarColor
import com.example.yeogi.data.model.ServiceCategory
import com.example.yeogi.navigation.BottomNavigationBar
import com.example.yeogi.navigation.NavItem
import com.example.yeogi.shared.RecommendationSection
import com.example.yeogi.ui.theme.YeogiTheme


@Composable
fun HomeScreen(navController: NavController) {
    val viewModel = viewModel<HomeViewModel>()

    SystemBarColor(color = MaterialTheme.colorScheme.background,)

    Scaffold(
        bottomBar = { BottomNavigationBar(navController = navController) }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background,),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item { HomeHeader() }

            item {
                ServiceCategorySection(
                    serviceCategoryList = viewModel.serviceCategoryList
                )
            }

            item {
                RecommendationSection(
                    title = "우리 동네 BEST",
                    accommodations = viewModel.accommodationList.subList(0, 5),
                    onItemClick = { id ->
                        navController.navigate(NavItem.AccommodationDetail.createRoute(id))
                    }
                )
            }

            item {
                RecommendationSection(
                    title = "이번 주 특가",
                    accommodations = viewModel.accommodationList.subList(6, 10),
                    onItemClick = { id ->
                        navController.navigate(NavItem.AccommodationDetail.createRoute(id))
                    }
                )
            }
            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}

@Composable
fun HomeHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background,)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "여기어때.", fontSize = 22.sp, fontWeight = FontWeight.ExtraBold, color = MaterialTheme.colorScheme.primary)
        TextButton(
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary
            ),
            onClick = {}
        ) {
            Text("로그인 / 회원가입")
        }
    }
}

@Composable
fun ServiceCategorySection(
    serviceCategoryList: List<ServiceCategory>
) {
    val categories = remember { serviceCategoryList }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(vertical = 16.dp)) {
            categories.chunked(4).forEach { rowItems ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    rowItems.forEach { category ->
                        CategoryIcon(
                            modifier = Modifier.weight(1f),
                            category = category
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryIcon(
    modifier: Modifier = Modifier,
    category: ServiceCategory
) {
    Column(
        modifier = modifier.clickable { /* 각 카테고리 화면으로 이동 */ },
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            category.icon,
            contentDescription = category.name,
            modifier = Modifier.size(28.dp),
            tint = Color(0xFF333333)
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(text = category.name, fontSize = 12.sp, color = Color.DarkGray)
    }
}


@Preview(showBackground = true, backgroundColor = 0xFFF5F5F5)
@Composable
fun HomeScreenPreview() {
    YeogiTheme {
        HomeScreen(navController = rememberNavController())
    }
}