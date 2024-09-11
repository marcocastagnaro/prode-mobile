package com.example.prode_mobile.pronosticos

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
//import androidx.compose.material.icons.filled.

import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.prode_mobile.R
import com.example.prode_mobile.leagues.LeagueViewModel
import com.example.prode_mobile.ui.theme.BlueButton
import com.example.prode_mobile.ui.theme.DarkBackground
import com.example.prode_mobile.ui.theme.PurpleGrey80
import com.example.prode_mobile.ui.theme.TitleColor

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun Pronosticos() {
    val viewModel = hiltViewModel<PronosticosViewModel>()
    val rounds by viewModel.roundsList.collectAsState()
    val loading by viewModel.loadingRounds.collectAsState()
    val showRetry by viewModel.showRetry.collectAsState()

    val matchesLoading = viewModel.loadingMatches.collectAsState()
    val showMatchesRetry = viewModel.showMatchesRetry.collectAsState()

    val listRoundNumber = rounds.map { round -> round.name.toInt() }
    val leagues = listOf<LeagueSelector>(
        LeagueSelector(league = "Choose Your League", onClick= {}),
        LeagueSelector(league = "Liga Argentina", onClick = {}),
        LeagueSelector(league = "Liga Brasilera", onClick = {}),
        LeagueSelector(league = "Liga Italiana", onClick = {}),
        LeagueSelector(league = "Liga Alemana", onClick = {}),
        )
    val fechas = listRoundNumber.map { FechaSelector(nroFecha = it) }

    val matches = remember { mutableListOf<MatchCardData>() }  // Usar remember para mantener el estado de la lista

    var isLeagueSelected by remember {
        mutableStateOf(false)
    }
    var isDateAndLeagueSelected by remember {
        mutableStateOf(false)
    }
    if (loading) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(64.dp)
                    .align(Alignment.Center),
                color = PurpleGrey80,
                trackColor = PurpleGrey80,
            )
        }
    } else if (showRetry) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(id = R.string.retry),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
            )
            Text(text = stringResource(id = R.string.retry_load_leagues))
            Button(onClick = { viewModel.retryLoadingRounds() }) {
                Text(text = stringResource(id = R.string.retry))
            }
        }
    } else {
        Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = DarkBackground,
    ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(
                        rememberScrollState()
                    )
            ) {
                SelectYourLeagueTitle()
                LeagueSelector(leagues, onLeagueSelected = { isLeagueSelected = true })

                AnimatedVisibility(visible = isLeagueSelected) {
                    DateSelector(
                        dates = fechas,
                        isDateAndLeagueSelected = { isDateAndLeagueSelected = true })
                }
                AnimatedVisibility(visible = isDateAndLeagueSelected) {
                    if (matchesLoading.value) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .size(64.dp)
                                    .align(Alignment.Center),
                                color = PurpleGrey80,
                                trackColor = PurpleGrey80,
                            )
                        }
                    } else if (showMatchesRetry.value) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(
                                10.dp,
                                Alignment.CenterVertically
                            ),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Text(
                                text = stringResource(id = R.string.retry),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                            )
                            Text(text = stringResource(id = R.string.retry_load_leagues))
                            Button(onClick = { viewModel.retryLoadingMatches("1") }) {
                                Text(text = stringResource(id = R.string.retry))
                            }
                        }
                    } else {
                        if (viewModel.scheduleList.value.isEmpty()) {
                            viewModel.retryLoadingMatches("1")
                        }
                        val roundsData by viewModel.scheduleList.collectAsState()
                        Log.i("Pronosticos", "Rounds: $roundsData")
                        if(roundsData.isNotEmpty()) {
                            roundsData[0].fixtures.forEach { fixture ->
                                val team1 = fixture.participants[0]
                                val team2 = fixture.participants[1]
                                val date = fixture.starting_at
                                val urlTeam1 = team1.image_path
                                val urlTeam2 = team2.image_path
                                val nroFecha = fixture.round_id

                                matches.add(
                                    MatchCardData(
                                        team1.name,
                                        team2.name,
                                        date,
                                        urlTeam1,
                                        urlTeam2,
                                        nroFecha
                                    )
                                )
                            }
                        }

                        Column(modifier = Modifier.padding(16.dp)) {
                            matches.forEach { part ->
                                MatchCard(
                                    MatchCardData(
                                        team1 = part.team1,
                                        team2 = part.team2,
                                        part.date,
                                        part.urlTeam1,
                                        part.urlTeam2, 1
                                    )
                                ) {}
                                Spacer(modifier = Modifier.size(24.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}




@Composable
fun SelectYourLeagueTitle () {
    Surface(modifier = Modifier
        .fillMaxWidth()
        .height(140.dp), color = BlueButton,
    ) {
        Column (horizontalAlignment = Alignment.CenterHorizontally){
            Text(
                text = stringResource(id = R.string.pronsoticos_title), style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    color = TitleColor,
                    fontSize = 32.sp,
                    fontFamily = FontFamily.SansSerif
                ), modifier = Modifier.padding(16.dp)
            )
            Text(
                text = stringResource(id = R.string.pronsoticos_subtitle), style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    color = TitleColor,
                    fontSize = 16.sp,
                    fontFamily = FontFamily.SansSerif
                ), modifier = Modifier.padding(16.dp)
            )
        }
    }
}


data class LeagueSelector(
    val league : String,
    val onClick : () -> Unit
)
data class FechaSelector (
    val nroFecha: Int,
)

data class MatchCardData (
    val team1: String,
    val team2: String,
    val date: String,
    val urlTeam1: String,
    val urlTeam2: String,
    val nroFecha: Int,
)
@Preview
@Composable
fun PreviewPronostico() {
    Pronosticos()
}