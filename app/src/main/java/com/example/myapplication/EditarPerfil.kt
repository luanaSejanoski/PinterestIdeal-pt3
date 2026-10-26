package com.example.myapplication
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme
//import androidx.compose.material.icons.filled.ChevronLeft
//import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter


import com.example.myapplication.data.Dados
import kotlinx.coroutines.launch
import kotlin.math.sin


@Composable
fun gerarBloco(cor:Color?, x: Int, y: Int, z:Int? = null, imagem: Any? = null, icon: Painter? = null){
    Surface(
        modifier = Modifier
            .requiredSize(x.dp)
            .padding(y.dp),
        color = cor?: Color.Transparent,
        shape = RoundedCornerShape((z?:0).dp)
    ) {
        if(imagem != null){
            Image(
                painter = rememberAsyncImagePainter(imagem),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        if(icon != null){
            Icon(
                painter = icon,
                contentDescription = null,
                tint = Color.White
            )
        }
    }
}

@Composable
fun gerarConteudoBloco(cor: Color? = null,
                       x: Int,
                       y: Int,
                       raio: Int? = 0,
                       imagem: Any? = null,
                       icon: Painter? = null,
                       texto: String? = null,
                       corTexto: Color = Color.White
){
    Box(contentAlignment = Alignment.Center) {
        gerarBloco(cor, x, y, raio, imagem, icon)
        if(!texto.isNullOrEmpty()){
            Text(text = texto, color = corTexto)
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CampoEditar(nomeCampo: String,
                valor: String?,
                singleline: Boolean,
                onValueChange: (String) -> Unit){
    var focado by remember { mutableStateOf(false) }
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = if (focado) Color.White else Color.Gray,
                shape = RoundedCornerShape(8.dp)
            )
            .onFocusChanged { campoFocado -> focado = campoFocado.isFocused },
        value = valor ?: "",
        onValueChange = { novoNome -> onValueChange(novoNome) },
        label = { Text(text = nomeCampo) },
        singleLine = singleline,
        textStyle = LocalTextStyle.current.copy(color = Color.White),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            focusedLabelColor = Color.White,
            unfocusedLabelColor = Color.White,
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White
        )
    )
}

//fun salvarAlteracoes(
//    usuario: Usuario, nomeExibicao: String,
//    nomeUsuario: String, biografia: String,
//    email: String, fotoPerfil: Uri?
//){
//    val usuarioAtualizado = usuario.copy(
//        nomeExibicao = nomeExibicao,
//        nomeUsuario = nomeUsuario,
//        biografia = biografia,
//        email = email,
//        foto = fotoPerfil
//    )
//
//    val indice = Dados.usuarios.indexOfFirst {
//        it.id == usuario.id
//    }
//
//    if(indice != -1){
//        Dados.usuarios[indice] = usuarioAtualizado
//    }
//}

suspend fun salvarAlteracoes(
    usuarioDAO: UsuarioDAO,
    usuario: Usuario,
    nomeExibicao: String,
    nomeUsuario: String,
    biografia: String,
    email: String,
    fotoPerfil: Uri?
){
    val usuarioAtualizado = usuario.copy(
        nomeExibicao = nomeExibicao,
        nomeUsuario = nomeUsuario,
        biografia = biografia,
        email = email,
        foto = fotoPerfil.toString()
    )

    usuarioDAO.atualizarUsuario(usuarioAtualizado)
}

@Composable
fun geraBotao(x: Float, texto: String, habilitado: Boolean,
              corHabilitado: Color = Color.DarkGray,
              onclick: () -> Unit, ){
    Button(
        modifier = Modifier.fillMaxWidth(x),
        contentPadding = PaddingValues(horizontal = 3.dp, vertical = 4.dp),
        onClick = onclick,
        enabled = habilitado,
        shape = RoundedCornerShape(13.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = corHabilitado,
            disabledContentColor = Color.White,
            contentColor = Color.White,
            disabledContainerColor = Color.DarkGray
        )){
        Text(text = texto)
    }
}



@Composable
fun TelaEditarPerfil(navController: NavController, idUsuario: Int = 0) {

    val context = LocalContext.current
    val db = remember { AppDatabase.getDatabase(context) }
    val usuarioDAO = remember { db.usuarioDAO() }
    val coroutineScope = rememberCoroutineScope()

    val usuario = Dados.usuarios.find { it.id == idUsuario }?: return


    var nomeExibicao by remember { mutableStateOf(usuario.nomeExibicao) }
    var nomeUsuario by remember { mutableStateOf(usuario.nomeUsuario) }
    var biografia by remember { mutableStateOf(usuario.biografia) }
    var email by remember { mutableStateOf(usuario.email) }
//    var fotoPerfil by remember { mutableStateOf(usuario.foto) }
    var fotoPerfil by remember { mutableStateOf(usuario.foto) }


    //seleciona foto da galeria
    val selecionarImagem = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->

        if(uri != null){
//            fotoPerfil = uri
            fotoPerfil = uri.toString()
        }
    }

    Surface(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState()),
        color = Color(0xFF1e1e1e),) {
        Row( modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 20.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BotaoVoltar(navController=navController)
                    Text(text = "Editar perfil", color = Color.White, textAlign = TextAlign.Center)

                    val habilitado =
                        nomeExibicao != usuario.nomeExibicao ||
                                nomeUsuario != usuario.nomeUsuario ||
                                biografia != usuario.biografia ||
                                email != usuario.email ||
                                fotoPerfil != usuario.foto

                    geraBotao(0.25f, "Feito",
                        habilitado,
                        corHabilitado = Color.Red,
                        onclick = {
//                            salvarAlteracoes(
//                                usuario, nomeExibicao, nomeUsuario,
//                                biografia, email, fotoPerfil
//                            )
//
//                            navController.navigate(Rotas.HOME)
                            coroutineScope.launch {
                                salvarAlteracoes(
                                    usuarioDAO = usuarioDAO,
                                    usuario = usuario,
                                    nomeExibicao = nomeExibicao,
                                    nomeUsuario = nomeUsuario,
                                    biografia = biografia,
                                    email = email,
                                    fotoPerfil = fotoPerfil?.let { Uri.parse(it) }
                                )

                                navController.navigate(Rotas.HOME)
                            }
                        }
                    )
                }


                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 30.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        gerarConteudoBloco(
                            x = 150,
                            y = 5,
                            cor = Color.Red,
                            raio = 75,
                            imagem = fotoPerfil?: R.drawable.foto_perfil_editar
                        )
                        geraBotao(0.2f, "Editar", habilitado = true,
                            onclick = {selecionarImagem.launch("image/*")}
                        )
                    }
                }


                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp, vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(30.dp)
                    ) {
                        CampoEditar("Nome",
                            nomeExibicao,
                            singleline = true,
                            onValueChange = { novoValor ->
                                nomeExibicao = novoValor
                            })
                        CampoEditar(
                            "Nome de usuário",
                            nomeUsuario,
                            singleline = true,
                            onValueChange = {novoValor -> nomeUsuario = novoValor})

                        CampoEditar(
                            "Biografia",
                            biografia,
                            singleline = false,
                            onValueChange = {novoValor -> biografia = novoValor})

                        CampoEditar(
                            "Email",
                            email,
                            singleline = true,
                            onValueChange = {novoValor -> email = novoValor})
                    }
                }
            }
        }
    }
}