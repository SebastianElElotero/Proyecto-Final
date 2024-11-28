package com.example.proyectofinal.ui.Screens

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.proyectofinal.data.UserPreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(navController: NavHostController, context: Context) {
    val userPreferences = UserPreferences(context)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Hola Mundo", fontSize = 24.sp)

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            // Eliminar las credenciales y navegar a LoginScreen
            CoroutineScope(Dispatchers.IO).launch {
                userPreferences.saveCredentials("", "") // Borra los datos del usuario
                navController.navigate("login") {
                    popUpTo("home") { inclusive = true } // Limpia el stack de navegación
                }
            }
        }) {
            Text(text = "Desloguearse")
        }
    }
}
