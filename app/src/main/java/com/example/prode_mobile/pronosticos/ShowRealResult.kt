package com.example.prode_mobile.pronosticos

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.prode_mobile.R
import com.example.prode_mobile.ui.theme.CardTextColor
import com.example.prode_mobile.ui.theme.LargeFontSize
import com.example.prode_mobile.ui.theme.RegularFontSize


@Composable
fun ShowRealResults (scoreTeam1: ScoreMatchData, scoreTeam2: ScoreMatchData) {
    Text(
        text = stringResource(id = R.string.resultado),
        style = TextStyle(
            fontWeight = FontWeight.Black,
            color = CardTextColor,
            fontSize = RegularFontSize,
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
                .border(dimensionResource(id = R.dimen.border_weight), MaterialTheme.colorScheme.tertiary)
                .padding(dimensionResource(id = R.dimen.default_padding)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = scoreTeam1.goals.toString() ?: "0",
                style = TextStyle(
                    fontSize = LargeFontSize,
                    fontWeight = FontWeight.Bold
                ),
                color = CardTextColor,
                textAlign = TextAlign.Center
            )
        }
        Box(
            modifier = Modifier
                .size(dimensionResource(id = R.dimen.march_box_result_size))
                .border(
                    dimensionResource(id = R.dimen.border_weight),
                    MaterialTheme.colorScheme.tertiary
                )
                .padding(dimensionResource(id = R.dimen.default_padding)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = scoreTeam2.goals.toString() ?: "0",
                style = TextStyle(
                    fontSize = LargeFontSize,
                    fontWeight = FontWeight.Bold
                ),
                color = CardTextColor,
                textAlign = TextAlign.Center
            )
        }
    }
}
@Preview
@Composable
fun ShowRealResultsPreview() {
    ShowRealResults(ScoreMatchData(1, "Team 2"), ScoreMatchData(2, "Team 1"))
}