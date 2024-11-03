import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.prode_mobile.R
import com.example.prode_mobile.ui.theme.CorrectResultColor
import com.example.prode_mobile.ui.theme.CorrectWinnerColor
import com.example.prode_mobile.ui.theme.WrongPrediction

@Composable
fun Reference() {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                dimensionResource(id = R.dimen.small_padding)
            )

            .background(MaterialTheme.colorScheme.primary),
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corner_shape)),
        shadowElevation = dimensionResource(id = R.dimen.shadow_elevation_score)
    ){
        Column(verticalArrangement = Arrangement.SpaceBetween, modifier = Modifier.padding(
            dimensionResource(id = R.dimen.default_padding))) {
            ReferenceItem(color = CorrectResultColor, description = "Correct winner and exact result, each match is worth 3 points")
            ReferenceItem(color = CorrectWinnerColor, description = "Correct winner but not exact result, each match is worth 1 point")
            ReferenceItem(color = WrongPrediction, description = "Wrong prediction, each match is worth 0 points")
        }
    }
}

@Composable
fun ReferenceItem(color: Color, description: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        // Circle bullet
        Box(
            modifier = Modifier
                .size(12.dp)
                .background(color = color)
        )

        // Arrow
        Icon(
            imageVector = Icons.Filled.ArrowForward,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.size(16.dp)
        )

        // Description text
        Text(text = description)
    }
}

@Preview
@Composable
fun ReferencePreview() {
    Reference()
}
