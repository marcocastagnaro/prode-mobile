package com.example.prode_mobile.pronosticos

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material3.Button
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

@Composable
fun Pronosticos() {
    val leagues = listOf<LeagueSelector>(
        LeagueSelector(league = "Choose Your League", onClick= {}),
        LeagueSelector(league = "Liga Argentina", onClick = {}),
        LeagueSelector(league = "Liga Brasilera", onClick = {}),
        LeagueSelector(league = "Liga Italiana", onClick = {}),
        LeagueSelector(league = "Liga Alemana", onClick = {}),
        )
    val fechas = listOf<FechaSelector> (
        FechaSelector(0, onClick = {}),
        FechaSelector(1, onClick = {}),
        FechaSelector(2, onClick = {}),
        FechaSelector(3, onClick = {}),
        FechaSelector(4, onClick = {}),
        FechaSelector(5, onClick = {})
    )

    val matchesPrimFecha = listOf<MatchCardData> (
        MatchCardData("TEAM1", "TEAM2", "2024-20-08", "https://cdn.sportmonks.com/images/soccer/teams/6/390.png", "https://cdn.sportmonks.com/images/soccer/teams/6/390.png", 1),
        MatchCardData("TEAM3", "TEAM4", "2024-21-08", "https://cdn.sportmonks.com/images/soccer/teams/6/390.png","https://cdn.sportmonks.com/images/soccer/teams/6/390.png",1),
        MatchCardData("TEAM5", "TEAM6", "2024-22-08", "https://cdn.sportmonks.com/images/soccer/teams/6/390.png","https://cdn.sportmonks.com/images/soccer/teams/6/390.png",1),
        MatchCardData("TEAM7", "TEAM8", "2024-24-08", "https://cdn.sportmonks.com/images/soccer/teams/6/390.png","https://cdn.sportmonks.com/images/soccer/teams/6/390.png",1),
        MatchCardData("TEAM7", "TEAM8", "2024-24-08", "https://cdn.sportmonks.com/images/soccer/teams/6/390.png","https://cdn.sportmonks.com/images/soccer/teams/6/390.png",1),
        MatchCardData("TEAM7", "TEAM8", "2024-24-08", "https://cdn.sportmonks.com/images/soccer/teams/6/390.png","https://cdn.sportmonks.com/images/soccer/teams/6/390.png",1)

    )
    var isLeagueSelected by remember {
        mutableStateOf(false)
    }
    var isDateAndLeagueSelected by remember {
        mutableStateOf(false)
    }
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = DarkBackground,
    ) {
        Column ( horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
            .fillMaxSize()
            .verticalScroll(
                rememberScrollState()
            )){
            SelectYourLeagueTitle()
            LeagueSelector(leagues, onLeagueSelected = {isLeagueSelected = true})

            AnimatedVisibility(visible = isLeagueSelected) {
                DateSelector(dates = fechas, isDateAndLeagueSelected = {isDateAndLeagueSelected = true})
            }
            AnimatedVisibility(visible = isDateAndLeagueSelected) {

            Column(modifier = Modifier.padding(16.dp)) {
                matchesPrimFecha.forEach { part ->
                    MatchCard(MatchCardData(
                        team1 = part.team1,
                        team2 = part.team2,
                        part.date,
                        part.urlTeam1,
                        part.team2, 1)
                    ) {}
                    Spacer(modifier = Modifier.size(24.dp))
                }
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
                    fontFamily = FontFamily.SansSerif
                ), modifier = Modifier.padding(16.dp)
            )
            Text(
                text = stringResource(id = R.string.pronsoticos_subtitle), style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    color = TitleColor,
                    fontSize = 16.sp,
                    fontFamily = FontFamily.SansSerif
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
    val urlTeam1: String,
    val urlTeam2: String,
    val nroFecha: Int,
)
@Preview
@Composable
fun PreviewPronostico() {
    Pronosticos()
}