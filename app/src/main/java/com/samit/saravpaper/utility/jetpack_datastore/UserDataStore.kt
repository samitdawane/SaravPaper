package com.samit.saravpaper.utility.jetpack_datastore

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserDataStore(
    context: Context
) {
    private val applicationContext = context.applicationContext
    private val dataStore: DataStore<Preferences>

    init {
        dataStore = applicationContext.createDataStore(
            name = "app_preferences"
        )
    }

    val name: Flow<String?>
        get() = dataStore.data.map { preferences ->
            preferences[KEY_NAME]
        }

    suspend fun setName(name: String) {
        dataStore.edit { preferences ->
            preferences[KEY_NAME] = name
        }
    }


    val langauge: Flow<String?>
        get() = dataStore.data.map { preferences ->
            preferences[KEY_LANGUAGE]
        }

    suspend fun setLanguage(language: String) {
        dataStore.edit { preferences ->
            preferences[KEY_LANGUAGE] = language
        }
    }

    val standard: Flow<String?>
        get() = dataStore.data.map { preferences ->
            preferences[KEY_STANDARD]
        }

    suspend fun setStandard(standard: String) {
        dataStore.edit { preferences ->
            preferences[KEY_STANDARD] = standard
        }
    }

    val standardMar: Flow<String?>
        get() = dataStore.data.map { preferences ->
            preferences[KEY_STANDARD_MAR]
        }

    suspend fun setStandardMar(standardMar: String) {
        dataStore.edit { preferences ->
            preferences[KEY_STANDARD_MAR] = standardMar
        }
    }

    val standardCode: Flow<String?>
        get() = dataStore.data.map { preferences ->
            preferences[KEY_STANDARD_CODE]
        }

    suspend fun setStandardCode(standardCode: String) {
        dataStore.edit { preferences ->
            preferences[KEY_STANDARD_CODE] = standardCode
        }
    }

    companion object {

        val KEY_NAME = preferencesKey<String>("key_name")

        val KEY_LANGUAGE = preferencesKey<String>("key_language")
        val KEY_STANDARD = preferencesKey<String>("key_statandard")
        val KEY_STANDARD_MAR = preferencesKey<String>("key_statandard_mar")
        val KEY_STANDARD_CODE = preferencesKey<String>("key_statandard_code")

    }
}