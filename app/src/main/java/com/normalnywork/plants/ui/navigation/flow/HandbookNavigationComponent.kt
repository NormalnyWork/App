package com.normalnywork.plants.ui.navigation.flow

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.normalnywork.plants.ui.navigation.screen.HandbookArticleComponent
import com.normalnywork.plants.ui.navigation.screen.HandbookListComponent
import kotlinx.serialization.Serializable

interface HandbookNavigationComponent {

    val childStack: Value<ChildStack<*, HandbookScreen>>

    @Serializable
    sealed interface HandbookConfig {

        @Serializable
        data object List : HandbookConfig

        @Serializable
        data class Article(val id: Int) : HandbookConfig
    }

    sealed class HandbookScreen {

        class List(val component: HandbookListComponent) : HandbookScreen()

        class Article(val component: HandbookArticleComponent) : HandbookScreen()
    }
}