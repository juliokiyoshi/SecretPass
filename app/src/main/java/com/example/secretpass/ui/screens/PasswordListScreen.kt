package com.example.secretpass.ui.screens

import android.app.Activity
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.secretpass.data.database.PasswordDatabase
import com.example.secretpass.data.database.PasswordEntity
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordListScreen(navController: NavHostController, database: PasswordDatabase) {
    val activity = LocalContext.current as? Activity
    val passwordDao = database.passwordDao()
    val passwords = remember { mutableStateListOf<PasswordEntity>() }
    var indexClicked by remember { mutableStateOf<Int?>(null) }

    val scope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        scope.launch {
            passwords.addAll(passwordDao.getAllPasswords())
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Password List") },
                navigationIcon = {
                    IconButton(onClick = {activity?.finish() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(Modifier.padding(padding)) {
            Column(
                modifier = Modifier.fillMaxSize().padding(16.dp)
            ) {
                Text("Gerenciador de Senhas", style = MaterialTheme.typography.headlineMedium)

                Spacer(modifier = Modifier.height(16.dp))

                LazyColumn(
                    modifier = Modifier.weight(1f)
                ) {
                    items(passwords.size) { index ->
                        Card(onClick = {
                            indexClicked = if (indexClicked == index) null else index
                        },
                            modifier =  Modifier.size(width = 400.dp, height = 150.dp).padding(16.dp)
                        ){
                            val password = passwords[index]
                            Text(text = "Domain: ${password.site}",
                                modifier= Modifier.padding(16.dp))
                            if (indexClicked == index){
                                Text(text = "Pass: ${password.password}",
                                    modifier= Modifier.padding(16.dp)
                                )
                            }else{
                                Text(text = "Pass: *****************",
                                    modifier= Modifier.padding(16.dp)
                                )
                            }

                        }

                    }
                }

                Button(
                    onClick = { navController.navigate("add_password") },
                    modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
                ) {
                    Text("Adicionar Nova Senha")
                }
            }
        }
    }
}