package com.example.prode_mobile.leagues

data class LeagueData (
    val id: Int,
    val country_id: Int,
    val name: String,
    val active: Boolean, //QUE SEA SIMEPRE TRRUE
    val image_path: String,
    val category: Int,
)