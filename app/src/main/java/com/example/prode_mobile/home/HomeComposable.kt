package com.example.prode_mobile.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.SportsSoccer
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.prode_mobile.R
import com.example.prode_mobile.ui.theme.BlueButton
import com.example.prode_mobile.ui.theme.DarkBackground
import com.example.prode_mobile.ui.theme.TitleColor

@Composable
fun Home() {
    val buttons = listOf<HomeButton>(
        HomeButton(
            Icons.Filled.Star,
            title = "Score",
            onClick = {}),
        HomeButton(
            Icons.Filled.FavoriteBorder,
            title = "My Leagues",
            onClick = {}
        )
    )
    //crear animacion
    var showImage by remember { mutableStateOf(true)}

    Surface(modifier = Modifier.fillMaxSize(), color = DarkBackground) {
        Column {
            TopTitle()
            Row (horizontalArrangement = Arrangement.Center){
                MainButton(leadingIcon = Icons.Filled.DateRange, title = "MIS PRONOSTICOS",
                    Modifier
                        .fillMaxWidth()
                        .height(140.dp)
                        .padding(16.dp))
            }
//            AnimatedVisibility(visible = showImage) {
//                Image(...)
//            }
            Row (horizontalArrangement = Arrangement.SpaceBetween, modifier= Modifier.padding(16.dp)) {
                buttons.forEach { button ->
                    ButtonWithIcons(
                        leadingIcon = button.leadingIcon,
                        title = button.title,
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 8.dp)

                    )
                }
            }
            Row (modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()) {
                ButtonWithIcons(
                    Icons.Filled.Settings,
                    title = "Configuration",
                    Modifier.fillMaxWidth()
                )
            }
            //mandar la img al fondo
            Image(painter = painterResource(id = R.drawable.futbol_ball

            ), contentDescription = "", contentScale = ContentScale.FillHeight, alignment = Alignment.Center, modifier = Modifier.fillMaxSize())
        }

    }
}
@Composable
fun MainButton (leadingIcon: ImageVector, title: String, modifier : Modifier) {
    Button(onClick = {}, colors = ButtonDefaults.buttonColors(BlueButton), modifier = modifier, shape = RoundedCornerShape(0.dp)){
        Row (horizontalArrangement = Arrangement.SpaceBetween){
            Icon(imageVector = leadingIcon, contentDescription = "")
            Text(title)
        }
    }
}
@Composable
fun ButtonWithIcons(
    leadingIcon: ImageVector,
    title: String,
    modifier: Modifier
) {
    Button(onClick = {}, modifier = modifier.height(60.dp), shape = RoundedCornerShape(0.dp), colors = ButtonDefaults.buttonColors(BlueButton)) {
        Row(
            horizontalArrangement = Arrangement.Absolute.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = leadingIcon, contentDescription = "")
            Text(text = title)
        }
    }
}

data class HomeButton(
    val leadingIcon: ImageVector,
    val title: String,
    val onClick : () -> Unit
)


@Preview
@Composable
fun PreviewHome() {
    Home()
}