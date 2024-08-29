package com.example.prode_mobile.score

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun ScoreAndProfile() {
    Surface (modifier = Modifier.fillMaxSize()) {
        Column {

            Text(
                text = "Score & Profile", style = TextStyle(
                    fontSize = 20.sp,
                    fontFamily = FontFamily.Serif,
                ), modifier = Modifier.padding(16.dp)
            )

            Divider(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            )
            Profile()
        }
    }
}


@Preview
@Composable
fun ScorePreview() {
    Score()
}