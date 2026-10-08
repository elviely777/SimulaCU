package com.simulacu.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.simulacu.app.presentation.navigation.SimulaCUNavHost
import com.simulacu.app.presentation.theme.SimulaCUTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SimulaCUTheme {
                SimulaCUNavHost()
            }
        }
    }
}