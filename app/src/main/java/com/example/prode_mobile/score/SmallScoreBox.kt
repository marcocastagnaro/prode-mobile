package com.example.prode_mobile.score

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import com.example.prode_mobile.R
import com.example.prode_mobile.ui.theme.MediumFontSize
import com.example.prode_mobile.ui.theme.RegularFontSize
import com.example.prode_mobile.ui.theme.SmallFontSize

@Composable
fun SmallScoreBox(score: Int, posibleScore: Int, backgroundColor: Color) {
    Card(
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corner_shape)),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.default_padding))
            .height(dimensionResource(id = R.dimen.match_card_initial_height))
    ) {
        Column (verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center, modifier = Modifier.fillMaxHeight()) {
            Text(
                text = "Score: $posibleScore",
                style = TextStyle(
                    fontSize = RegularFontSize,
                    color = MaterialTheme.colorScheme.secondary,
                    fontFamily = FontFamily.Serif
                ),
                modifier = Modifier.padding(dimensionResource(id =R.dimen.default_padding))
            )
            Text(
                text = "Matches: $score",
                style = TextStyle(
                    fontSize = RegularFontSize,
                    color = MaterialTheme.colorScheme.secondary,
                    fontFamily = FontFamily.Serif
                ),
                modifier = Modifier.padding(dimensionResource(id =R.dimen.default_padding))
            )
        }
    }
}
@Preview
@Composable
fun SmallScoreBoxPreview() {
    SmallScoreBox(5, 10, Color.Blue)
}