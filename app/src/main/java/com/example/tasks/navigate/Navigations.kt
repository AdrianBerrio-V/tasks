package com.example.tasks.navigate

import com.example.tasks.R

sealed class AppRoute(val route:String) {
    object Login : AppRoute("login_screen")
    object MainContent : AppRoute("main_content_screen")
    object Register : AppRoute("register_screen")
}


sealed class Screen (val route:String) {
    object Login : Screen("login_screen")
    object Home : Screen("home_screen")
    object Calendar : Screen("calendar_screen")
    object Profile : Screen("profile_screen")
}

sealed class BottomNavItem (val route:String, val icon:Int, val title:String) {
    object Home : BottomNavItem(Screen.Home.route, R.drawable.ic_home, "Home")
    object Calendar : BottomNavItem(Screen.Calendar.route, R.drawable.ic_calendar, "Calendar")
    object Profile : BottomNavItem(Screen.Profile.route, R.drawable.ic_profile, "Profile")
    companion object {
        fun getItems(): List<BottomNavItem> = listOf(Home, Calendar, Profile)
    }
}