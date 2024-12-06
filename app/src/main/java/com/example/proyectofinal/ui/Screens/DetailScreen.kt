package com.example.proyectofinal.ui.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.proyectofinal.R
import com.example.proyectofinal.models.Movie

@Composable
fun DetailScreen(navController: NavHostController, movieId: Int) {
    val movie = getMovieById(movieId) ?: return // Verifica que la película exista

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.annabelle_movie), // Sustituir con URL si es necesario
            contentDescription = movie.name,
            modifier = Modifier.fillMaxWidth().height(300.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = movie.name,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = movie.description,
            fontSize = 16.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Género: ${movie.genre} | Duración: ${movie.duration} min",
            fontSize = 14.sp,
            color = Color.DarkGray
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = { navController.popBackStack() }) {
            Text(text = "Volver a Home")
        }
    }
}

fun getMovieById(id: Int): Movie? {
    return Movie(
        id = id,
        name = "Inception",
        description = "A mind-bending thriller directed by Christopher Nolan.",
        genre = "Sci-Fi",
        duration = 148,
        imageUrl = "https://example.com/inception.jpg"
    )
}