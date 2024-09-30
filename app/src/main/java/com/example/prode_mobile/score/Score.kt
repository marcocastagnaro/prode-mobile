package com.example.prode_mobile.score

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.prode_mobile.R
import com.example.prode_mobile.ui.theme.BlackColor
import com.example.prode_mobile.ui.theme.DarkerGreyColor
import com.example.prode_mobile.ui.theme.GreyBackground
import com.example.prode_mobile.ui.theme.WhiteColor
@Composable
fun Score(score: State<Int>) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                dimensionResource(id = R.dimen.large_padding))
            .border(2.dp, color = BlackColor, shape = RoundedCornerShape(13.dp))
            .background(DarkerGreyColor),
        shape = RoundedCornerShape(12.8.dp),
        shadowElevation = 6.dp
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(25.dp)
        ) {
            Text(
                text = stringResource(id = R.string.my_score),
                style = TextStyle(
                    fontSize = 38.sp,
                    fontWeight = FontWeight.Bold,
                    color = BlackColor
                ),
                modifier = Modifier.padding(bottom = 13.dp)
            )
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .border(3.dp, WhiteColor, shape= RoundedCornerShape(10.dp))
                    .background(BlackColor)
                    .padding(dimensionResource(id = R.dimen.large_padding))
                    .fillMaxWidth(0.5f)
            ) {
                Text(
                    text = score.value.toString(),
                    style = TextStyle(
                        fontSize = 60.sp,
                        fontWeight = FontWeight.Bold,
                        color = WhiteColor
                    )
                )
            }
        }
    }
}

@Preview
@Composable
fun ScorePreview() {
    val score = remember { mutableStateOf(100) }
    Score(score = score)
}

