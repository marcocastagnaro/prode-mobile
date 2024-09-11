package com.example.prode_mobile.pronosticos

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.prode_mobile.ui.theme.TitleColor

@Composable
fun MatchNameAndImage (urlImage: String, name: String, modifier: Modifier) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier) {
        Text(
            text = name,
            style = TextStyle(
                fontWeight = FontWeight.Black,
                color = TitleColor,
                fontSize = 10.sp,
                fontFamily = FontFamily.SansSerif
            ),
            modifier = Modifier.padding(bottom = 4.dp)
        )
        AsyncImage(model = urlImage, contentDescription = "", modifier= Modifier.size(50.dp)) //Place holder para cuando la imagen carga y error cuando falla
    }
}