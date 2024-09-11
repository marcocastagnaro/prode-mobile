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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.prode_mobile.leagues.LeagueViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateSelector(dates: List<FechaSelector>, isDateAndLeagueSelected :() -> Unit) {

    val context = LocalContext.current
    val dateNames = dates.map { date -> date.nroFecha }
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(dateNames[0]) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp, 0.dp)
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            TextField(
                value = selectedText.toString(),
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                dateNames.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item.toString()) },
                        onClick = {
                            selectedText = item
                            expanded = false
                            isDateAndLeagueSelected()
                            Toast.makeText(context, item.toString(), Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }
        }
    }
}