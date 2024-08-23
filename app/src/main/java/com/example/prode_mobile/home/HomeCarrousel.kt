package com.example.prode_mobile.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SportsSoccer
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.prode_mobile.R
import com.example.prode_mobile.ui.theme.BlueButton
import com.example.prode_mobile.ui.theme.DarkBackground
import com.example.prode_mobile.ui.theme.TitleColor

@Composable
fun HomeCarrousel() {
    Surface (color = DarkBackground, modifier = Modifier.fillMaxSize() ) {
        Column {
            TopTitle()
            MyCarrousel()
        }
    }
}
@Composable
fun TopTitle() {
    Surface (modifier = Modifier
        .fillMaxWidth()
        .height(180.dp), color = BlueButton
    ){
        Row (horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Filled.SportsSoccer,tint = TitleColor, contentDescription = "")
            Text(text = stringResource(id = R.string.home_title), style = TextStyle(
                fontWeight = FontWeight.Bold,
                color = TitleColor,
                fontSize = 32.sp,
                fontFamily = FontFamily.Monospace
            )
            )
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyCarrousel(){
    val PronosticoCarrousel = CarrouselItems("Mis Pronosticos", painterResource(id = R.drawable.futbol_ball))
    val MyLeaguesCarrousel = CarrouselItems("My Leagues", painterResource(id = R.drawable.futbol_ball))
    val MyScore = CarrouselItems("My Score", painterResource(id = R.drawable.futbol_ball))

    val carrouselItems = listOf(PronosticoCarrousel, MyLeaguesCarrousel, MyScore)
    val carrouselState = rememberCarouselState {
        3
    }
    Box (modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        HorizontalMultiBrowseCarousel(
            state = carrouselState,
            preferredItemWidth = 400.dp,
            itemSpacing = 10.dp
        ) {
            carrouselItems.forEach { it ->
                Box(modifier = Modifier.size(500.dp)) {
                    Column (modifier = Modifier.padding(8.dp)) {
                        Image(
                            painter = it.image,
                            contentDescription = it.title,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(it.title, style = TextStyle(fontSize = 18.sp))
                    }
                }
            }
            }

        }
    }


data class CarrouselItems (
    val title : String,
    val image: Painter
)

@Preview
@Composable
fun PreviewHomeCarrousel() {
    HomeCarrousel()
}