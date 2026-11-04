/*Fredi Díaz
* Electiva 4
* Noviembre 4, 2026
* */
package com.example.registroestudiantes
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
class MainActivity : AppCompatActivity() {
    private lateinit var db: DatabaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        db = DatabaseHelper(this)
        val nombre = findViewById<EditText>(R.id.txtNombre)
        val apellido = findViewById<EditText>(R.id.txtApellido)
        val edad = findViewById<EditText>(R.id.txtEdad)
        val correo = findViewById<EditText>(R.id.txtCorreo)
        val guardar = findViewById<Button>(R.id.btnGuardar)
        val consultar = findViewById<Button>(R.id.btnConsultar)
        val resultados = findViewById<TextView>(
            R.id.txtResultados
        )
// BOTON GUARDAR
        guardar.setOnClickListener {
            val n = nombre.text.toString().trim()
            val a = apellido.text.toString().trim()
            val e = edad.text.toString().trim()
            val c = correo.text.toString().trim()
            if (n.isEmpty() || a.isEmpty() ||
                e.isEmpty() || c.isEmpty()) {
                Toast.makeText(
                    this,
                    "Completa todos los campos",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val edadNumero = e.toIntOrNull()
                if (edadNumero == null || edadNumero <= 0) {
                    Toast.makeText(
                        this,
                        "Ingresa una edad válida",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    val guardado = db.insertar(
                        n, a, edadNumero, c
                    )
                    if (guardado) {
                        Toast.makeText(
                            this,
                            "Estudiante registrado",
                            Toast.LENGTH_SHORT
                        ).show()
                        nombre.text.clear()
                        apellido.text.clear()
                        edad.text.clear()
                        correo.text.clear()
                    } else {
                        Toast.makeText(
                            this,
                            "Error al guardar",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
// BOTON CONSULTAR
        consultar.setOnClickListener {
            resultados.text = db.consultar()
        }
    }
}
