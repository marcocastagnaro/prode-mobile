package com.example.prode_mobile.score

import Reference
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.prode_mobile.R
import com.example.prode_mobile.ui.theme.CorrectResultColor
import com.example.prode_mobile.ui.theme.CorrectWinnerColor
import com.example.prode_mobile.ui.theme.SubtitleFontSize
import com.example.prode_mobile.ui.theme.TitleDimensions
import com.example.prode_mobile.ui.theme.WrongPrediction

@Composable
fun Score(score: State<Int>, exactScore: State<Int> , midScore: State<Int>, wrongScore: State<Int>) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                dimensionResource(id = R.dimen.small_padding)
            ),
        color = MaterialTheme.colorScheme.primary,
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corner_shape)),
        shadowElevation = dimensionResource(id = R.dimen.shadow_elevation_score)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.larger_padding))
        ) {
            Text(
                text = stringResource(id = R.string.my_score),
                style = TextStyle(
                    fontSize = SubtitleFontSize,
                    color = MaterialTheme.colorScheme.secondary
                ),
                modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.large_padding))
            )
            Card(
                shape = RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corner_shape)),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
                modifier = Modifier
                    .border(
                        dimensionResource(id = R.dimen.border_weight),
                        MaterialTheme.colorScheme.tertiary,
                        shape = RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corner_shape))
                    )
                    .fillMaxWidth(0.5f),

            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimensionResource(id = R.dimen.default_padding))
                ) {
                    Text(
                        text = score.value.toString(),
                        style = TextStyle(
                            fontSize = TitleDimensions,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.vertical_arrangement)))
            Row(
                horizontalArrangement = Arrangement.spacedBy(
                    dimensionResource(id =R.dimen.default_padding),
                    Alignment.CenterHorizontally
                )
            ) {
                SmallScoreBox(exactScore.value, exactScore.value * 3, CorrectResultColor)
                SmallScoreBox(midScore.value, midScore.value , CorrectWinnerColor)
                SmallScoreBox(wrongScore.value, 0, WrongPrediction)

            }
        }

    }
}

@Preview
@Composable
fun ScorePreview() {
    val score = remember { mutableStateOf(100) }
    Score(score = score, exactScore = score, midScore = score, wrongScore = score)
}

