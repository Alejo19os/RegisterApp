package com.example.registerapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.registerapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupButtons()
    }

    private fun setupButtons() {
        binding.RegisterButton.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        binding.Perfil.setOnClickListener {
            startActivity(Intent(this, PerfilActivity::class.java))
        }

        binding.ExitButton.setOnClickListener {
            startActivity(Intent(this, SalidaActivity::class.java))
        }
    }
}