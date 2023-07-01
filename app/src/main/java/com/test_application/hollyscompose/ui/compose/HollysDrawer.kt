package com.test_application.hollyscompose.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.test_application.hollyscompose.ui.theme.HollysTypography
import kotlinx.coroutines.launch

@Composable
fun HollysDrawer(
    modifier: Modifier,
    navController: NavHostController,
    scaffoldState: ScaffoldState
) {
    Column(
        modifier = modifier
            .background(MaterialTheme.colors.background),
    ) {
        val contentsModifier = Modifier.fillMaxWidth()

        TopIconButtons(
            modifier = contentsModifier,
            navController = navController,
            scaffoldState = scaffoldState
        )

        Text(
            modifier = contentsModifier
                .align(Alignment.Start)
                .wrapContentSize(
                    align = Alignment.CenterStart
                )
                .padding(start = 30.dp, bottom = 10.dp)
                .clickable { },
            text = "User1 님",
            style = HollysTypography.h1
        )
        Text(
            modifier = contentsModifier
                .align(Alignment.Start)
                .padding(start = 30.dp, bottom = 20.dp),
            text = "소중한 RED 멤버입니다.\nFREE쿠폰으로 다양한 할리스를 즐겨보세요.",
            style = HollysTypography.body1
        )

        DrawerRowCardButtons(
            modifier = contentsModifier
        )
    }
}

@Composable
private fun TopIconButtons(
    modifier: Modifier,
    navController: NavHostController,
    scaffoldState: ScaffoldState
) {
    val scope = rememberCoroutineScope()

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.End
    ) {
        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                imageVector = Icons.Outlined.Notifications,
                contentDescription = null
            )
        }
        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                imageVector = Icons.Outlined.Settings,
                contentDescription = null
            )
        }
        IconButton(onClick = {
            scope.launch { scaffoldState.drawerState.close() }
        }) {
            Icon(
                imageVector = Icons.Outlined.Clear,
                contentDescription = null
            )
        }
    }
}

@Composable
private fun DrawerRowCardButtons(
    modifier: Modifier
) {
    Row(
        modifier = modifier
    ) {
        Card() {
            
        }
    }
}

@Preview
@Composable
private fun HollysDrawerPreview() {
    HollysDrawer(
        modifier = Modifier.fillMaxSize(),
        navController = rememberNavController(),
        scaffoldState = rememberScaffoldState()
    )
}