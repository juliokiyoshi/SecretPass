package com.example.secretpass.ui.screens

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.secretpass.data.database.PasswordDatabase
import com.example.secretpass.data.database.PasswordEntity
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPasswordScreen(navController: NavHostController, database: PasswordDatabase) {
    val passwordDao = database.passwordDao()
    val useDarkTheme: Boolean =  isSystemInDarkTheme()
    val context = LocalContext.current

    var site by remember { mutableStateOf(TextFieldValue("")) }
    var password: TextFieldValue by remember { mutableStateOf(TextFieldValue("")) }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Password") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
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
                Text("Adicionar Nova Senha", style = MaterialTheme.typography.headlineLarge)

                Spacer(modifier = Modifier.height(16.dp))

                BasicTextField(
                    value = site,
                    onValueChange = { site = it },
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    decorationBox = { innerTextField ->
                        if (site.text.isEmpty()) Text("Site")
                        innerTextField()
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))

                BasicTextField(
                    value = password,
                    onValueChange = { password = it },
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    decorationBox = { innerTextField ->
                        if (password.text.isEmpty()) Text("Senha")
                        innerTextField()
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        scope.launch {
                            val newPassword = PasswordEntity(site = site.text, password = password.text)
                            passwordDao.insertPassword(newPassword)
                            navController.navigate("password_list") // Navigate back to the list
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Salvar")
                }
            }
        }
    }

}
