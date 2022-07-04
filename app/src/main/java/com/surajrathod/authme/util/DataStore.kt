package com.surajrathod.authme.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import kotlin.coroutines.coroutineContext

object DataStore {
    private const val PREFERENCES_AUTH = "auth"
    const val JWT_TOKEN = "jwt"
    val Context.preferenceDataStoreAuth : DataStore<Preferences> by preferencesDataStore(PREFERENCES_AUTH)
}