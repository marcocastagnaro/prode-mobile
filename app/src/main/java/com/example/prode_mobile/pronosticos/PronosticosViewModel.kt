package com.example.prode_mobile.pronosticos

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prode_mobile.api_calls.ApiServiceImpl
import com.example.prode_mobile.leagues.LeagueData
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
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

    init {
        loadRounds()
    }

    fun retryLoadingRounds() {
        loadRounds()
    }

    fun retryLoadingMatches(roundId: String) {
        loadMatches(roundId)
    }
    private fun loadRounds() {
        _loadingRounds.value = true

        var stagesFiltered = listOf<StageData>()

        apiService.getStages(
            seasonId = 23584,
            context = context,
            onSuccess = {
                viewModelScope.launch {
                    Log.i("PronosticosViewModel", "Stages: ${it.map { it.id }}")
                    stagesFiltered = it.filter { stage ->
                        stage.is_current && !stage.type.name.toLowerCase(Locale.ROOT).contains("knock")
                    }
                    _stagesList.emit(stagesFiltered)
                }

                apiService.getRoundsList(
                    seasonId = 23584,
                    context = context,
                    onSuccess = {
                        viewModelScope.launch {
                            Log.i("PronosticosViewModel", "Rounds: ${it.map { it.id }}")

                            val roundsFiltered = it.filter { round ->
                                stagesFiltered.any { stage -> stage.id == round.stage_id }
                            }
                            Log.i("PronosticosViewModel", "Rounds filtered: ${roundsFiltered.map { it.id }}")

                            _roundsList.emit(roundsFiltered.sortedBy { it.name.toInt() })
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

    }

    private fun loadMatches(roundId: String) {
        _loadingMatches.value = true
        apiService.getSchedule(
            seasonId = 23584,
            context = context,
            onSuccess = {
                viewModelScope.launch {
                    Log.i("ScheduleModel", "Schedule: ${it.map { it.id }}")
                    val filterByRoundId = it[0].rounds?.filter { schedule -> schedule.name == roundId }
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
    }
}