package com.example.prode_mobile.leagues

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LeagueViewModel @Inject constructor() : ViewModel() {
    private var _leaguesList = MutableStateFlow(listOf<LeagueData>())
    val leaguesList = _leaguesList.asStateFlow()
    val _allLeaguesList = MutableStateFlow(listOf<LeagueData>())
    val allLeaguesList = _allLeaguesList.asStateFlow()

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
    fun mockAllLeagues() {
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
            _allLeaguesList.emit(mockLeagues)
        }
    }
}