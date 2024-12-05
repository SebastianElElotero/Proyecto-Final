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
fun DetailScreen(navController: NavHostController, movieId: Int) {
    // Obtener los detalles de la película (por ahora, los simulamos)
    val movie = getMovieById(movieId)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Imagen de la película
        Image(
            painter = painterResource(id = R.drawable.annabelle_movie), // Aquí puedes usar la URL de la API
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

        // Descripción de la película
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

        // Botón para regresar
        Button(onClick = { navController.popBackStack() }) {
            Text(text = "Volver a Home")
        }
    }
}

// Simulación de obtener la película por ID (cuando la API esté funcionando, reemplazar con Retrofit)
fun getMovieById(id: Int): Movie {
    return Movie(
        id = id,
        name = "Inception",
        description = "A mind-bending thriller directed by Christopher Nolan.",
        genre = "Sci-Fi",
        duration = 148,
        imageUrl = "https://example.com/inception.jpg"
    )
}