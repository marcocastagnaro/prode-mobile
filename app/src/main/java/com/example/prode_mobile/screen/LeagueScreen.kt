package com.example.prode_mobile.screen

import android.util.Log
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.example.prode_mobile.screen.api_calls.API_calls
import com.example.prode_mobile.screen.api_calls.LeagueData
import kotlinx.coroutines.launch

@Composable
fun LeagueScreen() {
    LeaguesScreen()
}

@Composable
fun LeaguesScreen() {
    var leagues by remember { mutableStateOf<List<LeagueData>?>(null) }
    val coroutineScope = rememberCoroutineScope()
    val apiCalls = API_calls()  // Crear una instancia de API_calls

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            val response = apiCalls.fetchLeagues()
            Log.d("LeaguesScreen", "Response: $response")
            leagues = response  // Asignar directamente la lista de ligas
        }
    }

    // Mostrar la lista de ligas en la UI
    leagues?.let { leagueList ->
        LazyColumn {
            items(leagueList) { league ->
                Text(text = league.name)  // Asumiendo que LeagueData tiene un campo `name`
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LeaguesScreenPreview() {
    LeaguesScreen()
}
