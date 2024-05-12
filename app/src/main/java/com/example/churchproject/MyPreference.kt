package com.example.churchproject

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class MyPreference @Inject constructor(private val appContext: Context)  {
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

//    fun getLoginState(): Flow<Boolean> {
//        return dataStore.data.map { preferences ->
//            preferences[LOGIN_STATE] ?: false
//        }
//    }
//
//
//    suspend fun saveLoginState(isDarkModeActive: Boolean) {
//        dataStore.edit { preferences ->
//            preferences[LOGIN_STATE] = isDarkModeActive
//        }
//    }

    fun getToken(): Flow<String> {
        return appContext.dataStore.data.map { preferences ->
            preferences[TOKEN] ?: ""
        }
    }


    suspend fun saveToken(token: String) {
        appContext.dataStore.edit { preferences ->
            preferences[TOKEN] = token
        }
    }




    companion object {
        private val LOGIN_STATE = booleanPreferencesKey("login_state")
        private val TOKEN = stringPreferencesKey("token")
    }
}