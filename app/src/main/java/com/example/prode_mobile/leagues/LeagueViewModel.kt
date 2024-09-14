package com.example.prode_mobile.leagues

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.example.prode_mobile.api_calls.ApiServiceImpl
import com.example.prode_mobile.data.LeagueAndSeason
import com.example.prode_mobile.data.ProdeMobileDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
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

    val _allLeaguesList = MutableStateFlow(listOf<LeagueData>())
    val allLeaguesList = _allLeaguesList.asStateFlow()

    val _seasonList = MutableStateFlow(listOf<SeasonData>())
    val seasonList = _seasonList.asStateFlow()

    val _unavailableLeaguesList = MutableStateFlow(listOf<LeagueData>())
    val unavailableLeaguesList = _unavailableLeaguesList.asStateFlow()

    private val _showRetry = MutableStateFlow(false)
    val showRetry = _showRetry.asStateFlow()

    private val prode_database = ProdeMobileDatabase.getDatabase(context)
    val leaguesAndSeasonList = prode_database.leagueDao().getAllLeagues().asFlow()

    init {
        loadLeagues()
    }

    fun retryLoadingLeagues() {
        loadLeagues()
    }

    fun addNewLeague(id: Int, country_id: Int, name: String, active: Boolean, image_path: String, category: Int, seasonId: Int) {
        val newLeague = LeagueAndSeason(
            league_id = id,
            country_id = country_id,
            name = name,
            active = active,
            image_path = image_path,
            category = category,
            seasonId = seasonId
        )

        viewModelScope.launch {
            try {
                prode_database.leagueDao().insert(newLeague)
                val updatedLeaguesList = _allLeaguesList.value.filter { it.id != id }
                _allLeaguesList.emit(updatedLeaguesList)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    fun delLeague(id: Int) {
        viewModelScope.launch {
            val currentList = leaguesAndSeasonList.first()

            val newList = currentList.filter { it.league_id != id }

            val leagueToDelete = currentList.find { it.league_id == id }

            if (leagueToDelete != null) {
                prode_database.leagueDao().delete(leagueToDelete)
            }
            val newLeague = (newList.map { league ->
                LeagueData(
                    id = league.league_id,
                    country_id = league.country_id,
                    name = league.name,
                    active = league.active,
                    image_path = league.image_path,
                    category = league.category,
                    seasonId = league.seasonId
                )
            })
            retryLoadingLeagues()
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
                category = 1,
                seasonId = 23584

            ),
            LeagueData(
                id = 2,
                country_id = 20,
                name = "La Liga",
                active = true,
                image_path = "https://cdn.sportmonks.com/images/soccer/leagues/271.png",
                category = 1,
                seasonId = 23584
            ),
            LeagueData(
                id = 3,
                country_id = 30,
                name = "Serie A",
                active = true,
                image_path = "https://cdn.sportmonks.com/images/soccer/leagues/271.png",
                category = 1,
                seasonId = 23584

            ),
            LeagueData(
                id = 4,
                country_id = 40,
                name = "Bundesliga",
                active = true,
                image_path = "https://cdn.sportmonks.com/images/soccer/leagues/271.png",
                category = 1,
                seasonId = 23584

            ),
            LeagueData(
                id = 5,
                country_id = 50,
                name = "Ligue 1",
                active = true,
                image_path = "https://cdn.sportmonks.com/images/soccer/leagues/271.png",
                category = 1,
                seasonId = 23584

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
                            val filteredLeagues = leagues.map { league ->
                                val matchingSeason = currentSeasons.find { season -> season.league_id == league.id }

                                if (matchingSeason != null) {
                                    league.copy(seasonId = matchingSeason.id)
                                } else {
                                    league
                                }
                            }

                            val notRepeatedLeagues = filteredLeagues.filter { league ->
                                leaguesAndSeasonList.first().none { it.league_id == league.id }
                            }

                            _allLeaguesList.emit(notRepeatedLeagues)
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