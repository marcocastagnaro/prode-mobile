package com.example.prode_mobile.score

import Reference
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.prode_mobile.R
import com.example.prode_mobile.pronosticos.PronosticosViewModel
import com.example.prode_mobile.ui.theme.TitleDimensions


@Composable
fun ScoreAndProfile() {
    val viewModel = hiltViewModel<PronosticosViewModel>()
    val score = viewModel.score.collectAsState()
    val exactScore = viewModel.exactScore.collectAsState()
    val wrongScore = viewModel.wrongScore.collectAsState()
    val midScore = viewModel.midScore.collectAsState()
    Surface (modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column {
            Text(
                text = stringResource(id = R.string.score_and_profile), style = TextStyle(
                    fontSize = TitleDimensions,
                    fontFamily = FontFamily.Serif,
                    color = MaterialTheme.colorScheme.secondary
                ), modifier = Modifier.padding(
                    dimensionResource(id = R.dimen.large_padding))
            )
            Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.default_spacer)))
            Divider(
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.default_padding))
                    .fillMaxWidth()
            )
            Score(score,exactScore, midScore, wrongScore)
            Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.default_spacer)))
            Reference()
        }
    }
}


@Preview
@Composable
fun ScoreAndProfPreview() {
    ScoreAndProfile()
}