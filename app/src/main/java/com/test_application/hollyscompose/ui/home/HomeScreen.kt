package com.test_application.hollyscompose.ui.home

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.test_application.hollyscompose.HollysDestinations
import com.test_application.hollyscompose.R
import com.test_application.hollyscompose.ui.compose.HollysHomeTopAppBar
import com.test_application.hollyscompose.ui.theme.MainBottomStartRoundShape
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    modifier: Modifier,
    navController: NavHostController,
    scaffoldState: ScaffoldState
) {
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = modifier
    ) {
        HollysHomeTopAppBar {
            coroutineScope.launch {
                scaffoldState.drawerState.open()
            }
        }
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.user_intro_comment),
            style = MaterialTheme.typography.caption,
            textAlign = TextAlign.Center
        )

        HomeTopRoundButtons(navController)
        Spacer(modifier = Modifier.weight(1f))
        HomeIconButtons(navController)
        Spacer(modifier = Modifier.weight(1f))
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .padding(10.dp)
                .clip(RoundedCornerShape(15.dp))
                .background(color = Color.Blue),
            painter = painterResource(id = R.drawable.image_home_ad),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
private fun HomeTopRoundButtons(
    navController: NavHostController
) {
    ConstraintLayout {
        var isVisible by remember { mutableStateOf(false) }
        val (delivery, button, order) = createRefs()

        HomeDropDownButton(
            modifier = Modifier
                .constrainAs(delivery) {
                    top.linkTo(parent.top)
                }
                .zIndex(1.0f)
                .fillMaxWidth()
                .clip(MainBottomStartRoundShape)
                .background(Color.White)
                .clickable { isVisible = isVisible.not() }
                .padding(35.dp, 40.dp, 20.dp, 40.dp),
            title = R.string.delivery_button_title,
            subtitle = R.string.delivery_button_subtitle
        )
        AnimatedVisibility(
            visible = isVisible,
            modifier = Modifier
                .constrainAs(button) {
                    top.linkTo(parent.top)
                }
                .zIndex(0.8f)
                .fillMaxWidth()
                .clip(MainBottomStartRoundShape),
            enter = slideInVertically(initialOffsetY = { -it }),
            exit =  slideOutVertically(targetOffsetY = { -it })
        ) {
            Row(
                modifier = Modifier
                    .background(MaterialTheme.colors.background)
                    .padding(35.dp, 130.dp, 20.dp, 10.dp)
            ) {
                HomeIconButton(
                    modifier = Modifier.weight(1f),
                    image = Icons.Outlined.Edit,
                    name = stringResource(id = R.string.menu_list),
                    navController = navController
                )
                HomeIconButton(
                    modifier = Modifier.weight(1f),
                    image = Icons.Outlined.Place,
                    name = stringResource(id = R.string.choice_store),
                    navController = navController
                )
            }
        }
        HomeDropDownButton(
            modifier = Modifier
                .constrainAs(order) {
                    top.linkTo(parent.top)
                }
                .fillMaxWidth()
                .clip(MainBottomStartRoundShape)
                .background(MaterialTheme.colors.primary)
                .clickable {
                    navController.navigate(HollysDestinations.SMART_ORDER)
                }
                .padding(35.dp, 150.dp, 20.dp, 40.dp),
            mainColor = MaterialTheme.colors.primary,
            subColor = Color.White,
            thirdColor = Color.White,
            title = R.string.smart_order_button_title,
            subtitle = R.string.smart_order_button_subtitle,
            dropdownIcon = Icons.Filled.KeyboardArrowRight
        )
    }
}

@Composable
private fun HomeDropDownButton(
    modifier: Modifier,
    mainColor: Color = MaterialTheme.colors.background,
    subColor: Color = MaterialTheme.colors.surface,
    thirdColor: Color = MaterialTheme.colors.primary,
    @StringRes title: Int,
    @StringRes subtitle: Int,
    dropdownIcon: ImageVector = Icons.Filled.KeyboardArrowDown
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = stringResource(id = title),
                color = subColor,
                style = MaterialTheme.typography.h1
            )
            Text(
                text = stringResource(id = subtitle),
                color = subColor,
                style = MaterialTheme.typography.body1
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Box(
           modifier = Modifier
               .size(30.dp)
               .clip(CircleShape)
               .background(thirdColor)
        ) {
            Icon(
                modifier = Modifier
                    .fillMaxSize(),
                imageVector = dropdownIcon,
                contentDescription = null,
                tint = mainColor
            )
        }
    }
}

@Composable
private fun HomeIconButtons(
    navController: NavHostController
) {
    val modifier = Modifier.fillMaxWidth()

    Row(
        modifier = modifier
            .padding(bottom = 20.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        val buttonModifier = Modifier.weight(1f)

        HomeIconButton(
            modifier = buttonModifier,
            image = Icons.Outlined.Star,
            name = "0/12",
            navController = navController
        )
        HomeIconButton(
            modifier = buttonModifier,
            name = stringResource(id = R.string.hollys_card),
            navController = navController
        )
        HomeIconButton(
            modifier = buttonModifier,
            name = stringResource(id = R.string.coupon),
            navController = navController
        )
    }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center
    ) {
        val buttonModifier = Modifier.weight(1f)

        HomeIconButton(
            modifier = buttonModifier,
            image = Icons.Outlined.ShoppingCart,
            name = stringResource(id = R.string.hollys_mall),
            navController = navController
        )
        HomeIconButton(
            modifier = buttonModifier,
            image = Icons.Outlined.LocationOn,
            name = stringResource(id = R.string.market_place),
            navController = navController
        )
        Box(
            modifier = buttonModifier
                .clip(RoundedCornerShape(topStart = 50.dp, bottomStart = 50.dp))
                .background(MaterialTheme.colors.primary),
        ) {
            HomeIconButton(
                modifier = modifier,
                name = stringResource(id = R.string.cake_reservation),
                textColor = Color.White,
                navController = navController
            )
        }

    }
}

@Composable
private fun HomeIconButton(
    modifier: Modifier,
    image: ImageVector = Icons.Filled.Face,
    name: String,
    textColor: Color = MaterialTheme.colors.surface,
    navController: NavHostController
) {
    val destination = when (name) {
        stringResource(id = R.string.coupon) -> HollysDestinations.COUPON
        else -> HollysDestinations.COUPON
    }

    Column(
        modifier = modifier
            .padding(vertical = 10.dp)
            .clickable {
                navController.navigate(destination)
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .padding(horizontal = 30.dp)
                .size(50.dp),
            imageVector = image,
            contentDescription = null,
            alignment = Alignment.Center,
            contentScale = ContentScale.Fit,
        )
        Text(
            text = name,
            color = textColor,
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
@Preview
private fun HomeDropDownButtonPreView() {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) {
        ConstraintLayout {
            val (delivery, order) = createRefs()

            HomeDropDownButton(
                modifier = Modifier
                    .constrainAs(delivery) {
                        top.linkTo(parent.top)
                    }
                    .zIndex(1.0f),
                title = R.string.delivery_button_title,
                subtitle = R.string.delivery_button_subtitle
            )
            HomeDropDownButton(
                modifier = Modifier.constrainAs(order) {
                    top.linkTo(parent.top)
                },
                mainColor = MaterialTheme.colors.primary,
                subColor = Color.White,
                title = R.string.smart_order_button_title,
                subtitle = R.string.smart_order_button_subtitle
            )
        }
    }
}