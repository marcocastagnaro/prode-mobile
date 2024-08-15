package com.example.prode_mobile.screen.api_calls

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

data class LeagueData (
    val id: Int,
    val sport_id: Int,
    val country_id: Int,
    val name: String,
    val active: Boolean,
    val short_code: String?,
    val image_path: String,
    val type: String,
    val sub_type: String,
    val last_played_at: String?,
    val category: Int,
    val has_jerseys: Boolean
)