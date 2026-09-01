package com.example.registerapp

import java.io.Serializable

data class User(
    val nombre: String,
    val apellido: String,
    val edad: Int,
    val sexo: String,
    val telefono: String
) : Serializable