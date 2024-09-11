package com.example.prode_mobile.pronosticos

data class RoundData (
    val name: String,
    val id: String,
    val league_id: Int,
    val stage_id: Int,
    val finished: Boolean,
    val is_current: Boolean,
    val starting_at: String,
    val ending_at: String,
    val games_in_current_week: Boolean
    )

data class RoundsData(
    val data: ArrayList<RoundData>
)

data class StageData (
    val id: Int,
    val is_current: Boolean,
    val type : TypeData

)

data class StagesData (
    val data: ArrayList<StageData>
)
data class TypeData(
    val id: Int,
    val name: String,
    val model_type: String
)