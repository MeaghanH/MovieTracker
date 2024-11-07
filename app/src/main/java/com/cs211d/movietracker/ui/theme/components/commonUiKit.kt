package com.cs211d.movietracker.ui.theme.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cs211d.movietracker.ui.theme.theme.md_theme_light_surfaceTint

@Composable
fun NavigationButton(
    text: String,
    onClick : () -> Unit,
    modifier : Modifier = Modifier
) {

    Button(
        onClick = onClick,
        modifier = Modifier
            .padding(bottom = 24.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = md_theme_light_surfaceTint
        )
    ) {
        Text(
            text = text,
            fontSize = 24.sp
        )
    }
}

@Composable
fun FunctionalButton(
    text: String,
    onClick : () -> Unit,
    modifier : Modifier = Modifier
) {

    Button(
        onClick = onClick,
        modifier = Modifier
            .padding(bottom = 24.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = md_theme_light_surfaceTint
        )
    ) {
        Text(
            text = text,
            fontSize = 24.sp
        )
    }
}
