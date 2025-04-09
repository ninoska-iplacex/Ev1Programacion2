package com.example.restaurant.model

data class Plato(
    val nombre: String,
    val precio: Double,
    var cantidad: Int = 0
) {
    fun calcularSubtotal(): Double = precio * cantidad
}


