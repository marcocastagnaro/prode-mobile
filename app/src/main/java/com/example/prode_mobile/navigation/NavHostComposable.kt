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
import com.example.prode_mobile.home.Home
import com.example.prode_mobile.home.HomeCarrousel
import com.example.prode_mobile.pronosticos.Pronosticos


@Composable
fun NavHostComposable (innerPadding : PaddingValues, navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = ProdeScreen.Home.name,
        modifier = Modifier.fillMaxSize().padding(innerPadding)
    ){
        composable(route = ProdeScreen.Home.name) {
            HomeCarrousel()
        }
        composable(route = ProdeScreen.Pronosticos.name) {
            Pronosticos()
        }
    }

}