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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.prode_mobile.R
import com.example.prode_mobile.score.ScoreAndProfileViewModel
import com.example.prode_mobile.ui.theme.BlackColor
import com.example.prode_mobile.ui.theme.DarkerGreyColor
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
            .padding(16.dp),
        color = DarkerGreyColor,
        shape = RoundedCornerShape(10.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.weight(1f)) {
                cardInfo(R.string.username, tempUsername, isEditing) { newValue ->
                    tempUsername = newValue
                }
                cardInfo(R.string.country, tempCountry, isEditing) { newValue ->
                    tempCountry = newValue
                }
                cardInfo(R.string.age, tempAge, isEditing) { newValue ->
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
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = if (isEditing) Icons.Default.Save else Icons.Default.Edit,
                    contentDescription = if (isEditing) "Save" else "Edit",
                    tint = BlackColor,
                    modifier = Modifier.size(40.dp)
                )
            }
        }
    }
}

@Composable
fun cardInfo(subtitle: Int, value: String, isEditing: Boolean, onValueChange: (String) -> Unit) {
    Column(modifier = Modifier.padding(8.dp)) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(20.dp, 4.dp)
        ) {
            Text(
                text = stringResource(id = subtitle),
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = FontFamily.Serif,
                    fontStyle = FontStyle.Italic
                ),
                modifier = Modifier.padding(8.dp)
            )
            if (isEditing) {
                BasicTextField(
                    value = value,
                    onValueChange = onValueChange,
                    textStyle = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = FontFamily.Serif
                    ),
                    modifier = Modifier
                        .padding(8.dp)
                        .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                        .padding(8.dp)
                )
            } else {
                Text(
                    text = value,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = FontFamily.Serif
                    ),
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}