package com.example.prode_mobile.navigation

enum class ProdeScreen {
    Home,


    Pronosticos,
    League,

    Settings
}
val basePages = listOf(
    ProdeScreen.Home.name,
    ProdeScreen.Pronosticos.name,
    ProdeScreen.League.name,
    ProdeScreen.Settings
)