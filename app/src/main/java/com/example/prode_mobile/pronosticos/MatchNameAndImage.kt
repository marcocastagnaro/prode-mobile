package com.example.prode_mobile.pronosticos

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.prode_mobile.R
import com.example.prode_mobile.ui.theme.TitleBlueColor

@Composable
fun MatchNameAndImage (urlImage: String, name: String, modifier: Modifier) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier) {
        Text(
            text = name,
            style = TextStyle(
                fontWeight = FontWeight.Black,
                color = MaterialTheme.colorScheme.secondary,
                fontSize = 10.sp,
                fontFamily = FontFamily.SansSerif
            ),
            modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.small_padding))
        )
        AsyncImage(
            model = urlImage,
            contentDescription = "",
            modifier = Modifier.size(dimensionResource(id = R.dimen.team_image_size))
        ) //Place holder para cuando la imagen carga y error cuando falla
    }
}
@Preview
@Composable
fun MatchNameAndImagePreview() {
    MatchNameAndImage("https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png", "Argentina", Modifier.size(100.dp))
}