import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.prode_mobile.R
import com.example.prode_mobile.score.ScoreAndProfileViewModel

@Composable
fun Profile(viewModel: ScoreAndProfileViewModel) {
    var isEditing by remember { mutableStateOf(false) }

    var tempUsername by remember { mutableStateOf(viewModel.username.value) }
    var tempCountry by remember { mutableStateOf(viewModel.country.value) }
    var tempAge by remember { mutableStateOf(viewModel.age.value) }

    val username by viewModel.username.collectAsState()
    val country by viewModel.country.collectAsState()
    val age by viewModel.age.collectAsState()

    if (!isEditing) {
        tempUsername = username
        tempCountry = country
        tempAge = age
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.large_padding)),
        color = MaterialTheme.colorScheme.primary,
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corner_shape))
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.weight(1f)) {
                CardInfo(R.string.username, tempUsername, isEditing) { newValue ->
                    tempUsername = newValue
                }
                CardInfo(R.string.country, tempCountry, isEditing) { newValue ->
                    tempCountry = newValue
                }
                CardInfo(R.string.age, tempAge, isEditing) { newValue ->
                    tempAge = newValue
                }
            }

            IconButton(
                onClick = {
                    if (isEditing) {
                        viewModel.saveUsernameToDataStore(tempUsername)
                        viewModel.saveCountryToDataStore(tempCountry)
                        viewModel.saveAgeToDataStore(tempAge)
                    }
                    isEditing = !isEditing
                },
                modifier = Modifier.padding(dimensionResource(R.dimen.large_padding))
            ) {
                Icon(
                    imageVector = if (isEditing) Icons.Default.Save else Icons.Default.Edit,
                    contentDescription = if (isEditing) "Save" else "Edit",
                    tint = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier.size(dimensionResource(id = R.dimen.edit_icon_size))
                )
            }
        }
    }
}
@Composable
fun CardInfo(
    subtitle: Int,
    value: String,
    isEditing: Boolean,
    onValueChange: (String) -> Unit
) {
    Column(modifier = Modifier.padding(dimensionResource(id = R.dimen.default_padding))) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(
                    dimensionResource(id = R.dimen.larger_padding),
                    dimensionResource(id = R.dimen.small_padding)
                )
        ) {
            Text(
                text = stringResource(id = subtitle),
                style = TextStyle(
                    fontSize = dimensionResource(id = R.dimen.regular_font_size).value.sp,
                    fontFamily = FontFamily.Serif,
                    fontStyle = FontStyle.Italic,
                    color = MaterialTheme.colorScheme.secondary // Color aplicado
                ),
                modifier = Modifier.padding(dimensionResource(id = R.dimen.default_padding))
            )

            if (isEditing) {
                BasicTextField(
                    value = value,
                    onValueChange = onValueChange,
                    textStyle = TextStyle(
                        fontSize = dimensionResource(id = R.dimen.regular_font_size).value.sp,
                        fontFamily = FontFamily.Serif,
                        color = MaterialTheme.colorScheme.secondary
                    ),
                    modifier = Modifier
                        .border(1.dp, MaterialTheme.colorScheme.secondary, RoundedCornerShape(8.dp))
                        .padding(dimensionResource(id = R.dimen.default_padding))
                )
            } else {
                Text(
                    text = value,
                    style = TextStyle(
                        fontSize = dimensionResource(id = R.dimen.regular_font_size).value.sp,
                        fontFamily = FontFamily.Serif,
                        color = MaterialTheme.colorScheme.secondary
                    ),
                    modifier = Modifier.padding(dimensionResource(id = R.dimen.default_padding))
                )
            }
        }
    }
}