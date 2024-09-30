package com.example.prode_mobile.api_calls

import androidx.compose.ui.res.stringResource
import com.example.prode_mobile.ProdeApplication
import com.example.prode_mobile.R
import com.example.prode_mobile.leagues.LeagueData
import com.example.prode_mobile.leagues.LeaguesData
import com.example.prode_mobile.leagues.SeasonsData
import com.example.prode_mobile.pronosticos.RoundsData
import com.example.prode_mobile.pronosticos.SchedulesData
import com.example.prode_mobile.pronosticos.StagesData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("my/leagues")
    fun getLeagues(
        @Query("api_token") apiToken: String = ProdeApplication.context.getString(R.string.api_token),
        @Query("include") include: String = ""
    ): Call<LeaguesData>

    @GET("football/seasons/search/2025")
    fun getActualSeasons(
        @Query("api_token") apiToken: String = ProdeApplication.context.getString(R.string.api_token),
        @Query("include") include: String = ""
    ): Call<SeasonsData>




    @GET("football/rounds/seasons/{seasonId}")
    fun getRounds(
        @Path("seasonId") seasonId: Int,
        @Query("api_token") apiToken: String = ProdeApplication.context.getString(R.string.api_token),
        @Query("include") include: String = ""
    ): Call<RoundsData>


    @GET("football/stages/seasons/{seasonId}")
    fun getStages(
        @Path("seasonId") seasonId: Int,
        @Query("api_token") apiToken: String = ProdeApplication.context.getString(R.string.api_token),
        @Query("include") include: String = "type"
    ): Call<StagesData>

    @GET("football/schedules/seasons/{seasonId}")
    fun getSchedule(
        @Path("seasonId") seasonId: Int,
        @Query("api_token") apiToken: String = ProdeApplication.context.getString(R.string.api_token),
        @Query("include") include: String = ""
    ): Call<SchedulesData>
}