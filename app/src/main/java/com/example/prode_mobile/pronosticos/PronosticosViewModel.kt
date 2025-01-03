package com.example.prode_mobile.pronosticos

import android.content.Context
import android.util.Log
import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.example.prode_mobile.R
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.forEach
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@HiltViewModel
class PronosticosViewModel @Inject constructor(
    @ApplicationContext val context: Context,
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

    private var _roundsList = MutableStateFlow(listOf<RoundData>())
    val roundsList = _roundsList.asStateFlow()

    private var _scheduleList = MutableStateFlow(listOf<RoundMatchData>())
    val scheduleList = _scheduleList.asStateFlow()

    private var _roundValue = MutableStateFlow("")
    val roundValue = _roundValue.asStateFlow()

    private val _score = MutableStateFlow(0)
    val score = _score.asStateFlow()

    private val _wrongScore = MutableStateFlow(0)
    val wrongScore = _wrongScore.asStateFlow()

    private val _midScore = MutableStateFlow(0)
    val midScore = _midScore.asStateFlow()

    private val _exactScore = MutableStateFlow(0)
    val exactScore = _exactScore.asStateFlow()

    private val _alreadyPlayedMatches = MutableStateFlow(listOf<RoundMatchData>())

    private val prode_database: ProdeMobileDatabase;

    init {
        prode_database = ProdeMobileDatabase.getDatabase(context)

        viewModelScope.launch {
            sumScore()
            getScoreFromDataStore()
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

//Funcion para conseguir todas las fechas (stages) de esta season -> por eso el season id
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


//Funcion para conseguir todos los partidos de esa fecha
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

    fun savePronostico(matchId: Int, localGoals: Int?, visitorGoals: Int?) {
            if (localGoals == null || visitorGoals == null) {
            Toast.makeText(context, context.getString(R.string.fail_saving_results), Toast.LENGTH_SHORT).show()
            return
        }
        viewModelScope.launch(Dispatchers.IO) {

            val winner = if (localGoals > visitorGoals) "local" else if (localGoals < visitorGoals) "visitor" else "draw"
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
            withContext(Dispatchers.Main) {
                Toast.makeText(context, context.getString(R.string.save_result), Toast.LENGTH_SHORT).show()
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
    fun saveWrongResultsToDataStore() {
        viewModelScope.launch {
            saveToDataStore(context, _wrongScore.value, PreferencesKeys.WRONG_SCORE_DATA)
        }
    }
    fun saveMidResultsToDataStore() {
        viewModelScope.launch {
            saveToDataStore(context, _midScore.value, PreferencesKeys.MID_SCORE_DATA)
        }
    }
    fun saveExactResultsToDataStore() {
        viewModelScope.launch {
            saveToDataStore(context, _exactScore.value, PreferencesKeys.EXACT_SCORE_DATA)
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
                    resultList.forEach { result ->
                        processMatchResult(result)
                    }
                }
            }
            saveExactResultsToDataStore()
            saveMidResultsToDataStore()
            saveWrongResultsToDataStore()
        }
    }

    private fun processMatchResult(result: ProdeResult) {
        val fixture = _alreadyPlayedMatches.value
            .flatMap { it.fixtures }
            .find { it.id == result.matchId }

        fixture?.let {
            val scoreTeam1 = it.scores?.getOrNull(0)?.score
            val scoreTeam2 = it.scores?.getOrNull(1)?.score

            val winner = determineWinner(scoreTeam1, scoreTeam2)

            if (winner == result.winner) {
                _score.value += if (result.localGoals == scoreTeam1?.goals && result.visitorGoals == scoreTeam2?.goals) {
                    3
                } else 1
                if (result.localGoals == scoreTeam1?.goals && result.visitorGoals == scoreTeam2?.goals) {
                    _exactScore.value += 1
                }
                else {
                    _midScore.value += 1
                }
                saveScoreToDataStore(_score.value)
            }
            else {
                _wrongScore.value += 1
            }
        }
    }

    private fun determineWinner(scoreTeam1: ScoreMatchData?, scoreTeam2: ScoreMatchData?): String? {
        return when {
            scoreTeam1 == null || scoreTeam2 == null -> null
            scoreTeam1.goals > scoreTeam2.goals -> "local"
            scoreTeam1.goals < scoreTeam2.goals -> "visitor"
            else -> "draw"
        }
    }
    private suspend fun loadAllExistingMatches(): Boolean {
        return withContext(Dispatchers.IO) {
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
    }


    //Esta funcion la uso para conseguir todos los partidos que ya se jugaron y asi poder sumar el score
    private suspend fun loadExistingMatches(league: String) {
        val leaguesList = prode_database.leagueDao().getAllLeagues().asFlow().first()
        val season = leaguesList.find { it.name == league }

        season?.let {
            suspendCoroutine<Unit> { continuation ->
                apiService.getSchedule(
                    seasonId = season.seasonId,
                    context = context,
                    onSuccess = { scheduleData ->
                        val playedRounds = scheduleData[0].rounds?.filter { it.finished } ?: emptyList()
                        //solo suma los rounds que estan marcados como finished -> es decir que si
                        //se esta jugando la fecha todavia no se ve en el score.
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
        } ?: run {
            _loadingMatches.value = false
            _showMatchesRetry.value = true
        }
    }
}