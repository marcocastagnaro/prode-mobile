package com.example.prode_mobile.pronosticos

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.prode_mobile.data.LeagueAndSeason
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeagueSelector(leagues: List<LeagueAndSeason>, onLeagueSelected: (String) -> Unit) {
    val leagueNames = leagues.map { l -> l.name }

    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(if (leagueNames.isNotEmpty()) leagueNames[0] else "") }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp, 12.dp)
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            TextField(
                value = selectedText,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                leagueNames.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item) },
                        onClick = {
                            selectedText = item
                            expanded = false
                            onLeagueSelected(item)
                        }
                    )
                }
            }
        }
    }
}
