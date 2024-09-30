package com.example.prode_mobile.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.prode_mobile.leagues.LeagueViewModel
import com.example.prode_mobile.leagues.Leagues
import com.example.prode_mobile.pronosticos.Pronosticos
import com.example.prode_mobile.score.Score
import com.example.prode_mobile.score.ScoreAndProfile


@Composable
fun NavHostComposable (innerPadding : PaddingValues, navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = ProdeScreen.Pronosticos.name,
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ){
        composable(route = ProdeScreen.Pronosticos.name) {
            Pronosticos()
        }
        composable (route = ProdeScreen.League.name) {
            Leagues()
        }
        composable(route = ProdeScreen.Score.name) {
            ScoreAndProfile()
        }
    }

}
