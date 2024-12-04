package com.example.proyectofinal.ui.Screens

import android.content.Context
import androidx.compose.foundation.Image
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

@Composable
fun HomeScreen(navController: NavHostController, context: Context) {
    val userPreferences = UserPreferences(context)

    // Barra de búsqueda
    var searchQuery by remember { mutableStateOf("") }

    // Lista de películas (simulada por ahora)
    val movies = listOf(
        Movie("Babadook", R.drawable.babadook_movie),
        Movie("Nightmare on Elm Street", R.drawable.nightmare_movie),
        Movie("Annabelle", R.drawable.annabelle_movie),
        Movie("The Cabin in the Woods", R.drawable.cabin_in_woods_movie),
        Movie("Chucky", R.drawable.chucky_movie),
        Movie("The Conjuring", R.drawable.conjuring_movie),
        Movie("Halloween", R.drawable.halloween_movie),
        Movie("Scream", R.drawable.scream_movie),
        Movie("Texas Massacre", R.drawable.texas_massacre_movie),
        Movie("The Exorcist", R.drawable.the_exorcist_movie)
    )

    // Filtrar las películas según la búsqueda
    val filteredMovies = movies.filter {
        it.title.contains(searchQuery, ignoreCase = true) // Ignora mayúsculas y minúsculas
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
            painter = painterResource(id = movie.image),
            contentDescription = movie.title,
            modifier = Modifier.fillMaxWidth().height(200.dp) // Tamaño de la imagen
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = movie.title,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            fontSize = 16.sp
        )
    }
}

// Data class para representar la película
data class Movie(val id: Int, val title: String, val image: Int)
