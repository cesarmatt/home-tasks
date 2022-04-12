package com.example.todo.ui.home.components.topbar

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.todo.ui.theme.dimen2
import com.example.todo.ui.theme.noElevation

@Composable
fun HomeTopBarComponent() {
    TopAppBar(
        backgroundColor = MaterialTheme.colors.background,
        elevation = noElevation
    ) {
        Text(
            "Tasks",
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(horizontal = dimen2),
            textAlign = TextAlign.Start
        )
    }
}

@Preview
@Composable
fun HomeTopBarPreview() {
    HomeTopBarComponent()
}