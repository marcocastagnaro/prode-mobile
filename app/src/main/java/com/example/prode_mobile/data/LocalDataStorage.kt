package com.example.prode_mobile.data
import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "PRODE_DATA_STORE")

object PreferencesKeys {
    val SCORE_DATA = intPreferencesKey("score_data")
    val EXACT_SCORE_DATA = intPreferencesKey("exact_score_data")
    val MID_SCORE_DATA = intPreferencesKey("mid_score_data")
    val WRONG_SCORE_DATA = intPreferencesKey("wrong_score_data")
}

suspend fun <T> saveToDataStore(context: Context, value: T, key: Preferences.Key<T>) {
    context.dataStore.edit { preferences ->
        preferences[key] = value
    }
}

fun <T> getFromDataStore(context: Context, key: Preferences.Key<T>): Flow<T?> {
    return context.dataStore.data
        .map { preferences ->
            preferences[key]
        }
}