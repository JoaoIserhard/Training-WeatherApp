package com.example.weeatherapp.widgets


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherAppBar(
    title: String = "Title",
    icon: ImageVector? = null,
    isMainScreen: Boolean = true,
    navController: NavController,
    elevation: Dp = 0.dp,
    onAddActionsClicked: () -> Unit = {},
    onButtonClicked: () -> Unit = {}
) {
    CenterAlignedTopAppBar(
        title = {
            Text(text = title,
                style = MaterialTheme.typography.titleLarge,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
                )
        },
        actions = {
            if (isMainScreen) {
                IconButton(onClick = {}) {
                    Icon(imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon")
                }
                IconButton(onClick = {}) {
                    Icon(imageVector = Icons.Rounded.Menu,
                        contentDescription = "More Menu Icon")
                }
            } else {
                Box {}
            }
        },
        navigationIcon = {
            if (icon != null) {
                Icon(imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.clickable{
                        onButtonClicked.invoke()
                    })
            }

        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.Transparent
        ),
        windowInsets = WindowInsets(0,0,0,0)
    )

}