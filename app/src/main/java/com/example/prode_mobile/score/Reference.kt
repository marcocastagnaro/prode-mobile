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
import androidx.compose.ui.res.stringResource
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
            .padding(dimensionResource(id = R.dimen.small_padding)),
        color = MaterialTheme.colorScheme.primary,
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corner_shape)),
        shadowElevation = dimensionResource(id = R.dimen.shadow_elevation_score)
    ){
        Column(
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.extra_large_padding)), // Espacio de 16.dp entre elementos
            modifier = Modifier.padding(dimensionResource(id = R.dimen.large_padding))
        ) {
            ReferenceItem(color = CorrectResultColor, description = stringResource(id = R.string.correct_answer_description))
            ReferenceItem(color = CorrectWinnerColor, description = stringResource(id = R.string.correct_winner_ddescription))
            ReferenceItem(color = WrongPrediction, description = stringResource(id = R.string.wrong_answer_description))
        }
    }
}

@Composable
fun ReferenceItem(color: Color, description: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(dimensionResource(id = R.dimen.large_padding))
                .background(color = color)
        )

        // Arrow
        Icon(
            imageVector = Icons.Filled.ArrowForward,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.size(dimensionResource(id =R.dimen.larger_padding))
        )

        // Description text
        Text(text = description, color = MaterialTheme.colorScheme.onSurface, modifier = Modifier.padding(start = 8.dp))
    }
}

@Preview
@Composable
fun ReferencePreview() {
    Reference()
}
