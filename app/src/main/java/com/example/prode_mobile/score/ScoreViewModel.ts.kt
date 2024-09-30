package com.example.prode_mobile.score

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prode_mobile.api_calls.ApiServiceImpl
import com.example.prode_mobile.data.PreferencesKeys
import com.example.prode_mobile.data.getFromDataStore
import com.example.prode_mobile.data.saveToDataStore
import com.example.prode_mobile.pronosticos.PronosticosViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScoreViewModel  @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _score = MutableStateFlow(0)
    val score = _score.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _showRetry = MutableStateFlow(false)
    val showRetry = _showRetry.asStateFlow()

    private fun getScoreFromDataStore() {
        viewModelScope.launch {
            getFromDataStore(context, PreferencesKeys.SCORE_DATA).collect {
                _score.value = it ?: 0
            }
        }
    }

    init {
        getScoreFromDataStore()
    }

}