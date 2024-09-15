package com.example.prode_mobile.pronosticos

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.prode_mobile.ui.theme.BlueButton
import com.example.prode_mobile.ui.theme.TitleColor

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun MatchCard(matchData: MatchCardData, isCardSelected : () -> Unit) {
    var scoreTeam1 by remember { mutableStateOf(0) }
    var scoreTeam2 by remember { mutableStateOf(0) }
    var isMatchCardClickeable by remember {
        mutableStateOf(false)
    }
    val viewModel = hiltViewModel<PronosticosViewModel>()

    val cardHeight by animateDpAsState(targetValue = if (isMatchCardClickeable) 220.dp else 100.dp)
    Surface(
        modifier = Modifier
            .width(400.dp)
            .height(cardHeight)
            .clickable { isMatchCardClickeable = !isMatchCardClickeable },
        color = BlueButton,
        shape = MaterialTheme.shapes.medium,
        shadowElevation = 8.dp,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                MatchNameAndImage(urlImage = matchData.urlTeam1, name = matchData.team1, Modifier.fillMaxWidth(0.18f))

                Text(
                    text = matchData.date,
                    style = TextStyle(
                        fontWeight = FontWeight.Black,
                        color = TitleColor,
                        fontSize = 12.sp,
                        fontFamily = FontFamily.Monospace
                    ),
                    modifier = Modifier.align(Alignment.CenterVertically)
                )

                MatchNameAndImage(urlImage = matchData.urlTeam2, name =matchData.team2, Modifier.fillMaxWidth(0.38f))
            }

            Spacer(modifier = Modifier.weight(1f))
            if (isMatchCardClickeable) {
                if (!matchData.is_older) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        TextField(
                            value = scoreTeam1.toString(),
                            onValueChange = { scoreTeam1 = it.toIntOrNull() ?: 0 },
                            modifier = Modifier.width(90.dp),
                            label = {
                                Text(
                                    text = matchData.team1,
                                    style = TextStyle(fontSize = 8.sp)
                                )
                            }
                        )
                        TextField(
                            value = scoreTeam2.toString(),
                            onValueChange = { scoreTeam2 = it.toIntOrNull() ?: 0 },
                            modifier = Modifier.width(90.dp),
                            label = {
                                Text(
                                    text = matchData.team2,
                                    style = TextStyle(fontSize = 8.sp)
                                )
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(onClick = { /* Save action */ }, modifier = Modifier.height(24.dp)) {
                        Text(text = "Save", style = TextStyle(fontSize = 8.sp))
                    }
                } else {
                    val fixture = viewModel.scheduleList.value
                        .flatMap { it.fixtures }
                        .find { it.id == matchData.match_id }

                    val team_1 = fixture?.participants?.getOrNull(0)
                    val team_2 = fixture?.participants?.getOrNull(1)

                    val score_team_1 = fixture?.scores?.getOrNull(0)?.score
                    val score_team_2 = fixture?.scores?.getOrNull(1)?.score

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Box(
                            modifier = Modifier
                                .size(60.dp)
                                .border(2.dp, Color.Black)
                                .padding(8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = score_team_1?.goals?.toString() ?: "0",
                                style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
                                textAlign = TextAlign.Center
                            )
                        }
                        Box(
                            modifier = Modifier
                                .size(60.dp)
                                .border(2.dp, Color.Black)
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = score_team_2?.goals?.toString() ?: "0",
                                style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewCard() {
    MatchCard( MatchCardData(
        2,
        "Manchester City",
        "Chelsea",
        "30-oct",
        "https://cdn.sportmonks.com/images/soccer/teams/6/390.png",
        "https://cdn.sportmonks.com/images/soccer/teams/6/390.png", 1, true),
        {}
    )
}