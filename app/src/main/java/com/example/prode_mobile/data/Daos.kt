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