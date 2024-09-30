package com.example.prode_mobile.pronosticos

import android.content.Context
import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale
import javax.inject.Inject
import androidx.compose.runtime.State
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.forEach
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

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

    private var _scheduleList = MutableStateFlow(listOf<RoundMatchData>())
    val scheduleList = _scheduleList.asStateFlow()

    private var _roundValue = MutableStateFlow("")
    val roundValue = _roundValue.asStateFlow()

    private val _score = MutableStateFlow(0)
    val score = _score.asStateFlow()

    private val _alreadyPlayedMatches = MutableStateFlow(listOf<RoundMatchData>())
    val alreadyPlayedMatches=_alreadyPlayedMatches.asStateFlow()

    private val prode_database: ProdeMobileDatabase;

    init {
        prode_database = ProdeMobileDatabase.getDatabase(context)

        viewModelScope.launch {


            sumScore() // Sumar puntajes
            getScoreFromDataStore() // Obtener puntaje almacenado
        }
    }

    fun saveRoundSelected(round: String) {
        _roundValue.value = round
    }

    suspend fun isMatchAlreadyInDB(matchId: Int): Boolean {
        return withContext(Dispatchers.IO) {
            val result = prode_database.prodeResultDao().getProdeResult(matchId)
            if (result != null) {
                val valueId = result.id ?: 0
                valueId.toInt() != 0
            } else {
                false
            }
        }
    }

    private val _isMatchInDB = mutableStateOf(false)
    val isMatchInDB: State<Boolean> = _isMatchInDB

    fun checkIfMatchIsInDB(matchId: Int) {
        viewModelScope.launch {
            _isMatchInDB.value = isMatchAlreadyInDB(matchId)
        }
    }

    suspend fun getProdeResultForMatch(matchId: Int): ProdeResult? {
        return withContext(Dispatchers.IO) {
            prode_database.prodeResultDao().getProdeResult(matchId)
        }
    }

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
                if (season != null) {
                    apiService.getStages(
                        seasonId = season.seasonId,
                        context = context,
                        onSuccess = { stages ->
                            viewModelScope.launch {
                                stagesFiltered = stages.filter { stage ->
                                    stage.is_current && !stage.type.name.toLowerCase(Locale.ROOT)
                                        .contains("knock")
                                }
                                _stagesList.emit(stagesFiltered)
                            }

                            apiService.getRoundsList(
                                seasonId = season.seasonId,
                                context = context,
                                onSuccess = { rounds ->
                                    viewModelScope.launch {
                                        val roundsFiltered = rounds.filter { round ->
                                            stagesFiltered.any { stage -> stage.id == round.stage_id }
                                        }
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
                                val filterByRoundId =
                                    it[0].rounds?.filter { schedule -> schedule.name == round }
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
                    _loadingMatches.value = false
                    _showMatchesRetry.value = true
                }
            }
        }
    }

    fun savePronostico(matchId: Int, localGoals: Int, visitorGoals: Int, winner: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val prodeResult = ProdeResult(
                matchId = matchId,
                localGoals = localGoals,
                visitorGoals = visitorGoals,
                winner = winner
            )

            val existingMatch = prode_database.prodeResultDao().getProdeResult(matchId)

            if (existingMatch != null) {
                prode_database.prodeResultDao().updateProdeResult(
                    matchId,
                    localGoals,
                    visitorGoals,
                    winner
                )
            } else {
                prode_database.prodeResultDao().insert(prodeResult)
            }
        }
    }

    private fun getScoreFromDataStore() {
        viewModelScope.launch {
            getFromDataStore(context, PreferencesKeys.SCORE_DATA).collect {
                _score.value = it ?: 0
            }
        }
    }

    fun saveScoreToDataStore(score: Int) {
        viewModelScope.launch {
            _score.value = score
            saveToDataStore(context, score, PreferencesKeys.SCORE_DATA)
        }
    }
    fun sumScore() {
        viewModelScope.launch {
            val allMatchesLoaded = loadAllExistingMatches()

            if (allMatchesLoaded) {
                _score.value = 0
                prode_database.prodeResultDao().getAllProdeResults().collect { resultList ->
                    if (resultList.isNotEmpty()) {
                        for (result in resultList) {
                            val fixture = _alreadyPlayedMatches.value
                                .flatMap { it.fixtures }
                                .find { it.id == result.matchId }

                            if (fixture != null) {
                                Log.i("Fixture", fixture.toString())
                                val scoreTeam1 = fixture.scores?.getOrNull(0)?.score
                                val scoreTeam2 = fixture.scores?.getOrNull(1)?.score

                                val winner = if (scoreTeam1 != null && scoreTeam2 != null) {
                                    when {
                                        scoreTeam1.goals > scoreTeam2.goals -> "local"
                                        scoreTeam1.goals < scoreTeam2.goals -> "visitor"
                                        else -> "draw"
                                    }
                                } else {
                                    null
                                }

                                if (winner == result.winner) {
                                    if (result.localGoals == scoreTeam1?.goals && result.visitorGoals == scoreTeam2?.goals) {
                                        _score.value += 3
                                    } else {
                                        _score.value += 1
                                    }
                                    saveScoreToDataStore(_score.value)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    private suspend fun loadAllExistingMatches(): Boolean = withContext(Dispatchers.IO) {
        try {
            val leagues = prode_database.leagueDao().getAllLeagues().asFlow().first()

            _alreadyPlayedMatches.emit(emptyList())

            val loadJobs = leagues.map { league ->
                async {
                    loadExistingMatches(league.name)
                }
            }

            loadJobs.awaitAll()
            true
        } catch (e: Exception) {
            false
        }
    }

    private suspend fun loadExistingMatches(league: String) {
        withContext(Dispatchers.IO) {
            val leaguesList = prode_database.leagueDao().getAllLeagues().asFlow().first()
            val season = leaguesList.find { it.name == league }

            if (season != null) {
                suspendCoroutine<Unit> { continuation ->
                    apiService.getSchedule(
                        seasonId = season.seasonId,
                        context = context,
                        onSuccess = { scheduleData ->
                            val playedRounds = scheduleData[0].rounds?.filter { it.finished } ?: emptyList()
                            // Solo agarra los rounds que terminaron: es decir que si se esta jugando no se suma a los puntos,
                            // pero si entras al partido particular si podes ver el resultado en tiempo real,
                            // solamente no se suma a los puntos hasta que temrine la fecha entera

                            _alreadyPlayedMatches.tryEmit(_alreadyPlayedMatches.value + playedRounds)

                            continuation.resume(Unit)
                        },
                        onFail = {
                            _showMatchesRetry.value = true
                            continuation.resume(Unit)
                        },
                        loadingFinished = {
                            _loadingMatches.value = false
                        }
                    )
                }
            } else {
                _loadingMatches.value = false
                _showMatchesRetry.value = true
            }
        }
    }


}