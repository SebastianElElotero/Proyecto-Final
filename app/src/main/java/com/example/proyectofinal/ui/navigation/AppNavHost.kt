package com.tu_paquete.ui.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.proyectofinal.ui.Screens.HomeScreen
import com.example.proyectofinal.ui.Screens.LoginScreen
import com.example.proyectofinal.ui.Screens.RegisterScreen

@Composable
fun AppNavHost(navController: NavHostController = rememberNavController(), context: Context) {
    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LoginScreen(navController, context) }
        composable("register") { RegisterScreen(navController, context) }
        composable("home") { HomeScreen(navController, context) }
    }
}
