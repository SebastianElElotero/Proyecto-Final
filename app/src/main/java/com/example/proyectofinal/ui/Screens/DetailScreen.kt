package com.example.proyectofinal.ui.Screens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.core.IOException
import androidx.navigation.NavHostController
import com.example.proyectofinal.R
import com.google.gson.Gson
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient

@Composable
fun DetailScreen(navController: NavHostController, movieId: Int, context: Context) {
    // Estado para almacenar los detalles de la película
    val movieDetails = remember { mutableStateOf<MovieDetails?>(null) }
    val coroutineScope = rememberCoroutineScope()

    // Llamada a la API para obtener los detalles
    LaunchedEffect(movieId) {
        coroutineScope.launch {
            try {
                val response = fetchMovieDetails(movieId, context)
                movieDetails.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // UI de la pantalla de detalles
    movieDetails.value?.let { movie ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Imagen de la película
            Image(
                painter = painterResource(id = R.drawable.placeholder_movie), // Placeholder
                contentDescription = movie.name,
                modifier = Modifier.fillMaxWidth().height(300.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Título de la película
            Text(
                text = movie.name,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Descripción
            Text(
                text = movie.description,
                fontSize = 16.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Género y duración
            Text(
                text = "Género: ${movie.genre} | Duración: ${movie.duration} min",
                fontSize = 14.sp,
                color = Color.DarkGray
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Botón para regresar a HomeScreen
            Button(onClick = { navController.popBackStack() }) {
                Text(text = "Volver a Home")
            }
        }
    } ?: run {
        // Pantalla de carga
        Text(text = "Cargando detalles...", modifier = Modifier.align(Alignment.CenterHorizontally))
    }
}

// Modelo de datos para los detalles de la película
data class MovieDetails(
    val id: Int,
    val name: String,
    val description: String,
    val genre: String,
    val duration: Int
)

// Función para obtener los detalles de la película desde la API
suspend fun fetchMovieDetails(movieId: Int, context: Context): MovieDetails {
    val client = OkHttpClient()
    val request = Request.Builder()
        .url("http://<API_URL>/movies/$movieId/")
        .build()

    client.newCall(request).execute().use { response ->
        if (!response.isSuccessful) throw IOException("Error de conexión: ${response.message}")
        val body = response.body?.string() ?: throw IOException("Cuerpo vacío")
        return Gson().fromJson(body, MovieDetails::class.java)
    }
}
