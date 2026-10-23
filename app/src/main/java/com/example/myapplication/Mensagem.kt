package com.example.myapplication

data class Mensagem (
    val id: Int,
    val texto: String,
    val remetente: Usuario,
    val destinatario: Usuario
)