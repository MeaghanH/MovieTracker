package com.cs211d.movietracker.ui.theme

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cs211d.movietracker.R
import com.cs211d.movietracker.data.MovieUiState
import com.cs211d.movietracker.ui.theme.components.FunctionalButton
import com.cs211d.movietracker.ui.theme.theme.md_theme_light_surfaceTint

@Preview(showBackground = true)
@Composable
fun MovieRecScreen(
    movieViewModel: MovieViewModel = viewModel()
    )
{
    val movieUiState by movieViewModel.uiState.collectAsState()
    val defaultRecommendedMovie = decideDefaultText(movieUiState)
    var recommendedMovie by remember { mutableStateOf(defaultRecommendedMovie) }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = stringResource(R.string.recs_to_choose_from_text, movieUiState.movieList.size),
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(24.dp))

        FunctionalButton(
            onClick = {
                recommendedMovie = movieUiState.movieList.random()
                movieViewModel.updateRecommendMovie(recommendedMovie)
            },
            text = stringResource(R.string.make_a_rec_button)
        )
        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = stringResource(R.string.recommended_movie_text),
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = movieViewModel.currentMovie,
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(24.dp))

        val context = LocalContext.current
        Button(
            onClick = { shareMovie(context,recommendedMovie) },
            colors = ButtonDefaults.buttonColors(
                containerColor = md_theme_light_surfaceTint)
        ) {
            Icon(Icons.Default.Share, null)
        }
    }
}

@Composable
fun decideDefaultText(movieUiState: MovieUiState): String {
    var defaultRecommendedMovie = ""
    if(movieUiState.movieList.isEmpty()) {
        defaultRecommendedMovie = stringResource(R.string.recommended_movie_name_text)
    }
    return defaultRecommendedMovie
}

fun shareMovie(context: Context, recommendedMovie : String) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, "Check out this movie")
        putExtra(
            Intent.EXTRA_TEXT, "Check out this movie!\n" +
                    recommendedMovie
        )
    }
    context.startActivity(Intent.createChooser(intent, "Share Movie"))

}

