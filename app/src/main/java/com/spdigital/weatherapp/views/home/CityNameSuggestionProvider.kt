package com.spdigital.weatherapp.views.home

import android.content.SearchRecentSuggestionsProvider


class CityNameSuggestionProvider : SearchRecentSuggestionsProvider() {
    init {
        setupSuggestions(AUTHORITY, MODE)
    }

    companion object {
        val AUTHORITY = "com.spdigital.weatherapp.views.home.CityNameSuggestionProvider"
        val MODE = DATABASE_MODE_QUERIES or DATABASE_MODE_2LINES
    }
}
