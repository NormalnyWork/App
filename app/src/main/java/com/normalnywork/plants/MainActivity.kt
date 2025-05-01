package com.normalnywork.plants

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.remember
import com.arkivanov.decompose.defaultComponentContext
import com.normalnywork.plants.ui.kit.style.AppTheme
import com.normalnywork.plants.ui.navigation.impl.RootComponentImpl
import com.normalnywork.plants.ui.navigation.impl.RootContent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                val rootComponent = remember { RootComponentImpl(defaultComponentContext()) }

                RootContent(component = rootComponent)
            }
        }
    }
}