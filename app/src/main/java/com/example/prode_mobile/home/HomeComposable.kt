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
@Composable
fun TopTitle() {

    Surface (modifier = Modifier
        .fillMaxWidth()
        .height(140.dp), color = BlueButton){
        Row (horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = rememberSportsSoccer(),tint = TitleColor, contentDescription = "")
            Text(text = stringResource(id = R.string.home_title), style = TextStyle(
                fontWeight = FontWeight.Bold,
                color = TitleColor,
                fontSize = 32.sp,
                fontFamily = FontFamily.Monospace
            ))
        }
    }
}
@Composable
//Pasar esto a painter (agregarlo o subir a vectores)
// Icon(painter = PainterResource(id = r.drawable.icon), contentDescription =" ")
fun rememberSportsSoccer(): ImageVector {
    return remember {
        ImageVector.Builder(
            name = "sports_soccer",
            defaultWidth = 40.0.dp,
            defaultHeight = 40.0.dp,
            viewportWidth = 40.0f,
            viewportHeight = 40.0f
        ).apply {
            path(
                fill = SolidColor(TitleColor),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(20f, 36.375f)
                quadToRelative(-3.375f, 0f, -6.375f, -1.292f)
                quadToRelative(-3f, -1.291f, -5.208f, -3.521f)
                quadToRelative(-2.209f, -2.229f, -3.5f, -5.208f)
                quadTo(3.625f, 23.375f, 3.625f, 20f)
                reflectiveQuadToRelative(1.292f, -6.375f)
                quadToRelative(1.291f, -3f, 3.521f, -5.208f)
                quadToRelative(2.229f, -2.209f, 5.208f, -3.5f)
                quadTo(16.625f, 3.625f, 20f, 3.625f)
                reflectiveQuadToRelative(6.375f, 1.292f)
                quadToRelative(3f, 1.291f, 5.208f, 3.521f)
                quadToRelative(2.209f, 2.229f, 3.5f, 5.208f)
                quadToRelative(1.292f, 2.979f, 1.292f, 6.354f)
                reflectiveQuadToRelative(-1.292f, 6.375f)
                quadToRelative(-1.291f, 3f, -3.521f, 5.208f)
                quadToRelative(-2.229f, 2.209f, -5.208f, 3.5f)
                quadToRelative(-2.979f, 1.292f, -6.354f, 1.292f)
                close()
                moveToRelative(8.375f, -20.25f)
                lineToRelative(2.667f, -0.917f)
                lineToRelative(0.708f, -2.541f)
                quadToRelative(-1.375f, -2.125f, -3.375f, -3.646f)
                reflectiveQuadToRelative(-4.5f, -2.313f)
                lineToRelative(-2.542f, 1.75f)
                verticalLineToRelative(2.75f)
                close()
                moveToRelative(-16.708f, 0f)
                lineToRelative(7.041f, -4.917f)
                verticalLineToRelative(-2.75f)
                lineToRelative(-2.583f, -1.75f)
                quadToRelative(-2.5f, 0.792f, -4.5f, 2.313f)
                quadToRelative(-2f, 1.521f, -3.333f, 3.646f)
                lineToRelative(0.833f, 2.541f)
                close()
                moveToRelative(-2.084f, 13f)
                lineToRelative(2.334f, -0.25f)
                lineToRelative(1.5f, -2.583f)
                lineToRelative(-2.5f, -7.667f)
                lineToRelative(-2.792f, -0.917f)
                lineToRelative(-1.875f, 1.5f)
                quadToRelative(0f, 2.875f, 0.688f, 5.25f)
                quadToRelative(0.687f, 2.375f, 2.645f, 4.667f)
                close()
                moveTo(20f, 33.75f)
                quadToRelative(1.083f, 0f, 2.188f, -0.188f)
                quadToRelative(1.104f, -0.187f, 2.27f, -0.52f)
                lineToRelative(1.334f, -2.834f)
                lineToRelative(-1.25f, -2.166f)
                horizontalLineTo(15.5f)
                lineToRelative(-1.25f, 2.166f)
                lineToRelative(1.292f, 2.834f)
                quadToRelative(1.083f, 0.333f, 2.229f, 0.52f)
                quadToRelative(1.146f, 0.188f, 2.229f, 0.188f)
                close()
                moveToRelative(-4.167f, -8.375f)
                horizontalLineToRelative(8.25f)
                lineToRelative(2.459f, -7.25f)
                lineTo(20f, 13.458f)
                lineToRelative(-6.583f, 4.667f)
                close()
                moveToRelative(14.625f, 3.75f)
                quadToRelative(1.917f, -2.292f, 2.604f, -4.667f)
                quadToRelative(0.688f, -2.375f, 0.688f, -5.25f)
                lineToRelative(-1.875f, -1.333f)
                lineToRelative(-2.75f, 0.75f)
                lineToRelative(-2.5f, 7.667f)
                lineToRelative(1.458f, 2.583f)
                close()
            }
        }.build()
    }
}

@Preview
@Composable
fun PreviewHome() {
    Home()
}