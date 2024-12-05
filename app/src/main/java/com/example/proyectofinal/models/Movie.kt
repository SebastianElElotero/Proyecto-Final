package com.example.proyectofinal.models

// Movie.kt

data class Movie(
    val id: Int,
    val name: String,
    val description: String,
    val genre: String,
    val duration: Int,
    val imageUrl: String  // Aquí usaremos el URL de la imagen que nos da la API
)
