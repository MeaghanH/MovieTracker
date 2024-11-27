package com.cs211d.movietracker.ui

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cs211d.movietracker.R
import com.cs211d.movietracker.ui.theme.components.FunctionalButton
import com.cs211d.movietracker.ui.theme.theme.md_theme_light_surfaceTint

@Preview(showBackground = true)
@Composable
fun MovieRecScreen(
    movieViewModel: MovieViewModel = viewModel(),
    recViewModel: RecViewModel = viewModel()
    )
{
    val movieUiState by movieViewModel.movieUiState.collectAsState()
    var recommendedMovie by remember { mutableStateOf("") }
    val onlineMovieUiState by recViewModel.onlineMovieUiState.collectAsState()
    var onlineMovieInfo by remember { mutableStateOf<Unit>(Unit) }

    LaunchedEffect(Unit) {
        recViewModel.getOnlineMovieData()
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())
        .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = stringResource(R.string.recs_to_choose_from_text, movieUiState.movieList.size),
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(24.dp))

        FunctionalButton(
            onClick = {
                    recommendedMovie = movieUiState.movieList.random().movieName
                    recViewModel.updateRecommendMovie(recommendedMovie)

                onlineMovieInfo = recViewModel.getOnlineMovieData()
            },
            text = stringResource(R.string.make_a_rec_button),
        )
        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = stringResource(R.string.recommended_movie_text),
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = if(movieUiState.movieList.isEmpty()) {
                stringResource(R.string.recommended_movie_text)
            }else {
                recViewModel.getRecommendedMovie()
            },
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        when(recViewModel.onlineDataStatusState) {
            OnlineDataStatusState.SUCCESS -> {
                onlineMovieUiState.onlineMovieData?.imdbID?.let {
                    Text(
                        text = stringResource(R.string.online_imdbID_text, it),
                        style = MaterialTheme.typography.displaySmall,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally),
                        textAlign = TextAlign.Center
                    )
                }
                onlineMovieUiState.onlineMovieData?.released?.let {
                    Text(
                        text = stringResource(R.string.online_released_text, it),
                        style = MaterialTheme.typography.displaySmall,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally),
                        textAlign = TextAlign.Center
                    )
                }
                onlineMovieUiState.onlineMovieData?.director?.let {
                    Text(
                        text = stringResource(R.string.online_director_text, it),
                        style = MaterialTheme.typography.displaySmall,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally),
                        textAlign = TextAlign.Center
                    )
                }
            }

            OnlineDataStatusState.ERROR -> {
                Text(
                    text = stringResource(R.string.error_text),
                    style = MaterialTheme.typography.displaySmall,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally),
                    textAlign = TextAlign.Center
                )
            }

            OnlineDataStatusState.LOADING -> {
                Text(
                    text = stringResource(R.string.loading_text),
                    style = MaterialTheme.typography.displaySmall,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally),
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        val context = LocalContext.current
        Button(
            onClick = { shareMovie(context,recViewModel.getRecommendedMovie())},
            colors = ButtonDefaults.buttonColors(
                containerColor = md_theme_light_surfaceTint)
        ) {
            Icon(Icons.Default.Share, null)
        }

    }
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

