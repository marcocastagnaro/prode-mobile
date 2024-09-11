package com.example.prode_mobile.leagues

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prode_mobile.api_calls.ApiServiceImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LeagueViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val apiService: ApiServiceImpl
) : ViewModel() {
    private val _loadingLeagues = MutableStateFlow(false)
    val loadingLeagues = _loadingLeagues.asStateFlow()

    private var _leaguesList = MutableStateFlow(listOf<LeagueData>())
    val leaguesList = _leaguesList.asStateFlow()

    val _allLeaguesList = MutableStateFlow(listOf<LeagueData>())
    val allLeaguesList = _allLeaguesList.asStateFlow()

    val _seasonList = MutableStateFlow(listOf<SeasonData>())
    val seasonList = _seasonList.asStateFlow()
    val _unavailableLeaguesList = MutableStateFlow(listOf<LeagueData>())
    val unavailableLeaguesList = _unavailableLeaguesList.asStateFlow()

    private val _showRetry = MutableStateFlow(false)
    val showRetry = _showRetry.asStateFlow()

    init {
        loadLeagues()
    }

    fun retryLoadingLeagues() {
        loadLeagues()
    }

    fun addNewLeague (id : Int, country_id : Int, name : String, active : Boolean, image_path : String, category : Int) {
        val newLeague = LeagueData(id, country_id, name, active, image_path, category)
        val newList = _leaguesList.value + newLeague
        viewModelScope.launch {
            _leaguesList.emit(newList)
        }

        val updatedAllLeaguesList = _allLeaguesList.value.filterNot { it.id == id }
        viewModelScope.launch {
            _allLeaguesList.emit(updatedAllLeaguesList)
        }

    }
    fun delLeague (id : Int, country_id : Int, name : String, active : Boolean, image_path : String, category : Int) {
        val newList = _leaguesList.value.filter { it.id != id }
        viewModelScope.launch {
            _leaguesList.emit(newList)
        }

        val newLeague = LeagueData(id, country_id, name, active, image_path, category)
        val updatedAllLeaguesList = _allLeaguesList.value + newLeague
        viewModelScope.launch {
            _allLeaguesList.emit(updatedAllLeaguesList)
        }
    }
    fun mockUnavailableLeagues() {
        val mockLeagues = listOf(
            LeagueData(
                id = 1,
                country_id = 10,
                name = "Premier League",
                active = true,
                image_path = "https://cdn.sportmonks.com/images/soccer/leagues/271.png",
                category = 1
            ),
            LeagueData(
                id = 2,
                country_id = 20,
                name = "La Liga",
                active = true,
                image_path = "https://cdn.sportmonks.com/images/soccer/leagues/271.png",
                category = 1
            ),
            LeagueData(
                id = 3,
                country_id = 30,
                name = "Serie A",
                active = true,
                image_path = "https://cdn.sportmonks.com/images/soccer/leagues/271.png",
                category = 1
            ),
            LeagueData(
                id = 4,
                country_id = 40,
                name = "Bundesliga",
                active = true,
                image_path = "https://cdn.sportmonks.com/images/soccer/leagues/271.png",
                category = 1
            ),
            LeagueData(
                id = 5,
                country_id = 50,
                name = "Ligue 1",
                active = true,
                image_path = "https://cdn.sportmonks.com/images/soccer/leagues/271.png",
                category = 1
            )
        )
        viewModelScope.launch {
            _unavailableLeaguesList.emit(mockLeagues)
        }
    }

    private fun loadLeagues() {
        _loadingLeagues.value = true

        apiService.getSeasons(
            context = context,
            onSuccess = { seasons ->
                viewModelScope.launch {
                    _seasonList.emit(seasons)
//´

                }
                _showRetry.value = false

                apiService.getLeagues(
                    context = context,
                    onSuccess = { leagues ->
                        viewModelScope.launch {
                            val currentSeasons = _seasonList.value
                            val filteredLeagues = leagues.filter { league ->
                                currentSeasons.any { season -> season.league_id == league.id }
                            }
                            _allLeaguesList.emit(filteredLeagues)
                        }
                        _showRetry.value = false
                    },
                    onFail = {
                        _showRetry.value = true
                    },
                    loadingFinished = {
                        _loadingLeagues.value = false
                    }
                )
            },
            onFail = {
                _showRetry.value = true
            },
            loadingFinished = {
                _loadingLeagues.value = false
            }
        )
    }
}