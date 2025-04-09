package com.example.restaurant

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.restaurant.model.Pedido
import com.example.restaurant.model.Plato
import java.text.NumberFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var pedido: Pedido
    private lateinit var pastelChoclo: Plato
    private lateinit var cazuela: Plato

    private lateinit var switchPropina: Switch
    private lateinit var tvTotal: TextView
    private lateinit var tvPropina: TextView
    private lateinit var tvtotalconPropina: TextView
    private lateinit var tvSubtotalPastel: TextView
    private lateinit var tvSubtotalCazuela: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pastelChoclo = Plato("Pastel de Choclo", 12000.0)
        cazuela = Plato("Cazuela", 10000.0)
        pedido = Pedido().apply {
            agregarPlato(pastelChoclo)
            agregarPlato(cazuela)
        }
        configurarVistas()
        actualizarTotales()
    }

    private fun configurarVistas() {
        val etPastel = findViewById<EditText>(R.id.etPastel)
        val etCazuela = findViewById<EditText>(R.id.etCazuela)
        tvSubtotalPastel = findViewById(R.id.tvSubtotalPastel)
        tvSubtotalCazuela = findViewById(R.id.tvSubtotalCazuela)
        switchPropina = findViewById(R.id.switchPropina)
        tvTotal = findViewById(R.id.tvTotal)
        tvPropina = findViewById(R.id.tvPropina)
        tvtotalconPropina = findViewById(R.id.tvTotalConPropina)

        val imagePastel =
            findViewById<ImageView>(R.id.imagenPastel).apply { setImageResource(R.drawable.ic_pastel) }
        val imageCazuela =
            findViewById<ImageView>(R.id.imagenCazuela).apply { setImageResource(R.drawable.ic_cazuela) }

        etPastel.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                pastelChoclo.cantidad = s.toString().toIntOrNull() ?: 0
                tvSubtotalPastel.text = formatearMoneda(pastelChoclo.calcularSubtotal())
                actualizarTotales()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        etCazuela.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                cazuela.cantidad = s.toString().toIntOrNull() ?: 0
                tvSubtotalCazuela.text = formatearMoneda(cazuela.calcularSubtotal())
                actualizarTotales()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        switchPropina.setOnCheckedChangeListener { _, _ ->
            actualizarTotales()
        }
    }

    private fun actualizarTotales() {
        val totalSinPropina = pedido.calcularTotal(true)
        val totalConPropina = pedido.calcularTotal(false)
        val propina = totalConPropina - totalSinPropina

        tvTotal.text = formatearMoneda(totalSinPropina)
        tvPropina.text = formatearMoneda(propina)
        tvtotalconPropina.text = formatearMoneda(
            if (switchPropina.isChecked) totalConPropina else totalSinPropina
        )
    }

    private fun formatearMoneda(valor: Double): String {
        return NumberFormat.getCurrencyInstance(Locale("es", "CL"))
            .format(valor)
            .replace("CLP", "\$")
    }
}