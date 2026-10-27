package com.example.myapplication

import android.R
import android.util.Log
import androidx.room3.Query
import com.example.myapplication.data.Pasta

suspend fun buscarPastas(pastaDAO: PastaDAO, usuarioId: Int): List<Pasta> {
    return try {
        pastaDAO.pastasDoUsuario(usuarioId)
    } catch (e: Exception) {
        Log.e("Erro ao buscar pastas...", "Msg ${e.message}")
        emptyList()
    }
}

suspend fun buscarPastaId(pastaDAO: PastaDAO, pastaId: Int): Pasta{
    return try{
        pastaDAO.buscarPorId(pastaId)
    }catch (e: Exception){
        Log.e("Erro ao buscar pastas...", "Msg ${e.message}")
        throw e
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

suspend fun atualizarPasta( pasta: Pasta, pastaDAO: PastaDAO){
    try{
        pastaDAO.atualizar(pasta)
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

//operacoes usuarios
suspend fun atualizarDadosUsuario(
    usuarioDAO: UsuarioDAO,
    usuario: Usuario,
    nomeExibicao: String,
    nomeUsuario: String,
    biografia: String,
    email: String,
    fotoPerfil: String?
) {
    try {
        val usuarioAtualizado = usuario.copy(
            nomeExibicao = nomeExibicao,
            nomeUsuario = nomeUsuario,
            biografia = biografia,
            email = email,
            foto = fotoPerfil
        )
        usuarioDAO.atualizarUsuario(usuarioAtualizado)
    } catch (e: Exception) {
        Log.e("Erro ao atualizar usuário...", "Msg ${e.message}")
    }
}

suspend fun listarUsuarios(usuarioDAO: UsuarioDAO): List<Usuario> {
    return try {
        usuarioDAO.listarTodosUsuarios()
    } catch (e: Exception) {
        Log.e("Erro ao listar usuários...", "Msg ${e.message}")
        emptyList()
    }
}

suspend fun buscarAmigosDoUsuario(usuarioDAO: UsuarioDAO, idUsuario: Int): List<Usuario> {
    return try {
        usuarioDAO.buscarAmigos(idUsuario)
    } catch (e: Exception) {
        Log.e("Erro ao buscar amigos...", "Msg ${e.message}")
        emptyList()
    }
}
suspend fun adicionarAmigoBanco(usuarioDAO: UsuarioDAO, usuarioId: Int, amigoId: Int) {
    try {
        val relacao = UsuarioAmigo(usuarioId = usuarioId, amigoId = amigoId)
        usuarioDAO.adicionarAmigo(relacao)
    } catch (e: Exception) {
        Log.e("Erro ao adicionar amigo...", "Msg ${e.message}")
    }
}

