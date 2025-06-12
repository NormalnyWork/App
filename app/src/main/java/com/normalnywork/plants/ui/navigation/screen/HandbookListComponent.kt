package com.normalnywork.plants.ui.navigation.screen

import com.normalnywork.plants.domain.entity.Guide
import kotlinx.coroutines.flow.StateFlow

interface HandbookListComponent {

    val guides: StateFlow<List<Guide>>

    val searchRequest: StateFlow<String>

    fun onSearchRequestChanged(newSearchRequest: String)

    fun openGuide(guide: Guide)
}