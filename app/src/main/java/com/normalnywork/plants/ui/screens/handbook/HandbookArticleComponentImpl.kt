package com.normalnywork.plants.ui.screens.handbook

import com.arkivanov.decompose.ComponentContext
import com.normalnywork.plants.domain.entity.Guide
import com.normalnywork.plants.ui.navigation.screen.HandbookArticleComponent

class HandbookArticleComponentImpl(
    componentContext: ComponentContext,
    private val guide: Guide,
) : HandbookArticleComponent, ComponentContext by componentContext