package com.example.secretpass.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.secretpass.data.database.PasswordDatabase
import com.example.secretpass.ui.theme.ContrastAwareReplyTheme

class MainActivity : ComponentActivity() {
    private lateinit var database: PasswordDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        database = Room.databaseBuilder(
                applicationContext,
        PasswordDatabase::class.java,
        "password_database"
        ).build()
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "password_list") {
                composable("password_list") {
                    ContrastAwareReplyTheme(){
                        PasswordListScreen(navController, database)
                    }
                }
                composable("add_password") {
                    ContrastAwareReplyTheme(){
                        AddPasswordScreen(navController, database)
                    }
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun PreviewPasswordManagerApp() {
    // Dummy Preview for Design Check
    Column {
        Text("Gerenciador de Senhas", style = MaterialTheme.typography.headlineMedium)
    }
}


