package com.example.prode_mobile.score

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Recomposer
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.prode_mobile.R
import com.example.prode_mobile.ui.theme.TitleDimensions
import kotlinx.coroutines.flow.StateFlow

@Composable
fun ScoreCard (score: State<Int>) {
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
}


@Preview
@Composable
fun ScoreCardPreview() {
    val score = remember { mutableStateOf(100) }
    ScoreCard(score)
}
