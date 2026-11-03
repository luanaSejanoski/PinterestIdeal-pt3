package com.example.myapplication
import EditarPerfilViewModelFactory
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter


import com.example.myapplication.data.local.AppDatabase
import com.example.myapplication.data.repository.UsuarioRepository
import com.example.myapplication.ui.componentes.BotaoVoltar
import com.example.myapplication.ui.perfil.EditarPerfilViewModel


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
fun TelaEditarPerfil(
    navController: NavController,
    viewModel: EditarPerfilViewModel = viewModel(
     factory = EditarPerfilViewModelFactory(
         UsuarioRepository (AppDatabase.getDatabase(LocalContext.current).usuarioDAO())
        )
    )
)
{


    //tds as variaveis vem daq
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current


//    val db = remember { AppDatabase.getDatabase(context) }
//    val usuarioDAO = remember { db.usuarioDAO() }
//    val coroutineScope = rememberCoroutineScope()

//    var usuario by remember { mutableStateOf<Usuario?>(null) }
//
//    var nomeExibicao by remember { mutableStateOf("") }
//    var nomeUsuario by remember { mutableStateOf("") }
//    var biografia by remember { mutableStateOf("") }
//    var email by remember { mutableStateOf("") }
//    var fotoPerfil by remember { mutableStateOf<String?>(null) }

//    LaunchedEffect(idUsuario) {
//        val usuarioBanco = buscarUsuarioPorId(usuarioDAO, idUsuario)
//        if (usuarioBanco != null) {
//            usuario = usuarioBanco
//            nomeExibicao = usuarioBanco.nomeExibicao
//            nomeUsuario = usuarioBanco.nomeUsuario
//            biografia = usuarioBanco.biografia ?: ""
//            email = usuarioBanco.email ?: ""
//            fotoPerfil = usuarioBanco.foto
//        }
//    }

    val usuarioAtual = uiState.usuario


    if (usuarioAtual == null) {
        Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFF1e1e1e)) {}
        return
    }

    //seleciona foto da galeria
    val selecionarImagem = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri ->
        if(uri != null) {
            context.contentResolver.takePersistableUriPermission(
                uri,
                Intent.FLAG_GRANT_READ_URI_PERMISSION
            )

            viewModel.onFotoChange(uri.toString())
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
                        uiState.nomeExibicao != usuarioAtual.nomeExibicao ||
                                uiState.nomeUsuario != usuarioAtual.nomeUsuario ||
                                uiState.biografia != (usuarioAtual.biografia ?: "") ||
                                uiState.email != (usuarioAtual.email ?: "") ||
                                uiState.fotoPerfil != usuarioAtual.foto

                    geraBotao(0.25f, "Feito",
                        habilitado,
                        corHabilitado = Color.Red,
                        onclick = {
                            viewModel.aoClicarSalvar(
                                onSalvoComSucesso = {
                                        navController.navigate(Rotas.HOME) {
                                        popUpTo(Rotas.HOME) { inclusive = true }
                                    }
                                }
                            )
                            navController.navigate(Rotas.HOME)
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
                        val imagemPerfil = if (uiState.fotoPerfil != null && uiState.fotoPerfil != "") {
                            uiState.fotoPerfil!!.toUri()
                        } else {
                            R.drawable.foto_perfil_editar
                        }

                        gerarConteudoBloco(
                            x = 150,
                            y = 5,
                            cor = Color.Red,
                            raio = 75,
                            imagem = imagemPerfil
                        )
                        geraBotao(0.2f, "Editar", habilitado = true,
                            onclick = {selecionarImagem.launch(arrayOf("image/*"))}
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
                        CampoEditar(
                            nomeCampo = "Nome",
                            valor = uiState.nomeExibicao,
                            singleline = true,
                            onValueChange = viewModel::onNomeExibicaoChange)
                        CampoEditar(
                            nomeCampo = "Nome de usuário",
                            valor = uiState.nomeUsuario,
                            singleline = true,
                            onValueChange = viewModel::onNomeUsuarioChange)
                        CampoEditar(
                            nomeCampo = "Biografia",
                            valor = uiState.biografia,
                            singleline = false,
                            onValueChange = viewModel::onBioChange)
                        CampoEditar(
                            nomeCampo = "Email",
                            valor = uiState.email,
                            singleline = true,
                            onValueChange = viewModel::onEmailChange)
                    }
                }
            }
        }
    }
}
