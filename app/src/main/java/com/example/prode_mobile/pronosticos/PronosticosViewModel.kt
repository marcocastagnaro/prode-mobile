package com.example.prode_mobile.pronosticos

import androidx.lifecycle.ViewModel
import com.example.prode_mobile.leagues.LeagueData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class PronosticosViewModel @Inject constructor() : ViewModel() {
    private var _roundsList = MutableStateFlow(listOf<LeagueData>())
    val roundsList = _roundsList.asStateFlow()

    //fun get rounds -> api call de conseguir todos los rounds

    private var _matchesList = MutableStateFlow(listOf<MatchData>())
    val matchesList = _matchesList.asStateFlow()

    //fun get matches -> api call de conseguir todos los matches filtrado por el roundId
}