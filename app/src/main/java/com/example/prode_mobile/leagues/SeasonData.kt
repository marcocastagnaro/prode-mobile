package com.example.prode_mobile.leagues

data class SeasonData (
    val id : Int,
    val league_id: Int,
)
data class SeasonsData(
    val data: ArrayList<SeasonData>
)
