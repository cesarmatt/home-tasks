package com.example.todo.ui.home.components.tabs

import androidx.compose.material.Icon
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.runtime.*

@Composable
fun HomeTabComponent(homeTabOptions: List<HomeTabOption>) {
    var tabIndex by remember { mutableStateOf(0) }
    TabRow(selectedTabIndex = tabIndex) {
        homeTabOptions.forEachIndexed { index, homeTabOption ->
            Tab(
                selected = tabIndex == index,
                onClick = {
                    tabIndex = index
                    homeTabOption.onTabClicked?.let { callback ->
                        callback()
                    }
                },
                icon = { Icon(homeTabOption.icon, contentDescription = null) }
            )
        }
    }
}