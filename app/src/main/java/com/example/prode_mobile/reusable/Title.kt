package com.example.prode_mobile.reusable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SportsSoccer
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.prode_mobile.R


@Composable
fun TopTitle(string : Int) {
    Surface (modifier = Modifier
        .fillMaxWidth()
        .height(dimensionResource(id = R.dimen.title_surface_size)), color = MaterialTheme.colorScheme.primary
    ){
        Row (horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Filled.SportsSoccer,
                tint = MaterialTheme.colorScheme.secondary,
                contentDescription = null,
                modifier = Modifier.size(dimensionResource(id = R.dimen.icon_title_size))
            )
            Text(text = stringResource(id = string), style = TextStyle(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.secondary,
                fontSize = dimensionResource(id = R.dimen.xxlarge_font_size).value.sp,
                fontFamily = FontFamily.Monospace
            )
            )
        }
    }
}