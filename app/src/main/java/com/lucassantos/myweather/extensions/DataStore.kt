package com.lucassantos.myweather.extensions

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import com.lucassantos.myweather.utils.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

/**
 * PT-BR: Extensões para o uso do datastore.
 * EN: Extensions for using the datastore.
 */

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = Constants.PREFERENCES.NAME
)

fun AppCompatActivity.saveSettingsInDataStore(key: String, value: String) {
    val keyPreferences = stringPreferencesKey(key)
    lifecycleScope.launch {
        dataStore.edit {
            it[keyPreferences] = value
        }
    }
}

fun AppCompatActivity.readSettingsInDataStore(key: String): Flow<String> {
    val keyPreferences = stringPreferencesKey(key)
    return dataStore.data.map {
        it[keyPreferences] ?: Constants.PREFERENCES.NO_PREFERENCES
    }
}