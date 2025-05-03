package com.normalnywork.plants.ui.screens.handbook

import com.arkivanov.decompose.ComponentContext
import com.normalnywork.plants.ui.navigation.screen.HandbookArticleComponent

class HandbookArticleComponentImpl(
    componentContext: ComponentContext,
    private val id: Int,
) : HandbookArticleComponent, ComponentContext by componentContext