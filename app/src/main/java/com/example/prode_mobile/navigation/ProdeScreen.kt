package com.example.prode_mobile.navigation

enum class ProdeScreen {
    Home,


    Pronosticos,
    League,

    Score
}
val basePages = listOf(
    ProdeScreen.Home.name,
    ProdeScreen.Pronosticos.name,
    ProdeScreen.League.name,
    ProdeScreen.Score
)