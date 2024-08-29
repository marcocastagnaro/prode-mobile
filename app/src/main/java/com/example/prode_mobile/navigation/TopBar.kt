package com.example.prode_mobile.navigation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.SportsSoccer
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar (    navController : NavHostController) {
    val currentStackEntry by navController.currentBackStackEntryAsState()

    val showBackButton by remember(currentStackEntry) {
        derivedStateOf {
            val currentRoute = navController.currentDestination?.route
            navController.previousBackStackEntry != null && currentRoute != null && !basePages.contains(currentRoute)
        }
    }
    TopAppBar(
    navigationIcon = {
        Icon(imageVector = if (showBackButton) Icons.AutoMirrored.Filled.ArrowBack else Icons.Filled.SportsSoccer , contentDescription = "", modifier = Modifier
            .padding(20.dp)
            .clickable {
                if (showBackButton) {
                    navController.popBackStack()
                }
            })
    },
        title = {
            Text(text = "") },
        actions = {
            Row (horizontalArrangement = Arrangement.End, modifier = Modifier.padding(20.dp)) {
                BadgedBox(badge = {}) {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = "",
                        modifier = Modifier.clickable { navController.navigate(ProdeScreen.Score.name) })
                }
            }
        }
    )
}