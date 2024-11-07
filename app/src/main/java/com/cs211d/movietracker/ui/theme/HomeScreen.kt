package com.cs211d.movietracker.ui.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cs211d.movietracker.R
import com.cs211d.movietracker.ui.theme.components.NavigationButton

@Preview(showBackground = true)
@Composable
fun HomeScreen(
    onEnterMovieClick : () -> Unit = {},
    onMovieRecClick : () -> Unit = {}
) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        Text(
            text = stringResource(R.string.welcome),
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(vertical = 16.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = stringResource(R.string.welcome_instructions_text),
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(24.dp))
        NavigationButton(
            text = stringResource(R.string.enter_movies_button_text),
            onClick = onEnterMovieClick
        )
        NavigationButton(
        text = stringResource(R.string.recommend_movies_button_text),
        onClick = onMovieRecClick
        )
    }
}
