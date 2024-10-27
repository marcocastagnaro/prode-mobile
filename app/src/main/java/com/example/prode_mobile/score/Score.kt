package com.example.prode_mobile.score

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.prode_mobile.R
import com.example.prode_mobile.ui.theme.BlackColor
import com.example.prode_mobile.ui.theme.DarkerGreyColor
import com.example.prode_mobile.ui.theme.GreyBackground
import com.example.prode_mobile.ui.theme.WhiteColor
@Composable
fun Score(score: State<Int>, exactScore: State<Int> , midScore: State<Int>, wrongScore: State<Int>) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                dimensionResource(id = R.dimen.large_padding)
            )
            .border(
                2.dp,
                color = MaterialTheme.colorScheme.tertiary,
                shape = RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corner_shape))
            )
            .background(MaterialTheme.colorScheme.primary),
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
                    fontSize = dimensionResource(id = R.dimen.my_score_font_size).value.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.tertiary
                ),
                modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.large_padding))
            )
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .border(
                        3.dp,
                        MaterialTheme.colorScheme.tertiary,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .background(MaterialTheme.colorScheme.tertiary)
                    .padding(dimensionResource(id = R.dimen.large_padding))
                    .fillMaxWidth(0.5f)
            ) {
                Text(
                    text = score.value.toString(),
                    style = TextStyle(
                        fontSize = dimensionResource(id = R.dimen.title_font_size).value.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.surface
                    )
                )
            }

            Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.large_padding)))
            Text(
                text = stringResource(id = R.string.wrong_score) + " " + wrongScore.value.toString(),
                style = TextStyle(
                    fontSize = dimensionResource(id = R.dimen.my_score_font_size).value.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.tertiary
                ),
                modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.large_padding))
            )
            Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.large_padding)))
            Text(
                text = stringResource(id = R.string.exact_score) + " " + exactScore.value.toString(),
                style = TextStyle(
                    fontSize = dimensionResource(id = R.dimen.my_score_font_size).value.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.tertiary
                ),
                modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.large_padding))
            )
            Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.large_padding)))
            Text(
                text = stringResource(id = R.string.mid_score) + " " + midScore.value.toString(),
                style = TextStyle(
                    fontSize = dimensionResource(id = R.dimen.my_score_font_size).value.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.tertiary
                ),
                modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.large_padding))
            )

        }

    }
}

@Preview
@Composable
fun ScorePreview() {
    val score = remember { mutableStateOf(100) }
    Score(score = score, exactScore = score, midScore = score, wrongScore = score)
}

