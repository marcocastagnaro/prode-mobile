package com.example.prode_mobile.leagues

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.prode_mobile.R
import com.example.prode_mobile.reusable.AlertDialogExample

@Composable
fun Leagues() {
    val viewModel = hiltViewModel<LeagueViewModel>()
    val leaguesList by viewModel.leaguesAndSeasonList.collectAsState(initial = emptyList())

    val loading by viewModel.loadingLeagues.collectAsState()
    val showRetry by viewModel.showRetry.collectAsState()

    val allLeaguesList by viewModel.allLeaguesList.collectAsState()
    val unavailableList by viewModel.unavailableLeaguesList.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.mockUnavailableLeagues()
    }
    if (loading) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(dimensionResource(id = R.dimen.loading_box))
                    .align(Alignment.Center),
                color = MaterialTheme.colorScheme.onErrorContainer,
                trackColor = MaterialTheme.colorScheme.onErrorContainer,
            )
        }
    } else if (showRetry) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(id = R.string.retry),
                fontSize = dimensionResource(id = R.dimen.large_font_size).value.sp,
                fontWeight = FontWeight.Bold,
            )
            Text(text = stringResource(id = R.string.retry_load_leagues))
            Button(onClick = { viewModel.retryLoadingLeagues() }) {
                Text(text = stringResource(id = R.string.retry))
            }
        }
    } else {
        Surface (modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            LazyColumn(modifier = Modifier.padding(
                dimensionResource(id = R.dimen.large_padding)
            )) {
                item {
                    Text(
                        text = stringResource(id = R.string.my_leagues), style =
                        TextStyle(fontSize = dimensionResource(id = R.dimen.large_font_size).value.sp, color = MaterialTheme.colorScheme.secondary)
                    )
                }
                item {
                    leaguesList.forEach { league ->
                        val cast_league = LeagueData(league.league_id, league.country_id, league.name, league.active, league.image_path, league.category, league.seasonId, "league", "domestic",  SportData(
                            name = "Football"
                        ))
                        LeagueCard(league = cast_league, true, true, viewModel)
                    }
                }
                item {
                    Divider(
                        modifier = Modifier
                            .padding(
                                dimensionResource(id = R.dimen.default_padding)
                            )
                            .fillMaxWidth()
                    )
                }
                item {
                    Text(
                        text = stringResource(id =R.string.all_leagues),
                        style = TextStyle(fontSize = dimensionResource(id = R.dimen.large_font_size).value.sp, color = MaterialTheme.colorScheme.secondary)
                    )
                    allLeaguesList.forEach { league ->
                        LeagueCard(league = league, false, true, viewModel)
                    }
                }
                item {
                    unavailableList.forEach { league ->
                        LeagueCard(league = league, false, false, viewModel)
                    }
                }
            }
        }
    }
}

@Composable
fun DeleteLeagueAction(
    delLeagueAction: (Int) -> Unit,
    leagueId: Int
) {
    var showAlert by remember {
        mutableStateOf(false)
    }
    IconButton(
        onClick = {
           showAlert = true

        }
    ) {
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = "Del",
            tint = MaterialTheme.colorScheme.tertiary
        )
    }
    if (showAlert) {
        AlertDialogExample(
            onDismissRequest = { showAlert = false },
            onConfirmation = {
                delLeagueAction(leagueId)
                showAlert = false
            },
            dialogTitle = "Delete League",
            dialogText = "Are you sure you want to delete this league?",
            icon = Icons.Default.Delete
        )
    }

}


@Composable
fun AddLeagueAction(
    addLeagueaction : (Int, Int, String, Boolean, String, Int, Int) -> Unit,
    leagueData: LeagueData
) {
    var showAlert by remember {
        mutableStateOf(false)
    }

    IconButton(
        onClick = {
            showAlert = true
        }
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Add",
            tint = MaterialTheme.colorScheme.tertiary
        )
    }
    if (showAlert) {
        AlertDialogExample(
            onDismissRequest = { showAlert = false },
            onConfirmation = {
                addLeagueaction(leagueData.id, leagueData.country_id, leagueData.name, leagueData.active, leagueData.image_path, leagueData.category, leagueData.seasonId)
                showAlert = false
            },
            dialogTitle = "Add League",
            dialogText = "Are you sure you want to add this league?",
            icon = Icons.Default.Info
        )
    }
}
