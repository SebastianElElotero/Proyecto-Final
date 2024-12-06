package com.example.proyectofinal.api

// MovieApiService.kt

import com.example.proyectofinal.models.Movie
import retrofit2.http.GET

interface MovieApiService {
    @GET("movies/") // Cambia esto según el endpoint de tu API
    suspend fun getMovies(): List<Movie>  // Obtenemos todas las películas
}
