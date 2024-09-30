package com.example.prode_mobile.api_calls

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.prode_mobile.R
import com.example.prode_mobile.leagues.LeagueData
import com.example.prode_mobile.leagues.LeaguesData
import com.example.prode_mobile.leagues.SeasonData
import com.example.prode_mobile.leagues.SeasonsData
import com.example.prode_mobile.pronosticos.RoundData
import com.example.prode_mobile.pronosticos.RoundsData
import com.example.prode_mobile.pronosticos.ScheduleData
import com.example.prode_mobile.pronosticos.SchedulesData
import com.example.prode_mobile.pronosticos.StageData
import com.example.prode_mobile.pronosticos.StagesData
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ApiServiceImpl @Inject constructor() {

    fun getLeagues(
        context: Context,
        onSuccess: (List<LeagueData>) -> Unit,
        onFail: () -> Unit,
        loadingFinished: () -> Unit
    ) {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(context.getString(R.string.base_url))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(ApiService::class.java)
        val call: Call<LeaguesData> = service.getLeagues()

        call.enqueue(object : Callback<LeaguesData> {
            override fun onResponse(
                call: Call<LeaguesData>,
                response: Response<LeaguesData>
            ) {
                loadingFinished()
                if (response.isSuccessful) {
                    response.body()?.let { leaguesData ->
                        onSuccess(leaguesData.data)
                    } ?: run {
                        onFailure(call, Throwable("Body is null"))
                    }
                } else {
                    onFailure(call, Throwable("Response is not successful"))
                }
            }

            override fun onFailure(call: Call<LeaguesData>, t: Throwable) {
                Toast.makeText(context, "Can't get leagues", Toast.LENGTH_SHORT).show()
                onFail()
                loadingFinished()
            }
        })
    }

    fun getSeasons(
        context: Context,
        onSuccess: (List<SeasonData>) -> Unit,
        onFail: () -> Unit,
        loadingFinished: () -> Unit
    ) {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(context.getString(R.string.base_url))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(ApiService::class.java)
        val call: Call<SeasonsData> = service.getActualSeasons()

        call.enqueue(object : Callback<SeasonsData> {
            override fun onResponse(
                call: Call<SeasonsData>,
                response: Response<SeasonsData>
            ) {
                loadingFinished()
                if (response.isSuccessful) {
                    response.body()?.let { seasonsData ->
                        onSuccess(seasonsData.data)
                    } ?: run {
                        onFailure(call, Throwable("Body is null"))
                    }
                } else {
                    onFailure(call, Throwable("Response is not successful"))
                }
            }

            override fun onFailure(call: Call<SeasonsData>, t: Throwable) {
                Toast.makeText(context, "Can't get leagues", Toast.LENGTH_SHORT).show()
                onFail()
                loadingFinished()
            }
        })
    }




    fun getRoundsList(
        seasonId: Int,
        context: Context,
        onSuccess: (List<RoundData>) -> Unit,
        onFail: () -> Unit,
        loadingFinished: () -> Unit
    ){
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(context.getString(R.string.base_url))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(ApiService::class.java)
        val call: Call<RoundsData> = service.getRounds(seasonId)

        call.enqueue(object : Callback<RoundsData> {
            override fun onResponse(
                call: Call<RoundsData>,
                response: Response<RoundsData>
            ) {
                loadingFinished()
                if (response.isSuccessful) {
                    response.body()?.let { roundsData ->
                        onSuccess(roundsData.data)
                    } ?: run {
                        onFailure(call, Throwable("Body is null"))
                    }
                } else {
                    onFailure(call, Throwable("Response is not successful"))
                }
            }

            override fun onFailure(call: Call<RoundsData>, t: Throwable) {
                Toast.makeText(context, "Can't get leagues", Toast.LENGTH_SHORT).show()
                onFail()
                loadingFinished()
            }
        })
    }

    fun getStages (
        seasonId: Int,
        context: Context,
        onSuccess: (List<StageData>) -> Unit,
        onFail: () -> Unit,
        loadingFinished: () -> Unit
    ){
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(context.getString(R.string.base_url))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(ApiService::class.java)
        val call: Call<StagesData> = service.getStages(seasonId)
        call.enqueue(object : Callback<StagesData> {
            override fun onResponse(
                call: Call<StagesData>,
                response: Response<StagesData>
            ) {
                loadingFinished()
                if (response.isSuccessful) {
                    response.body()?.let { stagesData ->
                        onSuccess(stagesData.data)
                    } ?: run {
                        onFailure(call, Throwable("Body is null"))
                    }
                } else {
                    onFailure(call, Throwable("Response is not successful"))
                }
            }

            override fun onFailure(call: Call<StagesData>, t: Throwable) {
                Toast.makeText(context, "Can't get leagues", Toast.LENGTH_SHORT).show()
                onFail()
                loadingFinished()
            }
        })
    }

    fun getSchedule (
        seasonId: Int,
        context: Context,
        onSuccess: (List<ScheduleData>) -> Unit,
        onFail: () -> Unit,
        loadingFinished: () -> Unit
    ) {

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(context.getString(R.string.base_url))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(ApiService::class.java)
        val call: Call<SchedulesData> = service.getSchedule(seasonId)
        Log.i("API Request", "URL: ${call.request().url}")
        Log.i("API Request", "Method: ${call.request().method}")

        try {
            call.enqueue(object : Callback<SchedulesData> {
                override fun onResponse(
                    call: Call<SchedulesData>,
                    response: Response<SchedulesData>
                ) {
                    Log.i("API Response", "Response received")
                    loadingFinished()
                    if (response.isSuccessful) {
                        Log.i("API Response", "Successful response")

                        response.body()?.let { scheduleData ->
                            onSuccess(scheduleData.data)
                        } ?: run {
                            onFailure(call, Throwable("Body is null"))
                        }
                    } else {
                        Log.e("API Response", "Response is not successful: ${response.code()}")

                        onFailure(call, Throwable("Response is not successful"))
                    }
                }

                override fun onFailure(call: Call<SchedulesData>, t: Throwable) {
                    Log.i("Hello world 2", "Hello world 2")
                    Toast.makeText(context, "Can't get leagues", Toast.LENGTH_SHORT).show()
                    onFail()
                    loadingFinished()
                }
            })
        } catch (e: Exception) {
            Log.e("ApiCall", "Exception: ${e.message}")
        }
    }
}
