package com.example.proyectofinal.ui.Screens

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.proyectofinal.R
import com.example.proyectofinal.data.UserPreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(navController: NavHostController, context: Context) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    val userPreferences = UserPreferences(context)

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Imagen principal de Login
        Image(
            painter = painterResource(id = R.drawable.login),
            contentDescription = "Login image",
            modifier = Modifier.size(200.dp)
        )

        Text(text = "Bienvenido de vuelta", fontSize = 28.sp)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = "Inicia tu cuenta")
        Spacer(modifier = Modifier.height(16.dp))

        // Campo para ingresar email
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Dirección Email") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo para ingresar contraseña
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botón de ingresar
        Button(onClick = {
            CoroutineScope(Dispatchers.IO).launch {
                val savedEmail = userPreferences.email.first()
                val savedPassword = userPreferences.password.first()

                if (email == savedEmail && password == savedPassword) {
                    navController.navigate("home")  // Navegar a HomeScreen después del login
                } else {
                    errorMessage = "Credenciales incorrectas"
                }
            }
        }) {
            Text(text = "Ingresar")
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text(text = errorMessage, color = androidx.compose.ui.graphics.Color.Red)

        Spacer(modifier = Modifier.height(32.dp))

        Text(text = "¿Olvidaste tu contraseña?", modifier = Modifier.clickable {
            // Aquí puedes implementar lógica para recuperación de contraseña
        })

        Spacer(modifier = Modifier.height(32.dp))

        Text(text = "O regístrate")

        // Imágenes clicables para redes sociales
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(40.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Image(
                painter = painterResource(id = R.drawable.facebook),
                contentDescription = "Facebook",
                modifier = Modifier
                    .size(68.dp)
                    .clickable {
                        navController.navigate("register") // Navegar a la pantalla de registro
                    }
            )

            Image(
                painter = painterResource(id = R.drawable.google),
                contentDescription = "Google",
                modifier = Modifier
                    .size(68.dp)
                    .clickable {
                        navController.navigate("register") // Navegar a la pantalla de registro
                    }
            )

            Image(
                painter = painterResource(id = R.drawable.twitter),
                contentDescription = "Twitter",
                modifier = Modifier
                    .size(68.dp)
                    .clickable {
                        navController.navigate("register") // Navegar a la pantalla de registro
                    }
            )
        }
    }
}