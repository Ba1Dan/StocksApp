package com.baiganov.stocksapp

import android.content.SearchRecentSuggestionsProvider

class SuggestionProvider : SearchRecentSuggestionsProvider() {

    init {
        setupSuggestions(AUTHORITY, MODE)
    }

    companion object {
        const val AUTHORITY = "com.example.MySuggestionProvider"
        const val MODE: Int = DATABASE_MODE_QUERIES
    }
}