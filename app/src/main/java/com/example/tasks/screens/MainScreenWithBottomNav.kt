package com.example.tasks.screens

import HomeScreen
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.calendar.CalendarScreen
import com.example.tasks.navigate.BottomNavItem

@Preview
@Composable
fun MainPreview() {
    MainScreenWithBottomNav()
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreenWithBottomNav(rootNavController: NavController? = null) { // Recibe el NavController principal
    val bottomNavController = rememberNavController() // NavController para la Bottom Navigation
    val navBackStackEntry by bottomNavController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Scaffold(
        bottomBar = {
            NavigationBar {
                BottomNavItem.getItems().forEach { item -> // Itera sobre la lista de ítems
                    NavigationBarItem(
                        icon = { Icon(painterResource(item.icon), contentDescription = item.title) },
                        label = { Text(item.title) },
                        selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                        onClick = {
                            bottomNavController.navigate(item.route) {
                                // Asegura que solo haya una instancia del destino
                                popUpTo(bottomNavController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = bottomNavController,
            startDestination = BottomNavItem.Calendar.route, // Define la pestaña inicial
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(BottomNavItem.Home.route) { HomeScreen() }
            composable(BottomNavItem.Calendar.route) { CalendarScreen() }
            composable(BottomNavItem.Profile.route) { ProfileScreen() }
        }
    }
}