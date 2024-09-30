package com.example.prode_mobile.score

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prode_mobile.data.PreferencesKeys
import com.example.prode_mobile.data.getFromDataStore
import com.example.prode_mobile.data.saveToDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class ScoreAndProfileViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _score = MutableStateFlow(0)
    val score = _score.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _showRetry = MutableStateFlow(false)
    val showRetry = _showRetry.asStateFlow()

    private val _username = MutableStateFlow("")
    val username = _username.asStateFlow()

    private val _age = MutableStateFlow("")
    val age = _age.asStateFlow()

    private val _country = MutableStateFlow("")
    val country = _country.asStateFlow()

    init {
        getScoreFromDataStore()
        getUsernameFromDataStore()
        getAgeFromDataStore()
        getCountryFromDataStore()
    }

    private fun getScoreFromDataStore() {
        viewModelScope.launch {
            getFromDataStore(context, PreferencesKeys.SCORE_DATA).collect {
                _score.value = it ?: 0
            }
        }
    }

    private fun getUsernameFromDataStore() {
        viewModelScope.launch {
            getFromDataStore(context, PreferencesKeys.USERNAME).collect { storedUsername ->
                if (storedUsername.isNullOrEmpty()) {
                    saveUsernameToDataStore("Ingrese tu nombre de usuario")
                } else {
                    _username.value = storedUsername
                }
            }
        }
    }

    private fun getAgeFromDataStore() {
        viewModelScope.launch {
            getFromDataStore(context, PreferencesKeys.AGE).collect { storedAge ->
                if (storedAge.isNullOrEmpty()) {
                    saveAgeToDataStore("Ingrese tu edad")
                } else {
                    _age.value = storedAge
                }
            }
        }
    }

    private fun getCountryFromDataStore() {
        viewModelScope.launch {
            getFromDataStore(context, PreferencesKeys.COUNTRY).collect { storedCountry ->
                if (storedCountry.isNullOrEmpty()) {
                    saveCountryToDataStore("Ingrese tu país")
                } else {
                    _country.value = storedCountry
                }
            }
        }
    }

    fun saveUsernameToDataStore(newUsername: String) {
        viewModelScope.launch {
            saveToDataStore(context,newUsername, PreferencesKeys.USERNAME)
            _username.value = newUsername
        }
    }

    fun saveAgeToDataStore(newAge: String) {
        viewModelScope.launch {
            saveToDataStore(context, newAge,PreferencesKeys.AGE)
            _age.value = newAge
        }
    }

    fun saveCountryToDataStore(newCountry: String) {
        viewModelScope.launch {
            saveToDataStore(context, newCountry, PreferencesKeys.COUNTRY)
            _country.value = newCountry
        }
    }
}
