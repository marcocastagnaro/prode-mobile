package com.example.prode_mobile.pronosticos

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.prode_mobile.R
import com.example.prode_mobile.ui.theme.BlackColor
import com.example.prode_mobile.ui.theme.CorrectResultColor
import com.example.prode_mobile.ui.theme.CorrectWinnerColor
import com.example.prode_mobile.ui.theme.DarkerGreyColor
import com.example.prode_mobile.ui.theme.TitleBlueColor
import com.example.prode_mobile.ui.theme.WrongPrediction
import kotlinx.coroutines.launch

@SuppressLint("StateFlowValueCalledInComposition", "SuspiciousIndentation")
@Composable
fun MatchCard(matchData: MatchCardData, isOpen: Boolean,
              onCardClick: () -> Unit) {
    val viewModel = hiltViewModel<PronosticosViewModel>()
    var scoreTeam1 by remember { mutableStateOf<Int?>(null) }
    var scoreTeam2 by remember { mutableStateOf<Int?>(null) }
    val initialHeight = dimensionResource(id = R.dimen.match_card_initial_height)
    var matchHeight by remember {
        mutableStateOf(initialHeight)
    }
    var isLoading by remember { mutableStateOf(true) }

    val primaryColor = MaterialTheme.colorScheme.primary
    var backgroundColor by remember { mutableStateOf(primaryColor) }

    val localGoals = remember { mutableStateOf(0) }
    val visitorGoals = remember { mutableStateOf(0) }
    LaunchedEffect(matchData.match_id) {
        viewModel.checkIfMatchIsInDB(matchData.match_id)
    }

    LaunchedEffect(matchData.match_id) {
        val prodeResult = viewModel.getProdeResultForMatch(matchData.match_id)
        val fixture = viewModel.scheduleList.value
            .flatMap { it.fixtures }
            .find { it.id == matchData.match_id }
        scoreTeam1 = prodeResult?.localGoals
        scoreTeam2 = prodeResult?.visitorGoals
        val realLocalGoals= fixture?.scores?.getOrNull(0)?.score?.goals
        val realVisitorGoals = fixture?.scores?.getOrNull(1)?.score?.goals

        val winner = if (realLocalGoals != null && realVisitorGoals != null) {
            if (realLocalGoals > realVisitorGoals) "local"
            else if (realLocalGoals < realVisitorGoals) "visitor"
            else "draw"
        } else {
            null
        }
        //Apenas se renderiza la card se setea el color de fondo, no hace falta clickearlo
        if (prodeResult != null) {
            backgroundColor = when {
                winner == prodeResult.winner && realLocalGoals == prodeResult.localGoals && realVisitorGoals == prodeResult.visitorGoals -> CorrectResultColor
                winner == prodeResult.winner -> CorrectWinnerColor
                winner == null -> primaryColor
                else -> WrongPrediction
            }
        }
        else {
            backgroundColor = primaryColor
        }
        isLoading = false
    }

    val cardHeight by animateDpAsState(targetValue = matchHeight)
    Surface(
        modifier = Modifier
            .width(dimensionResource(id = R.dimen.match_card_width))
            .height(cardHeight)
            .clickable { onCardClick() },
        color = backgroundColor,
        shape = MaterialTheme.shapes.medium,
        shadowElevation = dimensionResource(id = R.dimen.shadow_elevation),
    ) {
        matchHeight = dimensionResource(id = R.dimen.match_card_initial_height)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(id = R.dimen.large_padding)),
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(id = R.dimen.default_padding)),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                MatchNameAndImage(
                    urlImage = matchData.urlTeam1,
                    name = matchData.team1,
                    Modifier.fillMaxWidth(0.18f)
                )

                Text(
                    text = matchData.date,
                    style = TextStyle(
                        fontWeight = FontWeight.Black,
                        color = MaterialTheme.colorScheme.secondary,
                        fontSize = dimensionResource(id = R.dimen.medium_font_size).value.sp,
                        fontFamily = FontFamily.Monospace
                    ),
                    modifier = Modifier.align(Alignment.CenterVertically)
                )

                MatchNameAndImage(
                    urlImage = matchData.urlTeam2,
                    name = matchData.team2,
                    Modifier.fillMaxWidth(0.38f)
                )
            }

            Spacer(modifier = Modifier.weight(1f))
            if (isOpen) {
                matchHeight = dimensionResource(id =R.dimen.match_card_expanded_height)
                if (!matchData.is_older && !isLoading) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        TextField(
                            value = scoreTeam1?.toString() ?: "",
                            onValueChange = {
                                scoreTeam1 = it.toIntOrNull()
                            },
                            modifier = Modifier.width(dimensionResource(id = R.dimen.text_field_width)),
                            label = {
                                Text(
                                    text = matchData.team1,
                                    style = TextStyle(fontSize = dimensionResource(id = R.dimen.small_font_size).value.sp)
                                )
                            },
                            placeholder = {
                                Text(
                                    text = "-",
                                    style = TextStyle(fontSize = dimensionResource(id = R.dimen.small_font_size).value.sp)
                                )
                            },
                            isError = scoreTeam1 == null

                        )

                        TextField(
                            value = scoreTeam2?.toString() ?: "",
                            onValueChange = {
                                scoreTeam2 = it.toIntOrNull()
                            },
                            modifier = Modifier.width(dimensionResource(id = R.dimen.text_field_width)),
                            label = {
                                Text(
                                    text = matchData.team2,
                                    style = TextStyle(fontSize = dimensionResource(id = R.dimen.small_font_size).value.sp)
                                )
                            },
                            placeholder = {
                                Text(
                                    text = "-",
                                    style = TextStyle(fontSize = dimensionResource(id = R.dimen.small_font_size).value.sp)
                                )
                            },
                            isError = scoreTeam2 == null
                        )
                    }

                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.default_spacer)))
                    SavePronosticoToDatabase(
                        matchId = matchData.match_id,
                        scoreTeam1 = scoreTeam1,
                        scoreTeam2 = scoreTeam2,
                        viewModel = viewModel
                    )
                } else {

                    if (matchData.is_older && !isLoading) {
                    LaunchedEffect(matchData.match_id) {
                        viewModel.checkIfMatchIsInDB(matchData.match_id)
                    }
                    val fixture = viewModel.scheduleList.value
                        .flatMap { it.fixtures }
                        .find { it.id == matchData.match_id }

                    val score_team_1 = fixture?.scores?.getOrNull(0)?.score
                    val score_team_2 = fixture?.scores?.getOrNull(1)?.score
                        if (viewModel.isMatchInDB.value) {
                            matchHeight = dimensionResource(id = R.dimen.match_card_extra_expanded_height)

                            Column(modifier = Modifier.padding(dimensionResource(id = R.dimen.default_padding)), verticalArrangement = Arrangement.SpaceEvenly) {
                                ShowPredictions(
                                    backgroundColor = backgroundColor,
                                    localGoals = localGoals,
                                    visitorGoals = visitorGoals
                                )
                                if (score_team_1 != null && score_team_2 != null) {
                                    ShowRealResults(
                                        scoreTeam1 = score_team_1,
                                        scoreTeam2 = score_team_2
                                    )
                                }
                            }
                        }
                    else {
                        matchHeight = dimensionResource(id = R.dimen.match_card_expanded_height)
                            if (score_team_1 != null && score_team_2 != null) {
                                ShowRealResults(scoreTeam1 = score_team_1, scoreTeam2 = score_team_2 )
                            }
                    }
                    }
                }
            }
        }
    }
}
@Composable
fun ShowPredictions (backgroundColor: Color, localGoals: MutableState<Int>, visitorGoals: MutableState<Int>) {
    Text(
        text = stringResource(id = R.string.pronostico),
        style = TextStyle(
            fontWeight = FontWeight.Black,
            color = MaterialTheme.colorScheme.secondary,
            fontSize = dimensionResource(id = R.dimen.regular_font_size).value.sp,
            fontFamily = FontFamily.Monospace
        ),    )
    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.small_spacer)))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(backgroundColor),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Box(
                modifier = Modifier
                    .size(dimensionResource(id = R.dimen.march_box_result_size))
                    .border(2.dp, MaterialTheme.colorScheme.tertiary)
                    .padding(dimensionResource(id = R.dimen.default_padding)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = localGoals.value.toString(),
                    style = TextStyle(
                        fontSize = dimensionResource(id = R.dimen.large_font_size).value.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    textAlign = TextAlign.Center
                )
            }
            Box(
                modifier = Modifier
                    .size(dimensionResource(id = R.dimen.march_box_result_size))
                    .border(2.dp, MaterialTheme.colorScheme.tertiary)
                    .padding(dimensionResource(id = R.dimen.default_padding)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = visitorGoals.value.toString(),
                    style = TextStyle(
                        fontSize = dimensionResource(id = R.dimen.large_font_size).value.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    textAlign = TextAlign.Center
                )
            }
        }

}
@Composable
fun ShowRealResults (scoreTeam1: ScoreMatchData, scoreTeam2: ScoreMatchData) {
    Text(
        text = stringResource(id = R.string.resultado),
        style = TextStyle(
            fontWeight = FontWeight.Black,
            color = MaterialTheme.colorScheme.secondary,
            fontSize = dimensionResource(id = R.dimen.regular_font_size).value.sp,
            fontFamily = FontFamily.Monospace
        ),
    )
    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.small_spacer)))

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Box(
            modifier = Modifier
                .size(dimensionResource(id = R.dimen.march_box_result_size))
                .border(2.dp, MaterialTheme.colorScheme.tertiary)
                .padding(dimensionResource(id = R.dimen.default_padding)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = scoreTeam1.goals.toString() ?: "0",
                style = TextStyle(
                    fontSize = dimensionResource(id = R.dimen.large_font_size).value.sp,
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colorScheme.secondary,
                textAlign = TextAlign.Center
            )
        }
        Box(
            modifier = Modifier
                .size(dimensionResource(id = R.dimen.march_box_result_size))
                .border(2.dp, MaterialTheme.colorScheme.tertiary)
                .padding(dimensionResource(id = R.dimen.default_padding)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = scoreTeam2.goals.toString() ?: "0",
                style = TextStyle(
                    fontSize = dimensionResource(id = R.dimen.large_font_size).value.sp,
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colorScheme.secondary,
                textAlign = TextAlign.Center
            )
        }
    }
}
@Composable
fun SavePronosticoToDatabase(
    matchId: Int,
    viewModel: PronosticosViewModel,
    scoreTeam1: Int?,
    scoreTeam2: Int?
) {

    Button(
        onClick = {
            viewModel.savePronostico(matchId, scoreTeam1, scoreTeam2)
        },
        modifier = Modifier.height(dimensionResource(id = R.dimen.save_button_height)),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.tertiary,
            contentColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Text(
            text = stringResource(id = R.string.save_button),
            style = TextStyle(
                fontSize = dimensionResource(id = R.dimen.small_font_size).value.sp
            )
        )
    }
}
