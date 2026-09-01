package com.example.registerapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.registerapp.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupListeners()
    }

    private fun setupListeners() {
        binding.btnRegistrar.setOnClickListener {
            captureData()
        }
    }

    private fun captureData() {
        val nombre = binding.etNombre.text.toString()
        val apellido = binding.etApellido.text.toString()
        val edadStr = binding.etEdad.text.toString()
        val telefono = binding.etTelefono.text.toString()

        val selectedRadioButtonId = binding.rgSexo.checkedRadioButtonId
        val sexo = if (selectedRadioButtonId != -1) {
            val radioButton = findViewById<RadioButton>(selectedRadioButtonId)
            radioButton.text.toString()
        } else {
            "No especificado"
        }

        val edad = edadStr.toIntOrNull() ?: 0

        if (nombre.isNotEmpty() && apellido.isNotEmpty() && edad > 0) {
            val user = User(
                nombre = nombre,
                apellido = apellido,
                edad = edad,
                sexo = sexo,
                telefono = telefono
            )

            saveUserToPrefs(user)

            val intent = Intent(this, PerfilActivity::class.java).apply {
                putExtra("USER_DATA", user)
            }
            startActivity(intent)
        } else {
            Toast.makeText(this, "Por favor, completa los campos obligatorios", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveUserToPrefs(user: User) {
        val sharedPref = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString("nombre", user.nombre)
            putString("apellido", user.apellido)
            putInt("edad", user.edad)
            putString("sexo", user.sexo)
            putString("telefono", user.telefono)
            apply()
        }
    }
}