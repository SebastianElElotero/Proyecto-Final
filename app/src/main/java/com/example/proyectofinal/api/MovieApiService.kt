package com.example.proyectofinal.api

// MovieApiService.kt

import retrofit2.http.GET

interface MovieApiService {
    @GET("movies/") // Cambia esto según el endpoint de tu API
    suspend fun getMovies(): List<Movie>  // Obtenemos todas las películas
}
