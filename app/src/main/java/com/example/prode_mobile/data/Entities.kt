package com.example.prode_mobile.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "leagues")
data class LeagueAndSeason(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val league_id: Int,
    val country_id: Int,
    val name: String,
    val active: Boolean,
    val image_path: String,
    val category: Int,

    val seasonId: Int
)


@Entity(tableName = "prode_results")
data class ProdeResult(
    @PrimaryKey(autoGenerate = true)
        val id: Long = 0,
        val matchId: Int,
        val localGoals: Int,
        val visitorGoals: Int,
        val winner: String
)