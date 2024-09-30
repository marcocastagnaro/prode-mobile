package com.example.prode_mobile.score

import Profile
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Divider
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.prode_mobile.R
import com.example.prode_mobile.pronosticos.PronosticosViewModel
import com.example.prode_mobile.ui.theme.TitleBlueColor


@Composable
fun ScoreAndProfile() {
    val viewModel = hiltViewModel<PronosticosViewModel>()
    val scoreAndProfileViewModel = hiltViewModel<ScoreAndProfileViewModel>()
    val score = viewModel.score.collectAsState()
    Surface (modifier = Modifier.fillMaxSize()) {
        Column {
            Text(
                text = stringResource(id = R.string.score_and_profile), style = TextStyle(
                    fontSize = 60.sp,
                    fontFamily = FontFamily.Serif,
                    color = TitleBlueColor
                ), modifier = Modifier.padding(
                    dimensionResource(id = R.dimen.large_padding))
            )
            Spacer(modifier = Modifier.size(16.dp))
            Divider(
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.default_padding))
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.size(16.dp))
            Profile(viewModel = scoreAndProfileViewModel)
            Spacer(modifier = Modifier.size(16.dp))
            Divider(
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.default_padding))
                    .fillMaxWidth()
            )
            Score(score)
        }
    }
}


@Preview
@Composable
fun ScoreAndProfPreview() {
    ScoreAndProfile()
}