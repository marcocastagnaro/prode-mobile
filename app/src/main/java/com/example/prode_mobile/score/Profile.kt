package com.example.prode_mobile.score

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Profile() {
    cardInfo("Username", "Marco")
    cardInfo(subtitle = "Country", value = "Argentina")
    cardInfo(subtitle = "Age", value = "21")
    Divider(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    )

}
@Composable
fun cardInfo (subtitle: String, value: String) {
    Column (modifier = Modifier.padding(8.dp)){

        Row (horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().padding(20.dp, 4.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "$subtitle: ", style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = FontFamily.Serif,
                        fontStyle = FontStyle.Italic
                    ), modifier = Modifier.padding(8.dp)
                )
                Text(
                    text = value, style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = FontFamily.Serif,
                    ),
                    modifier = Modifier.padding(8.dp)
                )
            }
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Edit",
                tint = Color.Black,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}