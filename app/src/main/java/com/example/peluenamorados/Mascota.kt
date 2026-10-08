package com.example.peluenamorados

data class Mascota(
    val nombre: String,
    val raza: String,
    val edad: Int,
    val descripcion: String,
    val imagenUrl: String,

    val latitud: Double,

    val longitud: Double
)