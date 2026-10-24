package com.example.myapplication

import com.example.myapplication.data.Dados


suspend fun carregarDadosBanco(db: AppDatabase){
    val usuarioDAO = db.usuarioDAO()
    val pastaDAO = db.pastaDAO()
    val pinDAO = db.pinDAO()
   // val mensagemDAO = db.mensagemDAO()

    if(usuarioDAO.contarUsuarios() > 0) return

    //Carrega os usuários
    Dados.usuarios.forEach {usuarioDAO.inserir(it)}
    Dados.amizades.forEach {usuarioDAO.vincular(it)}


    //Carrega Pins
    Dados.pins.forEach {pinDAO.adicionar(it)}

    //Carrega as pastas
    Dados.pastas.forEach {pastaDAO.criar(it)}
    Dados.pastaPins.forEach {pastaDAO.vincular(it)}


}