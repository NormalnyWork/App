package com.normalnywork.plants.ui.navigation.screen

import com.normalnywork.plants.domain.entity.Guide

interface HandbookArticleComponent {

    val guide: Guide

    fun goBack()
}