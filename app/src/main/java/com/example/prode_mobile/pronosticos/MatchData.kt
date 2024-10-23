package com.example.prode_mobile.pronosticos

data class FechaSelector (
    val nroFecha: Int,
)

data class MatchCardData (
    val match_id: Int,
    val team1: String,
    val team2: String,
    val date: String,
    val urlTeam1: String,
    val urlTeam2: String,
    val nroFecha: Int,
    val is_older: Boolean
)

data class SchedulesData (
    val data: List<ScheduleData>
)

data class ScheduleData (
    val id: Int,
    val sport_id: Int,
    val league_id: Int,
    val season_id: Int,
    val type_id: Int,
    val name: String,
    val sort_order: Int,
    val finished: Boolean,
    val is_current: Boolean,
    val starting_at: String,
    val ending_at: String,
    val games_in_current_week: Boolean,
    val rounds: List<RoundMatchData>?
)

data class RoundMatchData (
    val id: Int,
    val sport_id: Int,
    val league_id: Int,
    val season_id: Int,
    val stage_id: Int,
    val name: String,
    val finished: Boolean,
    val is_current: Boolean,
    val starting_at: String,
    val ending_at: String,
    val games_in_current_week: Boolean,
    val fixtures: List<FixtureData>
)

data class FixtureData (
    val id: Int,
    val sport_id: Int,
    val league_id: Int,
    val season_id: Int,
    val stage_id: Int,
    val round_id: Int,
    val name: String,
    val starting_at: String,
    val participants: List<Team>,
    val scores: List<ScoreData>?
)

data class Team (
    val id: Int,
    val name: String,
    val image_path: String,
    val meta: Meta
)

data class Meta (
    val location: String,
    val winner: Boolean,
    val position: Int
)

data class ScoreData (
    val id: Int,
    val fixture_id: Int,
    val participant_id: Int,
    val score: ScoreMatchData
)

data class ScoreMatchData (
    val goals: Int,
    val participant: String
)
