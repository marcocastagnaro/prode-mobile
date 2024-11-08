package com.example.prode_mobile.pronosticos

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import com.example.prode_mobile.R
import com.example.prode_mobile.ui.theme.RegularFontSize
import com.example.prode_mobile.ui.theme.TitleDimensions

@Composable
fun PredictionsTitle() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(dimensionResource(id = R.dimen.make_predictions_title_surface_size)),
        color = MaterialTheme.colorScheme.primary,
    ) {
        Column (horizontalAlignment = Alignment.CenterHorizontally){
            Text(
                text = stringResource(id = R.string.pronsoticos_title), style = TextStyle(
                    color = MaterialTheme.colorScheme.secondary,
                    fontSize = TitleDimensions,
                    fontFamily = FontFamily.SansSerif
                ), modifier = Modifier.padding(dimensionResource(id = R.dimen.large_padding))
            )
            Text(
                text = stringResource(id = R.string.pronsoticos_subtitle), style = TextStyle(
                    color = MaterialTheme.colorScheme.secondary,
                    fontSize = RegularFontSize,
                    fontFamily = FontFamily.SansSerif
                ), modifier = Modifier.padding(
                    dimensionResource(id = R.dimen.large_padding)
                )
            )
        }
    }
}

@Preview
@Composable
fun PredictionsTitlePreview() {
    PredictionsTitle()
}