package com.example.myapplication

import android.R
import android.util.Log
import com.example.myapplication.data.Pasta

suspend fun buscarPastas(pastaDAO: PastaDAO, usuarioId: Int): List<Pasta> {
    return try {
        pastaDAO.pastasDoUsuario(usuarioId)
    } catch (e: Exception) {
        Log.e("Erro ao buscar pastas...", "Msg ${e.message}")
        emptyList()
    }
}

suspend fun buscarPinsPasta(pastaDAO: PastaDAO, pastaId: Int) : List<Pin>{
    return try{
        pastaDAO.pinsDaPasta(pastaId)
    }catch (e: Exception){
        Log.e("Erro ao buscar os pins da pastas...", "Msg ${e.message}")
        emptyList()
    }

}

suspend fun contarPinsPasta(pastaDAO: PastaDAO, idPasta: Int): Int{
    return try{
        pastaDAO.contarPins(idPasta)
    }catch (e: Exception){
        Log.e("Erro ao contar os pins...", "Msg ${e.message}")
    }
}
suspend fun buscarDoisUltimosPins(pastaDAO: PastaDAO, idPasta: Int): List<Pin> {
    return try {
        pastaDAO.doisUltimosPins(idPasta)
    } catch (e: Exception) {
        Log.e("Erro ao buscar pins...", "Msg ${e.message}")
        emptyList()
    }
}

suspend fun criarPasta(nome: String, privado: Boolean, usuarioId: Int, pastaDAO: PastaDAO){
     try{
        pastaDAO.criar(Pasta(nome = nome, statusPrivado = privado, usuarioId = usuarioId))
    }catch (e: Exception){
        Log.e("Erro ao criar pasta...", "Msg ${e.message}")
    }
}

suspend fun excluirPasta(pasta: Pasta, pastaDAO: PastaDAO){
    try {
        pastaDAO.excluirLigacao(pasta.id)
        pastaDAO.excluir(pasta)
    }catch (e: Exception){
        Log.e("Erro ao excluir pasta...", "Msg ${e.message}")
    }
}

suspend fun mudarNomePasta(novoNome: String, pasta: Pasta, pastaDAO: PastaDAO){
    try{
        pasta.nome = novoNome
        pastaDAO.mudarNome(pasta)
    }catch (e: Exception){
        Log.e("Erro ao mudar o nome da pasta...", "Msg ${e.message}")
    }
}

suspend fun adicionarPinNaPasta(pinId: Int, pastaId: Int, pastaDAO: PastaDAO){
    try{
        pastaDAO.vincular(PastaPin(pastaId, pinId))
    }catch(e: Exception){
        Log.e("Erro ao adicionar o pin na pasta...", "Msg ${e.message}")
    }
}



//Operações Pin
suspend fun adicionarPin(uri: String, largura: Int, altura: Int, pinDAO: PinDAO): Int{
   return try {
        pinDAO.adicionar(Pin(imagem = uri, largura = largura, altura = altura)).toInt()
    }catch (e: Exception){
        Log.e("Erro ao adicionar pin...", "Msg ${e.message}")
    }
}


suspend fun excluirPinDaPasta(pinId: Int, pastaId: Int, pastaDAO: PastaDAO, pinDAO: PinDAO){
    try{
        pastaDAO.excluirPinPasta(pastaId, pinId)

        if(pastaDAO.contarPastasPin(pinId) == 0){
            pinDAO.excluirPin(pinDAO.pinsSemPasta())
        }
    }catch (e: Exception){
        Log.e("Erro ao excluir o pin da pasta...", "Msg ${e.message}")

    }
}

suspend fun alterarTituloPin(novoTitulo: String, pin:Pin, pinDAO: PinDAO){
    try{
        pin.titulo = novoTitulo
        pinDAO.editar(pin)
    }catch (e: Exception){
        Log.e("Erro ao mudar o título do pin...", "Msg ${e.message}")

    }
}

