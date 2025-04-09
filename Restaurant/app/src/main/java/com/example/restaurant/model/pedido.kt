package com.example.restaurant.model

class Pedido {
    private val platos = mutableListOf<Plato>()

    fun agregarPlato(plato: Plato) {
        platos.add(plato)
    }
    fun calcularTotal(sinPropina: Boolean): Double {
        val subtotal = platos.sumOf { it.calcularSubtotal() }
        return if (sinPropina) subtotal else subtotal * 1.10
    }
    fun getPlato(): List<Plato> = platos.toList()
}

