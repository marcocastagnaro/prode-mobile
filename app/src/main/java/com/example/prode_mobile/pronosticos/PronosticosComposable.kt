package com.example.prode_mobile.pronosticos

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.prode_mobile.R
import com.example.prode_mobile.ui.theme.RegularFontSize
import com.example.prode_mobile.ui.theme.TitleDimensions
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun Pronosticos() {
    val viewModel = hiltViewModel<PronosticosViewModel>()
    val loadingRounds by viewModel.loadingRounds.collectAsState()
    val showRetry by viewModel.showRetry.collectAsState()

    val matchesLoading = viewModel.loadingMatches.collectAsState()
    val showMatchesRetry = viewModel.showMatchesRetry.collectAsState()

    val leagues = viewModel.leaguesAndSeasonList.collectAsState(initial = emptyList()).value

    var isLeagueSelected by remember { mutableStateOf(false) }
    var isDateAndLeagueSelected by remember { mutableStateOf(false) }
    var selectedLeague by remember { mutableStateOf<String?>(null) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            PredictionsTitle()

            LeagueSelector(leagues, onLeagueSelected = { league ->
                isLeagueSelected = true
                selectedLeague = league
                viewModel.retryLoadingRounds(league)
            })


            AnimatedVisibility(visible = isLeagueSelected) {
                ShowWhenLeagueIsSelected(loadingRounds, showRetry, viewModel,onDateAndLeagueSelected = { isSelected -> isDateAndLeagueSelected = isSelected }, selectedLeague = selectedLeague)
            }

            AnimatedVisibility(visible = isDateAndLeagueSelected) {
                ShowWhenRoundSelected(matchesLoading = matchesLoading.value, showMatchesRetry = showMatchesRetry.value, viewModel = viewModel , selectedLeague = selectedLeague )
            }
        }
    }
}

@SuppressLint("StateFlowValueCalledInComposition", "SimpleDateFormat")
@Composable
fun ShowWhenRoundSelected(matchesLoading: Boolean, showMatchesRetry: Boolean, viewModel: PronosticosViewModel, selectedLeague: String?) {
    var selectedMatchId by remember { mutableStateOf<Int?>(null) }

    if (matchesLoading) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(dimensionResource(id = R.dimen.loading_box))
                    .align(Alignment.Center),
                color = MaterialTheme.colorScheme.onErrorContainer,
                trackColor = MaterialTheme.colorScheme.onErrorContainer,
            )
        }
    } else if (showMatchesRetry) {
        Column(
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.vertical_arrangement), Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(id = R.string.retry),
                fontSize = RegularFontSize,
                fontWeight = FontWeight.Bold,
            )
            Text(text = stringResource(id = R.string.retry_load_matches))
            Button(onClick = { selectedLeague?.let { viewModel.retryLoadingMatches(league = it) } }) {
                Text(text = stringResource(id = R.string.retry))
            }
        }
    } else {
        if (viewModel.scheduleList.value.isEmpty()) {
            viewModel.retryLoadingMatches(league = selectedLeague!!)
        }
        val roundsData by viewModel.scheduleList.collectAsState()
        if (roundsData.isNotEmpty()) {
            val matches = roundsData[0].fixtures.map { fixture ->
                val team1 = fixture.participants[0]
                val team2 = fixture.participants[1]
                SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(fixture.starting_at)?.let {
                    MatchCardData(
                        fixture.id,
                        team1.name,
                        team2.name,
                        fixture.starting_at,
                        team1.image_path,
                        team2.image_path,
                        fixture.round_id,
                        is_older = it.before(Date())
                    )
                }
            }

            Column(modifier = Modifier.padding(dimensionResource(id = R.dimen.large_padding))) {
                matches.forEach { match ->
                    if (match != null) {
                        MatchCard(
                            matchData = match,
                            isOpen = match.match_id == selectedMatchId,
                            onCardClick = {
                                selectedMatchId = if (selectedMatchId == match.match_id) null else match.match_id
                            },
                        )
                    }
                    Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.larger_spacer)))
                }
            }
        }
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun ShowWhenLeagueIsSelected(loadingRounds : Boolean, showRetry : Boolean, viewModel : PronosticosViewModel, onDateAndLeagueSelected: (Boolean) -> Unit, selectedLeague : String?) {

    val rounds by viewModel.roundsList.collectAsState()

    if (loadingRounds) {
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
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.vertical_arrangement), Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(id = R.string.retry),
                fontSize = RegularFontSize,
                fontWeight = FontWeight.Bold,
            )
            Text(text = stringResource(id = R.string.retry_load_rounds))
            Button(onClick = { selectedLeague?.let { viewModel.retryLoadingRounds(league = it) } }) {
                Text(text = stringResource(id = R.string.retry))
            }
        }
    } else if (rounds.isNotEmpty()) {
        val listRoundNumber = viewModel.roundsList.value.map { round -> round.name.toInt() }
        val fechas = listRoundNumber.map { FechaSelector(nroFecha = it) }

        DateSelector(
            dates = fechas,
            isDateAndLeagueSelected = { onDateAndLeagueSelected(true) },
            retryMatches = { viewModel.retryLoadingMatches(league = selectedLeague!!) }
        )
    }
}

@Preview
@Composable
fun PreviewPronostico() {
    Pronosticos()
}