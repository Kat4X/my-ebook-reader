package com.kat4x.myebookreader

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import androidx.multidex.MultiDex
import kotlinx.coroutines.flow.first
import timber.log.Timber

class BaseApplication : Application() {


    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        sp = getSharedPreferences("App", Context.MODE_PRIVATE)

        dataStore = createDataStore(name = "settings")

        Timber.plant(Timber.DebugTree())
    }

    companion object {
        lateinit var sp: SharedPreferences
        lateinit var dataStore: DataStore<Preferences>

        suspend fun saveBookIndex(key: String, value: Int) {
            val dataStoreKey = preferencesKey<Int>(key)
            dataStore.edit { settings ->
                settings[dataStoreKey] = value
            }
        }

        suspend fun readBookIndex(key: String): Int {
            val dataStoreKey = preferencesKey<Int>(key)
            val preferences = dataStore.data.first()
            return preferences[dataStoreKey] ?: 0
        }
    }
}