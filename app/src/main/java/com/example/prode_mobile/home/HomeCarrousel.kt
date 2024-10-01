package com.example.prode_mobile.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.prode_mobile.R
import com.example.prode_mobile.reusable.TopTitle
import com.example.prode_mobile.ui.theme.GreyBackground

@Composable
fun HomeCarrousel() {
    Surface (color = GreyBackground, modifier = Modifier.fillMaxSize() ) {
        Column {
            TopTitle(R.string.home_title)
            MyCarrousel()
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
                    Column (modifier = Modifier.padding(
                        dimensionResource(id = R.dimen.default_padding)
                    )) {
                        Image(
                            painter = it.image,
                            contentDescription = it.title,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.small_spacer)))
                        Text(it.title, style = TextStyle(fontSize = dimensionResource(id = R.dimen.regular_font_size).value.sp))
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