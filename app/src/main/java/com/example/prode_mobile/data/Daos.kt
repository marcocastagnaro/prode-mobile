package com.example.prode_mobile.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface LeagueDao {
    @Insert
    suspend fun insert(league: LeagueAndSeason)

    @Update
    suspend fun update(league: LeagueAndSeason)

    @Delete
    suspend fun delete(league: LeagueAndSeason)

    @Query("SELECT * FROM leagues")
    fun getAllLeagues(): LiveData<List<LeagueAndSeason>>

}

@Dao
interface ProdeResultDao {
    @Insert
    suspend fun insert(prodeResult: ProdeResult)

    @Update
    suspend fun update(prodeResult: ProdeResult)

    @Delete
    suspend fun delete(prodeResult: ProdeResult)

    @Query("SELECT * FROM prode_results")
    fun getAllProdeResults(): LiveData<List<ProdeResult>>

    @Query("SELECT * FROM prode_results WHERE matchId = :matchId")
    fun getProdeResult(matchId: Int): LiveData<ProdeResult>
}