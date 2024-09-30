package com.example.prode_mobile.leagues

data class LeagueData (
    val id: Int,
    val country_id: Int,
    val name: String,
    val active: Boolean, //QUE SEA SIMEPRE TRRUE
    val image_path: String,
    val category: Int,
    val seasonId: Int,
    val type: String,
    val sub_type: String,
    val sport : SportData
)
data class SportData(
    val name: String
)
data class LeaguesData(
    val data: ArrayList<LeagueData>
)