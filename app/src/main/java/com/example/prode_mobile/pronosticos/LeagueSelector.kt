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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.prode_mobile.R
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
            .padding(dimensionResource(id = R.dimen.extra_large_padding), 12.dp)
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
@Preview
@Composable
fun LeagueSelectorPreview() {
    LeagueSelector(
        leagues = listOf(
            LeagueAndSeason(1, 1, 1, "League 1", true, "https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png", 1, 1),
            LeagueAndSeason(2, 1, 1, "League 2", true, "https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png", 1, 1),
            LeagueAndSeason(3, 1, 1, "League 3", true, "https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png", 1, 1)
        ),
        onLeagueSelected = { /* No-op for preview */ }
    )
}