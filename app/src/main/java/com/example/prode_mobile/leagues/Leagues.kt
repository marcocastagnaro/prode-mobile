package com.example.prode_mobile.leagues

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.prode_mobile.R
import com.example.prode_mobile.reusable.AlertDialogExample
import com.example.prode_mobile.ui.theme.PurpleGrey80

@Composable
fun Leagues() {
    val viewModel = hiltViewModel<LeagueViewModel>()
    val leaguesList by viewModel.leaguesList.collectAsState()

    val loading by viewModel.loadingLeagues.collectAsState()
    val showRetry by viewModel.showRetry.collectAsState()

    val allLeaguesList by viewModel.allLeaguesList.collectAsState()
    val unavailableList by viewModel.unavailableLeaguesList.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.mockUnavailableLeagues()
    }
    if (loading) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(64.dp)
                    .align(Alignment.Center),
                color = PurpleGrey80,
                trackColor = PurpleGrey80,
            )
        }
    } else if (showRetry) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(id = R.string.retry),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
            )
            Text(text = stringResource(id = R.string.retry_load_leagues))
            Button(onClick = { viewModel.retryLoadingLeagues() }) {
                Text(text = stringResource(id = R.string.retry))
            }
        }
    } else {
        Surface {
            LazyColumn(modifier = Modifier.padding(16.dp)) {
                item {
                    Text(
                        text = "My Leagues", style =
                        TextStyle(fontSize = 20.sp, color = Color.Black)
                    )
                }
                item {
                    leaguesList.forEach { league ->
                        LeagueCard(league = league, true, viewModel)
                    }
                }
                item {
                    Divider(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                    )
                }
                item {
                    Text(
                        text = "All Leagues",
                        style = TextStyle(fontSize = 20.sp, color = Color.Black)
                    )
                    allLeaguesList.forEach { league ->
                        LeagueCard(league = league, false, viewModel)
                    }
                }
                item {
                    unavailableList.forEach { league ->
                        UnavailableLeagueCard(league = league, false, viewModel)
                    }
                }
            }
        }
    }
}

@Composable
fun LeagueCard(league: LeagueData, myleague: Boolean, viewModel: LeagueViewModel) {
    Surface (modifier = Modifier
        .padding(8.dp)
        .background(Color.White)
        .height(70.dp)
        .fillMaxWidth(), shape = RoundedCornerShape(8.dp), tonalElevation = 4.dp) {
        Row (horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
            .padding(16.dp)
            , verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(model = league.image_path, contentDescription = "League Image", modifier= Modifier.size(30.dp)) //Place holder para cuando la imagen carga y error cuando falla
            Spacer(modifier = Modifier.weight(0.2f))
            Column {
                Text(
                    text = league.name,
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 16.sp
                    )
                )
                Text(
                    text = "Category: " + league.category.toString(),
                    style = TextStyle(
                        color = Color.Gray,
                        fontSize = 16.sp
                    )
                )
            }

            if (!myleague) {
                Spacer(modifier = Modifier.weight(1f))
                AddLeagueAction(
                    addLeagueaction = viewModel::addNewLeague,
                    league
                )
            }
            else {
                Spacer(modifier = Modifier.weight(1f))
                DeleteLeagueAction(
                    delLeagueAction = viewModel::delLeague,
                    league
                )
            }

        }
    }
}
@Composable
fun UnavailableLeagueCard(league: LeagueData, myleague: Boolean, viewModel: LeagueViewModel) {
    Surface(
        modifier = Modifier
            .padding(8.dp)
            .height(70.dp)
            .fillMaxWidth()
            .clickable(enabled = false){}
        , shape = RoundedCornerShape(8.dp), tonalElevation = 4.dp
    ) {
        Row (horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
            .padding(16.dp)
            , verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(model = league.image_path, contentDescription = "League Image", modifier= Modifier.size(30.dp)) //Place holder para cuando la imagen carga y error cuando falla
            Spacer(modifier = Modifier.weight(0.2f))
            Column {
                Text(
                    text = league.name,
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 16.sp
                    )
                )
                Text(
                    text = "Category: " + league.category.toString(),
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 16.sp
                    )
                )
            }

                Spacer(modifier = Modifier.weight(1f))
            IconButton(
                onClick = {
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Block,
                    contentDescription = "Del",
                    tint = Color.Black
                )
            }
        }
    }
}

@Composable
fun DeleteLeagueAction (delLeagueAction : (Int, Int, String, Boolean, String, Int) -> Unit, leagueData: LeagueData) {
    var showAlert by remember {
        mutableStateOf(false)
    }
    IconButton(
        onClick = {
           showAlert = true

        }
    ) {
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = "Del",
            tint = Color.Black
        )
    }
    if (showAlert) {
        AlertDialogExample(
            onDismissRequest = { showAlert = false },
            onConfirmation = {
                delLeagueAction(
                    leagueData.id,
                    leagueData.country_id,
                    leagueData.name,
                    leagueData.active,
                    leagueData.image_path,
                    leagueData.category
                )
                showAlert = false
            },
            dialogTitle = "Delete League",
            dialogText = "Are you sure you want to delete this league?",
            icon = Icons.Default.Delete
        )
    }

}


@Composable
fun AddLeagueAction(
    addLeagueaction : (Int, Int, String, Boolean, String, Int) -> Unit,
    leagueData: LeagueData
) {
    var showAlert by remember {
        mutableStateOf(false)
    }

    IconButton(
        onClick = {
            showAlert = true
        }
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Add",
            tint = Color.Black
        )
    }
    if (showAlert) {
        AlertDialogExample(
            onDismissRequest = { showAlert = false },
            onConfirmation = {
                addLeagueaction(leagueData.id, leagueData.country_id, leagueData.name, leagueData.active, leagueData.image_path, leagueData.category)
                showAlert = false
            },
            dialogTitle = "Add League",
            dialogText = "Are you sure you want to add this league?",
            icon = Icons.Default.Info
        )
    }
}

@Preview
@Composable
fun PreviewLeagues (){
    Leagues()
}
