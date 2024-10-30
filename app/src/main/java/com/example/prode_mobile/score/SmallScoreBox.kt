package com.example.prode_mobile.score

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SmallScoreBox(score: Int, posibleScore: Int, backgroundColor: Color) {
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor), // Establece el color de fondo
        modifier = Modifier.padding(8.dp)
    ) {
        Column {
            Text(
                text = "Score: " + posibleScore.toString(),
                style = TextStyle(
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.surface
                ),
                modifier = Modifier.padding(8.dp)
            )
            Text(
                text = "Matches: " + score.toString(),
                style = TextStyle(
                    fontSize = 16.sp
                ),
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}
@Preview
@Composable
fun SmallScoreBoxPreview() {
    SmallScoreBox(5, 10, Color.Blue)
}