package com.example.prode_mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.prode_mobile.navigation.BottomBar
import com.example.prode_mobile.navigation.NavHostComposable
import com.example.prode_mobile.navigation.TopBar
import com.example.prode_mobile.ui.theme.ProdemobileTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            ProdemobileTheme{
                Surface (modifier = Modifier.fillMaxSize(),color = MaterialTheme.colorScheme.background) {
                    Scaffold(
                        topBar = {
                            TopBar(
                                navController = navController,
                            )
                        },
                        bottomBar = {
                            BottomBar { navController.navigate(it) }
                        },
                    ) { innerPadding ->
                        NavHostComposable(innerPadding, navController)
                    }
                }
            }
        }
    }
}
