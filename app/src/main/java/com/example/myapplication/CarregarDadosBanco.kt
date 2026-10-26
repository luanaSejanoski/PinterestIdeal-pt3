package com.example.myapplication

import com.example.myapplication.data.Dados


suspend fun carregarDadosBanco(db: AppDatabase) {
    val usuarioDAO = db.usuarioDAO()
    val pastaDAO = db.pastaDAO()
    val pinDAO = db.pinDAO()
    // val mensagemDAO = db.mensagemDAO()


    // Carrega os usuários apenas se a tabela estiver vazia
    if (usuarioDAO.contarUsuarios() == 0) {
        Dados.usuarios.forEach { usuarioDAO.inserir(it)
        }}

    // Carrega as amizades apenas se não existirem
    if (usuarioDAO.contarAmizades() == 0) {
        Dados.amizades.forEach {usuarioDAO.vincular(it)
        }}

    // Carrega os Pins apenas se a tabela estiver vazia
    if (pinDAO.contarPins() == 0) {
        Dados.pins.forEach {pinDAO.adicionar(it)
        }}

    // Carrega as pastas apenas se a tabela estiver vazia
    if (pastaDAO.contarPastas() == 0) {
        Dados.pastas.forEach {pastaDAO.criar(it)
        }}

    // Carrega os relacionamentos entre pastas e Pins
    if (pastaDAO.contarRelacaoPastaPin() == 0) {
        Dados.pastaPins.forEach {pastaDAO.vincular(it)
        }}

    android.util.Log.d(
        "BANCO_TESTE",
        "Total de relacionamentos: ${pastaDAO.contarRelacaoPastaPin()}"
    )

}