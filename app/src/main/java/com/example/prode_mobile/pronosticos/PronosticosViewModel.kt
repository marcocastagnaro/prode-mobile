package com.example.prode_mobile.pronosticos

import android.content.Context
import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.example.prode_mobile.api_calls.ApiServiceImpl
import com.example.prode_mobile.data.PreferencesKeys
import com.example.prode_mobile.data.ProdeMobileDatabase
import com.example.prode_mobile.data.ProdeResult
import com.example.prode_mobile.data.getFromDataStore
import com.example.prode_mobile.data.saveToDataStore
import com.example.prode_mobile.leagues.LeagueData
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class PronosticosViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val apiService: ApiServiceImpl
) : ViewModel() {


    private val _loadingRounds = MutableStateFlow(false)
    val loadingRounds = _loadingRounds.asStateFlow()

    private val _loadingMatches = MutableStateFlow(false)
    val loadingMatches = _loadingMatches.asStateFlow()

    private val _showRetry = MutableStateFlow(false)
    val showRetry = _showRetry.asStateFlow()

    private val _showMatchesRetry = MutableStateFlow(false)
    val showMatchesRetry = _showMatchesRetry.asStateFlow()

    private var _stagesList = MutableStateFlow(listOf<StageData>())
    val stagesList = _stagesList.asStateFlow()

    private var _roundsList = MutableStateFlow(listOf<RoundData>())
    val roundsList = _roundsList.asStateFlow()

    //fun get rounds -> api call de conseguir todos los rounds

    //fun get matches -> api call de conseguir todos los matches
    private var _scheduleList = MutableStateFlow(listOf<RoundMatchData>())
    val scheduleList = _scheduleList.asStateFlow()

    private var _roundValue = MutableStateFlow("")
    val roundValue = _roundValue.asStateFlow()
    init {
        getRoundFromDataStore()
    }
    private fun getRoundFromDataStore() {
        viewModelScope.launch {
            getFromDataStore(context, PreferencesKeys.ROUND_SELECTED).collect {
                _roundValue.value = it ?: ""
            }
        }
    }

    fun isMatchAlreadyInDB (matchId: Int) : Boolean {
        val value = prode_database.prodeResultDao().getProdeResult(matchId).value
        return value != null
    }

    fun getProdeResult (matchId: Int) : ProdeResult {
        return prode_database.prodeResultDao().getProdeResult(matchId).value!!
    }
    fun saveRoundToDataStore(round: String) {
        viewModelScope.launch {
            _roundValue.value = round
            saveToDataStore(context, round, PreferencesKeys.ROUND_SELECTED)
        }
    }
    private val prode_database = ProdeMobileDatabase.getDatabase(context)
    val leaguesAndSeasonList = prode_database.leagueDao().getAllLeagues().asFlow()

    fun retryLoadingRounds(league: String) {
        loadRounds(league)
    }

    fun retryLoadingMatches(league: String) {
        loadMatches(league)
    }
    private fun loadRounds(league: String) {
        _loadingRounds.value = true

        var stagesFiltered = listOf<StageData>()

        viewModelScope.launch {
            leaguesAndSeasonList.collect { leagues ->
                val season = leagues.find { it.name == league }
                Log.i("PronosticosViewModel", "Season: ${season?.seasonId}")
                if (season != null) {
                    apiService.getStages(
                        seasonId = season.seasonId,
                        context = context,
                        onSuccess = { stages ->
                            viewModelScope.launch {
                                Log.i("PronosticosViewModel", "Stages: ${stages.map { it.id }}")
                                stagesFiltered = stages.filter { stage ->
                                    stage.is_current && !stage.type.name.toLowerCase(Locale.ROOT).contains("knock")
                                }
                                _stagesList.emit(stagesFiltered)
                            }

                            apiService.getRoundsList(
                                seasonId = season.seasonId,  // Usamos el id correcto
                                context = context,
                                onSuccess = { rounds ->
                                    viewModelScope.launch {
                                        Log.i("PronosticosViewModel", "Rounds: ${rounds.map { it.id }}")

                                        val roundsFiltered = rounds.filter { round ->
                                            stagesFiltered.any { stage -> stage.id == round.stage_id }
                                        }
                                        Log.i("PronosticosViewModel", "Rounds filtered: ${roundsFiltered.map { it.id }}")

                                        val sorted = roundsFiltered.sortedBy { it.name.toInt() }
                                        _roundsList.emit(sorted)
                                    }
                                    _showRetry.value = false
                                },
                                onFail = {
                                    _showRetry.value = true
                                },
                                loadingFinished = {
                                    _loadingRounds.value = false
                                }
                            )
                        },
                        onFail = {
                            _showRetry.value = true
                        },
                        loadingFinished = {
                            _loadingRounds.value = false
                        }
                    )
                } else {
                    _loadingRounds.value = false
                    _showRetry.value = true
                }
            }
        }
    }

    private fun loadMatches(league: String) {
        _loadingMatches.value = true
        val round = roundValue.value
        viewModelScope.launch {
            leaguesAndSeasonList.collect { leagues ->
                val season = leagues.find { it.name == league }

                if (season != null) {
                    apiService.getSchedule(
                        seasonId = season.seasonId,
                        context = context,
                        onSuccess = {
                            viewModelScope.launch {
                                Log.i("ScheduleModel", "Schedule: ${it.map { it.id }}")
                                val filterByRoundId = it[0].rounds?.filter { schedule -> schedule.name == round }
                                if (filterByRoundId != null) {
                                    _scheduleList.emit(filterByRoundId)
                                }
                            }
                            _showMatchesRetry.value = false
                        },
                        onFail = {
                            _showMatchesRetry.value = true
                        },
                        loadingFinished = {
                            _loadingMatches.value = false
                        }
                    )
                } else {
                    Log.e("loadMatches", "No se encontró la liga con el nombre: $league")
                    _loadingMatches.value = false
                    _showMatchesRetry.value = true
                }
            }
        }
    }


    fun savePronostico (matchId: Int, localGoals: Int, visitorGoals: Int, winner: String) {
        val prodeResult: ProdeResult = ProdeResult(
            matchId = matchId,
            localGoals = localGoals,
            visitorGoals = visitorGoals,
            winner = winner
        )
        viewModelScope.launch {
            prode_database.prodeResultDao().insert(prodeResult)
        }
    }
}