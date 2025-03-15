package com.example.secretpass.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.secretpass.data.database.PasswordDatabase
import com.example.secretpass.ui.screens.AddPasswordScreen
import com.example.secretpass.ui.screens.PasswordListScreen


@Composable
fun AppNavigation(navController: NavHostController, database: PasswordDatabase) {
    NavHost(navController = navController, startDestination = "password_list") {
        composable("password_list") {
            PasswordListScreen(navController, database)
        }
        composable("add_password") {
            AddPasswordScreen(navController, database)
        }
    }
}