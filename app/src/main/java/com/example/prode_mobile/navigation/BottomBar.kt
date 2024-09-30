package com.example.prode_mobile.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun BottomBar (onNavigate: (String) -> Unit) {
    val pronosticosTab = TabBarItem (title = ProdeScreen.Pronosticos.name, selectedIcon =Icons.Filled.DateRange, unselectedIcon = Icons.Outlined.DateRange)
    val leagues = TabBarItem (title = ProdeScreen.League.name, selectedIcon = Icons.Filled.FavoriteBorder, unselectedIcon = Icons.Outlined.FavoriteBorder)
    val profileAndScore = TabBarItem (title = ProdeScreen.Score.name, selectedIcon = Icons.Filled.Person, unselectedIcon = Icons.Outlined.Person)

    val topBarItems = listOf(pronosticosTab, leagues, profileAndScore)
    TabView(tabBarItems = topBarItems, onNavigate)

}
data class TabBarItem (
    val title :String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)
@Composable
fun TabView (tabBarItems : List <TabBarItem>, onNavigate: (String) -> Unit) {
    var selectedTabIndex by rememberSaveable {
        mutableIntStateOf(0)
    }
    NavigationBar {
        tabBarItems.forEachIndexed{index, it  ->
            NavigationBarItem(selected = selectedTabIndex == index, onClick = {selectedTabIndex = index;  onNavigate(it.title)}, icon = {
                TabBarIconView(isSelected = selectedTabIndex == index, selectedIcon = it.selectedIcon, unselectedIcon = it.unselectedIcon, title = it.title)
            })
        }
    }
}

@Composable
fun TabBarIconView(isSelected : Boolean, selectedIcon: ImageVector, unselectedIcon: ImageVector, title: String){
    BadgedBox(badge =  {}) {
        Icon(imageVector = if (isSelected) selectedIcon else unselectedIcon, contentDescription = title)
        
    }
}