package com.example.proyectofinal.ui.Screens

import com.example.proyectofinal.api.RetrofitClient

// HomeScreen.kt
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.proyectofinal.R
import com.example.proyectofinal.data.UserPreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException

@Composable
fun HomeScreen(navController: NavHostController, context: Context) {
    val userPreferences = UserPreferences(context)

    // Estado para almacenar las películas y errores
    var movies by remember { mutableStateOf<List<Movie>>(emptyList()) }
    var errorMessage by remember { mutableStateOf("") }

    // Barra de búsqueda
    var searchQuery by remember { mutableStateOf("") }

    // Función para cargar las películas desde la API
    LaunchedEffect(true) {
        try {
            // Llamada a la API para obtener las películas
            val movieList = RetrofitClient.movieApiService.getMovies()
            movies = movieList
        } catch (e: HttpException) {
            errorMessage = "Error al cargar las películas: ${e.message()}"
        } catch (e: Exception) {
            errorMessage = "Ocurrió un error inesperado"
        }
    }

    // Filtrar las películas según la búsqueda
    val filteredMovies = movies.filter {
        it.name.contains(searchQuery, ignoreCase = true) // Ignora mayúsculas y minúsculas
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Barra de búsqueda
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Buscar película") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botón de desloguearse
        Button(onClick = {
            CoroutineScope(Dispatchers.IO).launch {
                userPreferences.saveCredentials("", "")  // Borra los datos del usuario
                navController.navigate("login") {
                    popUpTo("home") { inclusive = true }  // Limpia el stack de navegación
                }
            }
        }) {
            Text(text = "Desloguearse")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Si hay un error, mostramos el mensaje de error
        if (errorMessage.isNotEmpty()) {
            Text(text = errorMessage, color = Color.Red)
        }

        // Rejilla de películas (3 columnas por fila)
        LazyVerticalGrid(
            columns = GridCells.Fixed(3), // 3 columnas por fila
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(filteredMovies) { movie ->
                MovieCard(movie = movie) {
                    // Redirigir a DetailScreen con el ID de la película
                    navController.navigate("detail/${movie.id}")
                }
            }
        }
    }
}

@Composable
fun MovieCard(movie: Movie, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() }, // Detectar el clic
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.annabelle_movie), // Usar una imagen por defecto o la URL de la imagen de la API
            contentDescription = movie.name,
            modifier = Modifier.fillMaxWidth().height(200.dp) // Tamaño de la imagen
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = movie.name,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            fontSize = 16.sp
        )
    }
}
