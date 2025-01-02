package com.dev.fetchtest.cache

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.dev.fetchtest.network.model.response.DataResponse
import com.dev.fetchtest.repository.models.DataModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import timber.log.Timber

internal class CacheRepository(
    private val dataStore: DataStore<Preferences>,
    private val json: Json
) : CacheRepositoryInterface {
    override fun getListData(): Flow<List<DataModel>?> = dataStore.data.map { prefs ->
        json.fromJsonDataStore<List<DataModel>>(prefs[Keys.LIST_DATA])
    }

    override suspend fun putListData(value: List<DataModel>) {
        withContext(Dispatchers.IO) {
            dataStore.edit { prefs ->
                prefs[Keys.LIST_DATA] = json.encodeToString(value)
            }
        }
    }

    private inline fun <reified T> Json.fromJsonDataStore(dataStoreValue: String?): T? =
        dataStoreValue?.takeIf { it.isNotBlank() }?.let {
            try {
                decodeFromString<T>(it)
            } catch (e: Exception) {
                Timber.e("Json cache parsing error ${e.message}")
                null
            }
        }

    private object Keys {
        val LIST_DATA = stringPreferencesKey("list_data")
    }
}