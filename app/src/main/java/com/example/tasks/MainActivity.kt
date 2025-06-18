package com.example.tasks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tasks.navigate.AppRoute
import com.example.tasks.navigate.Screen
import com.example.tasks.screens.LoginScreen
import com.example.tasks.screens.MainScreenWithBottomNav
import com.example.tasks.screens.RegisterScreen
import com.example.tasks.ui.theme.TasksTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TasksTheme {
                AppNavigation()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = AppRoute.Login.route) {
        composable(AppRoute.Login.route) {
            LoginScreen(navController = navController) {
                //ToDo function to validate login success
                navController.navigate(AppRoute.MainContent.route) {
                    popUpTo(Screen.Login.route) {inclusive = true}
                }
            }
        }
        composable(AppRoute.MainContent.route) {
            MainScreenWithBottomNav(rootNavController = navController)
        }
        composable(AppRoute.Register.route) {
            RegisterScreen(navController = navController) {
                navController.navigate(AppRoute.MainContent.route) {
                    popUpTo(AppRoute.Login.route) { inclusive = true }
                }
            }
        }
    }
}
