package com.example.prode_mobile.api_calls

import retrofit2.http.GET
import retrofit2.http.Query

interface LeagueService {

    @GET("${StaticValues.API_VERSION}/my/leagues")
    suspend fun getLeagues(
        @Query("api_token") apiToken: String = StaticValues.api_token,
        @Query("include") include: String = ""
    ): LeaguesResponse
}