package com.example.prode_mobile.pronosticos

data class MatchData(
    val matchId: String,
    val roundId: String,
    val team1:Team,
    val team2:Team,
    val date: String,
)

data class Team (
    val id: Int,
    val name: String,
    val image_path: String
)