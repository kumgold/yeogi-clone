package com.example.yeogi.core.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.yeogi.core.model.RecentSearch
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

val Context.recentSearchDataStore: DataStore<Preferences>
    by preferencesDataStore(name = "recent_searches")

class RecentSearchRepository(private val context: Context) {
    private val gson = Gson()

    private val DOMESTIC_RECENT_SEARCH_KEY = stringPreferencesKey("domestic_recent_searches")

    suspend fun getDomesticRecentSearches(): List<RecentSearch> {
        val json = context.recentSearchDataStore.data.map { preferences ->
            preferences[DOMESTIC_RECENT_SEARCH_KEY] ?: "[]"
        }.first()
        return gson.fromJson(json, object : TypeToken<List<RecentSearch>>() {}.type)
    }

    suspend fun addDomesticRecentSearch(newSearch: RecentSearch) {
        context.recentSearchDataStore.edit { preferences ->
            val currentSearches = getDomesticRecentSearches().toMutableList()
            // 중복 방지
            val existingIndex = currentSearches.indexOfFirst { it.keyword == newSearch.keyword }
            if (existingIndex != -1) {
                currentSearches.removeAt(existingIndex)
            }

            // 최신 검색을 맨 앞에 추가
            currentSearches.add(0, newSearch)

            // 최대 5개 유지
            while (currentSearches.size > 5) {
                currentSearches.removeAt(currentSearches.lastIndex)
            }
            preferences[DOMESTIC_RECENT_SEARCH_KEY] = gson.toJson(currentSearches)
        }
    }

    suspend fun removeDomesticRecentSearch(id: Int) {
        context.recentSearchDataStore.edit { preferences ->
            val currentSearches = getDomesticRecentSearches().toMutableList()
            val updatedSearches = currentSearches.filter { it.id != id }
            preferences[DOMESTIC_RECENT_SEARCH_KEY] = gson.toJson(updatedSearches)
        }
    }

    suspend fun clearDomesticRecentSearches() {
        context.recentSearchDataStore.edit { preferences ->
            preferences[DOMESTIC_RECENT_SEARCH_KEY] = "[]"
        }
    }

    // Overseas Recent Searches
    private val OVERSEAS_RECENT_SEARCH_KEY = stringPreferencesKey("overseas_recent_searches")

    suspend fun getOverseasRecentSearches(): List<RecentSearch> {
        val json = context.recentSearchDataStore.data.map { preferences ->
            preferences[OVERSEAS_RECENT_SEARCH_KEY] ?: "[]"
        }.first()
        return gson.fromJson(json, object : TypeToken<List<RecentSearch>>() {}.type)
    }

    suspend fun addOverseasRecentSearch(newSearch: RecentSearch) {
        context.recentSearchDataStore.edit { preferences ->
            val currentSearches = getOverseasRecentSearches().toMutableList()
            // 중복 방지
            val existingIndex = currentSearches.indexOfFirst { it.keyword == newSearch.keyword }
            if (existingIndex != -1) {
                currentSearches.removeAt(existingIndex)
            }

            // 최신 검색을 맨 앞에 추가
            currentSearches.add(0, newSearch)

            // 최대 5개 유지 (원하는 개수로 조절 가능)
            while (currentSearches.size > 5) {
                currentSearches.removeAt(currentSearches.lastIndex)
            }
            preferences[OVERSEAS_RECENT_SEARCH_KEY] = gson.toJson(currentSearches)
        }
    }

    suspend fun removeOverseasRecentSearch(id: Int) {
        context.recentSearchDataStore.edit { preferences ->
            val currentSearches = getOverseasRecentSearches().toMutableList()
            val updatedSearches = currentSearches.filter { it.id != id }
            preferences[OVERSEAS_RECENT_SEARCH_KEY] = gson.toJson(updatedSearches)
        }
    }

    suspend fun clearOverseasRecentSearches() {
        context.recentSearchDataStore.edit { preferences ->
            preferences[OVERSEAS_RECENT_SEARCH_KEY] = "[]"
        }
    }
}