package com.example.prode_mobile.pronosticos

import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.prode_mobile.R
import com.example.prode_mobile.ui.theme.SmallFontSize

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
                fontSize = SmallFontSize
            )
        )
    }
}

@Preview
@Composable
fun SavePronosticoToDatabasePreview() {
    val viewModel = hiltViewModel<PronosticosViewModel>()

    SavePronosticoToDatabase(1, viewModel, 1, 2)
}