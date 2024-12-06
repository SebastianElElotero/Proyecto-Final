package com.example.proyectofinal.ui.Screens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import com.example.proyectofinal.api.RetrofitClient
import com.example.proyectofinal.data.UserPreferences
import com.example.proyectofinal.models.Movie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException

@Composable
fun HomeScreen(navController: NavHostController, context: Context) {
    val userPreferences = UserPreferences(context)

    // Estados para almacenar las películas, errores y la consulta de búsqueda
    var movies by remember { mutableStateOf<List<Movie>>(emptyList()) }
    var errorMessage by remember { mutableStateOf("") }
    var searchQuery by remember { mutableStateOf("") }

    // Función para cargar las películas desde la API
    LaunchedEffect(Unit) {
        try {
            val movieList = RetrofitClient.movieApiService.getMovies()
            movies = movieList
        } catch (e: HttpException) {
            errorMessage = "Error al cargar las películas: ${e.message()}"
        } catch (e: Exception) {
            errorMessage = "Ocurrió un error inesperado al cargar las películas."
        }
    }

    // Filtrar las películas según la consulta de búsqueda
    val filteredMovies = movies.filter {
        it.name.contains(searchQuery, ignoreCase = true)
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

        // Botón de cierre de sesión
        Button(onClick = {
            CoroutineScope(Dispatchers.IO).launch {
                userPreferences.saveCredentials("", "") // Borrar credenciales
                navController.navigate("login") {
                    popUpTo("home") { inclusive = true } // Limpia el stack de navegación
                }
            }
        }) {
            Text(text = "Cerrar sesión")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Mostrar mensaje de error si ocurre un problema
        if (errorMessage.isNotEmpty()) {
            Text(text = errorMessage, color = Color.Red)
        } else {
            // Mostrar rejilla de películas si no hay errores
            LazyVerticalGrid(
                columns = GridCells.Fixed(3), // 3 columnas por fila
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(filteredMovies) { movie ->
                    MovieCard(movie = movie) {
                        // Navegar a la pantalla de detalles con el ID de la película
                        navController.navigate("detail/${movie.id}")
                    }
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
            painter = painterResource(id = R.drawable.annabelle_movie), // Usar una imagen predeterminada o cargar desde la API
            contentDescription = movie.name,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp) // Tamaño de la imagen
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
