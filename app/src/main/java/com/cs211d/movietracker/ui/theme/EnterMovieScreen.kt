package com.cs211d.movietracker.ui.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cs211d.movietracker.R
import com.cs211d.movietracker.ui.theme.components.FunctionalButton

@Composable
fun EnterMovieScreen(
        movieViewModel: MovieViewModel,
        modifier : Modifier = Modifier) {

    val movieUiState by movieViewModel.uiState.collectAsState()
    val currentMovie = movieViewModel.currentMovie

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = currentMovie,
            shape = MaterialTheme.shapes.large,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                disabledContainerColor = MaterialTheme.colorScheme.surface,
            ),
            onValueChange = {
                movieViewModel.userAddMovieTextField(it)
            },
            label = { Text(stringResource(R.string.movie_field_label)) },
            isError = movieUiState.isError,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            )
        )
        FunctionalButton(
            onClick = {
                movieViewModel.userAddMovieButton()
            },
            text = stringResource(R.string.add_button_text)
        )

        Spacer(modifier = Modifier.height(24.dp))
        LazyColumn {
            items(movieUiState.movieList) { movie ->
                MovieCard(
                    movie = movie
                )
            }
        }
    }
}

@Composable
fun MovieCard(movie: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(text = movie, fontSize = 24.sp)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

