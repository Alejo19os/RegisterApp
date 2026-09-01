package com.example.registerapp

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.registerapp.databinding.ActivityPerfilBinding

class PerfilActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPerfilBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        binding = ActivityPerfilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var user = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra("USER_DATA", User::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getSerializableExtra("USER_DATA") as? User
        }

        if (user == null) {
            user = loadUserFromPrefs()
        }

        user?.let {
            displayUserData(it)
        } ?: run {
            Toast.makeText(this, "No hay datos de usuario registrados", Toast.LENGTH_SHORT).show()
        }

        binding.btnVolver.setOnClickListener {
            finish()
        }
    }

    private fun loadUserFromPrefs(): User? {
        val sharedPref = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val nombre = sharedPref.getString("nombre", null) ?: return null
        val apellido = sharedPref.getString("apellido", "")
        val edad = sharedPref.getInt("edad", 0)
        val sexo = sharedPref.getString("sexo", "No especificado") ?: "No especificado"
        val telefono = sharedPref.getString("telefono", "") ?: ""
        
        return User(nombre, apellido!!, edad, sexo, telefono)
    }

    private fun displayUserData(user: User) {
        binding.tvValueNombre.text = "${user.nombre} ${user.apellido}"
        binding.tvValueEdad.text = getString(R.string.age_format, user.edad)
        binding.tvValueSexo.text = user.sexo
        binding.tvValueTelefono.text = user.telefono
    }
}