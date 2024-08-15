package com.example.prode_mobile.pronosticos

import android.widget.Toast
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.RemoveCircleOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
//import androidx.compose.material.icons.filled.

import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.prode_mobile.R
import com.example.prode_mobile.ui.theme.BlueButton
import com.example.prode_mobile.ui.theme.DarkBackground
import com.example.prode_mobile.ui.theme.TitleColor
import java.util.Date

@Composable
fun Pronosticos() {
    val leagues = listOf<LeagueSelector>(
        LeagueSelector(league = "Liga Argentina", onClick = {}),
        LeagueSelector(league = "Liga Brasilera", onClick = {}),
        LeagueSelector(league = "Liga Italiana", onClick = {}),
        LeagueSelector(league = "Liga Alemana", onClick = {}),
        )
    val fechas = listOf<FechaSelector> (
        FechaSelector(1, onClick = {}),
        FechaSelector(2, onClick = {}),
        FechaSelector(3, onClick = {}),
        FechaSelector(4, onClick = {}),
        FechaSelector(5, onClick = {})
    )

    val matchesPrimFecha = listOf<MatchCardData> (
        MatchCardData("TEAM1", "TEAM2", "2024-20-08", 1),
        MatchCardData("TEAM3", "TEAM4", "2024-21-08", 1),
        MatchCardData("TEAM5", "TEAM6", "2024-22-08", 1),
        MatchCardData("TEAM7", "TEAM8", "2024-24-08", 1),
        MatchCardData("TEAM7", "TEAM8", "2024-24-08", 1),
        MatchCardData("TEAM7", "TEAM8", "2024-24-08", 1)

    )
    Surface(onClick = { /*TODO*/ }, modifier = Modifier.fillMaxSize(), color= DarkBackground) {
        Column (modifier = Modifier.verticalScroll(rememberScrollState()), horizontalAlignment = Alignment.CenterHorizontally){
            SelectYourLeagueTitle()
            LeagueSelector(leagues)
            Column(modifier = Modifier.padding(16.dp)) {
                matchesPrimFecha.forEach { part ->
                    MatchCard(
                        team1 = part.team1,
                        team2 = part.team2,
                        part.date
                    )
                    Spacer(modifier = Modifier.size(16.dp))
                }
            }
        }
    }
}

@Composable
fun MatchCard(team1: String, team2: String, date: String) {
    var scoreTeam1 by remember { mutableStateOf(0) }
    var scoreTeam2 by remember { mutableStateOf(0) }

    Surface(
        modifier = Modifier
            .width(250.dp)
            .height(150.dp), // Se ajusta la altura para acomodar los inputs y el botón
        color = BlueButton,
        shape = MaterialTheme.shapes.medium, // Añade bordes redondeados
        shadowElevation = 8.dp // Añade sombra para darle profundidad
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp), // Añade padding interno para separar el contenido de los bordes
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp), // Espacio debajo de las puntuaciones
                horizontalArrangement = Arrangement.SpaceBetween // Espacia uniformemente los elementos
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = team1,
                        style = TextStyle(
                            fontWeight = FontWeight.Black,
                            color = TitleColor,
                            fontSize = 10.sp,
                            fontFamily = FontFamily.Monospace
                        ),
                        modifier = Modifier.padding(bottom = 4.dp) // Espacio debajo del nombre del equipo
                    )
                    ScoreInput(score = scoreTeam1, onScoreChange = {
                        if (it != null) {
                            scoreTeam1 = it
                        }
                    })
                }

                Text(
                    text = "VS",
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
                        text = team2,
                        style = TextStyle(
                            fontWeight = FontWeight.Black,
                            color = TitleColor,
                            fontSize = 10.sp,
                            fontFamily = FontFamily.Monospace
                        ),
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    ScoreInput(score = scoreTeam2, onScoreChange = {
                        if (it != null) {
                            scoreTeam2 = it
                        }
                    })
                }
            }

            Spacer(modifier = Modifier.weight(1f)) 

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = date,
                    style = TextStyle(
                        fontWeight = FontWeight.W300,
                        color = TitleColor,
                        fontSize = 14.sp,
                        fontFamily = FontFamily.SansSerif
                    )
                )
                Button(
                    onClick = { /*TODO*/ },
                    colors = ButtonDefaults.buttonColors(containerColor = TitleColor)
                ) {
                    Text("Save", color = BlueButton)
                }
            }
        }
    }
}
@Composable
fun ScoreInput(score: Int?, onScoreChange: (Int?) -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        IconButton(
            onClick = {
                if (score != null && score > 0) {
                    onScoreChange(score - 1)
                } else if (score == 0) {
                    onScoreChange(null) // Vuelve a "-"
                }
            },
            modifier = Modifier.size(18.dp)
        ) {
            Icon(imageVector = Icons.Default.Remove, contentDescription = "Decrease")
        }

        OutlinedTextField(
            value = score?.toString() ?: "-",
            onValueChange = { newValue ->
                val newScore = newValue.toIntOrNull()
                onScoreChange(newScore)
            },
            modifier = Modifier
                .width(40.dp)
                .height(30.dp),
            textStyle = TextStyle(fontSize = 12.sp),
            singleLine = true,
            readOnly = true // Hacer que sea solo de lectura para que solo los botones lo cambien
        )

        IconButton(
            onClick = {
                if (score == null) {
                    onScoreChange(0) // Empieza de 0 si está en "-"
                } else {
                    onScoreChange(score + 1)
                }
            },
            modifier = Modifier.size(18.dp)
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Increase")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeagueSelector(leagues: List<LeagueSelector>) {
    val context = LocalContext.current
    val leagueNames = leagues.map{ l -> l.league}
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(leagueNames[0]) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp)
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            TextField(
                value = selectedText,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                leagueNames.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item) },
                        onClick = {
                            selectedText = item
                            expanded = false
                            Toast.makeText(context, item, Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun SelectYourLeagueTitle () {
    Surface(modifier = Modifier
        .fillMaxWidth()
        .height(140.dp), color = BlueButton,
    ) {
        Column (horizontalAlignment = Alignment.CenterHorizontally){
            Text(
                text = stringResource(id = R.string.pronsoticos_title), style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    color = TitleColor,
                    fontSize = 32.sp,
                    fontFamily = FontFamily.Monospace
                ), modifier = Modifier.padding(16.dp)
            )
            Text(
                text = stringResource(id = R.string.pronsoticos_subtitle), style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    color = TitleColor,
                    fontSize = 16.sp,
                    fontFamily = FontFamily.Monospace
                ), modifier = Modifier.padding(16.dp)
            )
        }
    }
}


data class LeagueSelector(
    val league : String,
    val onClick : () -> Unit
)
data class FechaSelector (
    val nroFecha: Int,
    val onClick: () -> Unit
)

data class MatchCardData (
    val team1: String,
    val team2: String,
    val date: String,
    val nroFecha: Int
)
@Preview
@Composable
fun PreviewPronostico() {
    Pronosticos()
}