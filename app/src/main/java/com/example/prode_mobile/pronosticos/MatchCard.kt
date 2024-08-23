package com.example.prode_mobile.pronosticos

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.prode_mobile.ui.theme.BlueButton
import com.example.prode_mobile.ui.theme.TitleColor

@Composable
fun MatchCard(matchData: MatchCardData, isCardSelected : () -> Unit) {
    var scoreTeam1 by remember { mutableStateOf(0) }
    var scoreTeam2 by remember { mutableStateOf(0) }

    Surface(
        modifier = Modifier
            .width(250.dp)
            .height(80.dp),
        color = BlueButton,
        shape = MaterialTheme.shapes.medium,
        shadowElevation = 8.dp
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
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = matchData.team1,
                        style = TextStyle(
                            fontWeight = FontWeight.Black,
                            color = TitleColor,
                            fontSize = 10.sp,
                            fontFamily = FontFamily.SansSerif
                        ),
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    AsyncImage(model = matchData.urlTeam1, contentDescription = "", modifier= Modifier.size(50.dp)) //Place holder para cuando la imagen carga y error cuando falla
                }

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

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = matchData.team2,
                        style = TextStyle(
                            fontWeight = FontWeight.Black,
                            color = TitleColor,
                            fontSize = 10.sp,
                            fontFamily = FontFamily.Monospace
                        ),
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    AsyncImage(model = matchData.urlTeam2, contentDescription = "", modifier = Modifier.size(50.dp)) //Place holder para cuando la imagen carga y error cuando falla
                }
            }

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Preview
@Composable
fun PreviewCard() {
    MatchCard( MatchCardData(
        "Manchester City",
        "Chelsea",
        "30-oct",
        "https://cdn.sportmonks.com/images/soccer/teams/6/390.png",
        "https://cdn.sportmonks.com/images/soccer/teams/6/390.png", 1),
        {}
    )
}
