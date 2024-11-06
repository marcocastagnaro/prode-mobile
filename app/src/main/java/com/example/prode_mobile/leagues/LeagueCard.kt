package com.example.prode_mobile.leagues

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Block
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.example.prode_mobile.R
import com.example.prode_mobile.ui.theme.RegularFontSize


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LeagueCard(
    league: LeagueData,
    myleague: Boolean,
    isAvailable: Boolean,
    viewModel: LeagueViewModel? = null
) {
    Surface(
        modifier = Modifier
            .padding(
                dimensionResource(id = R.dimen.default_padding)
            )
            .background(MaterialTheme.colorScheme.surface)
            .height(dimensionResource(id = R.dimen.league_card_height))
            .fillMaxWidth()
            .border(
                dimensionResource(id = R.dimen.border_weight),
                color = MaterialTheme.colorScheme.secondary,
                shape = RoundedCornerShape(dimensionResource(id = R.dimen.default_padding))
            )
            .clickable(enabled = isAvailable) {}, // clickable only if available
        shape = RoundedCornerShape(dimensionResource(R.dimen.default_padding)),
        tonalElevation = dimensionResource(R.dimen.small_padding)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(
                dimensionResource(id = R.dimen.large_padding)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = league.image_path,
                contentDescription = "League Image",
                modifier = Modifier.size(dimensionResource(id = R.dimen.league_image_size))
            )

            Spacer(modifier = Modifier.weight(0.2f))

            Column {
                Text(
                    text = league.name,
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.tertiary,
                        fontSize = RegularFontSize
                    )
                )
                Text(
                    text = "${stringResource(id = R.string.category)}: ${league.category}",
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.tertiary,
                        fontSize = RegularFontSize
                    )
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            if (isAvailable) {
                if (myleague) {
                    viewModel?.let {
                        DeleteLeagueAction(
                            delLeagueAction = it::delLeague,
                            leagueId = league.id
                        )
                    }
                } else {
                    viewModel?.let {
                        AddLeagueAction(
                            addLeagueaction = viewModel::addNewLeague,
                            league
                        )
                    }
                }
            } else {

                IconButton(
                    onClick = {},
                    enabled = false
                ) {
                    Icon(
                        imageVector = Icons.Default.Block,
                        contentDescription = "Unavailable",
                        tint = MaterialTheme.colorScheme.tertiary
                    )
                }
            }

        }
    }
}


@Preview
@Composable
fun LeagueCardPreview() {
    LeagueCard(
        league = LeagueData(
            id = 1,
            name = "Liga Argentina",
            category = 2,
            image_path = "https://cdn.sportmonks.com/images/countries/png/short/fr.png",
            country_id = 1,
            active = true,
            seasonId = 1,
            type = "league",
            sub_type = "domestic",
            sport = SportData("Football")
        ),
        myleague = false,
        isAvailable = true
    )
}