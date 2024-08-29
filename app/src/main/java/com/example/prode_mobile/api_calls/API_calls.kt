package com.example.prode_mobile.api_calls

import com.example.prode_mobile.leagues.LeagueData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class API_calls {
    suspend fun fetchLeagues(): ArrayList<LeagueData>? {
        return withContext(Dispatchers.IO) {
            try {
                val leagueApi = LeagueApi()
                val response = leagueApi.service.getLeagues()
                response.data
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }

}

data class LeaguesResponse(
    val data: ArrayList<LeagueData>
)

