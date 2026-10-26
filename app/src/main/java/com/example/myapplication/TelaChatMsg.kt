
package com.example.myapplication

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.example.myapplication.data.Dados
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun TelaMsg(navController: NavController,
            idAmigo: Int,
            idUsuario: Int) {

    val context = LocalContext.current
    val db = AppDatabase.getDatabase(context)
    val mensagemDAO = db.mensagemDAO()
    val usuarioDAO = db.usuarioDAO()

    var mensagens by remember { mutableStateOf<List<Mensagem>>(emptyList()) }
    var mensagem by remember { mutableStateOf("") }
    var amigo by remember { mutableStateOf<Usuario?>(null) }

    LaunchedEffect(idUsuario, idAmigo) {
        CoroutineScope(Dispatchers.IO).launch {
            mensagens = mensagemDAO.buscarConversa(idUsuario, idAmigo)
            amigo = usuarioDAO.buscarUsuarioId(idAmigo)
        }
    }

//    val remetente = Dados.usuarios.find { it.id == 0 }
//    val destinatario = Dados.usuarios.find { it.id == idAmigo }
//
//    val mensagensConversa = Dados.mensagens.filter { mensagem ->
//        (mensagem.remetente.id == 0 &&
//                mensagem.destinatario.id == idAmigo) ||
//
//                (mensagem.remetente.id == idAmigo &&
//                        mensagem.destinatario.id == 0)
//    }

    val listaState = rememberLazyListState()

//    var proximoId by remember {
//        mutableIntStateOf(
//            (mensagens.maxOfOrNull { it.id } ?: 0) + 1
//        )
//    }

    // Enviar mensagem
    fun enviarMensagem() {
        if (mensagem.isNotBlank()) {
            val texto = mensagem.trim()
            mensagem = ""
            CoroutineScope(Dispatchers.IO).launch {
                mensagemDAO.inserir(
                    Mensagem(
                        texto = texto,
                        remetenteId = idUsuario,
                        destinatarioId = idAmigo
                    )
                )
                mensagens = mensagemDAO.buscarConversa(idUsuario, idAmigo)
            }
        }
    }

    // vai para a mensagem mais recente
    LaunchedEffect(mensagens.size) {
        if (mensagens.isNotEmpty()) {
            listaState.animateScrollToItem(0)
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFF1E1E1E)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 5.dp, vertical = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BotaoVoltar(
                    navController = navController
                )

                Text(
                    text = "${amigo?.nomeExibicao}",
                    color = Color.White,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
            }

            // Mensagens começando de baixo
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                state = listaState,
                reverseLayout = true,
                verticalArrangement = Arrangement.spacedBy(
                    8.dp,
                    Alignment.Bottom
                ),
                contentPadding = PaddingValues(vertical = 12.dp)
            ) {
                items(mensagens.reversed()) { mensagemEnviada ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = if (mensagemEnviada.remetenteId == idUsuario)
                            Arrangement.End
                        else
                            Arrangement.Start
                    ) {

                        Surface(
                            color = Color.Transparent,
                            shape = RoundedCornerShape(12.dp),
                            border = BorderStroke(1.dp, Color.White),
                            modifier = Modifier.widthIn(max = 280.dp)
                        ) {
                            Text(
                                text = mensagemEnviada.texto,
                                color = Color.White,
                                softWrap = true,
                                modifier = Modifier
                                    .widthIn(max = 280.dp)
                                    .padding(12.dp),
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            }

            // Campo de mensagem e botão de envio
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF1E1E1E))
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CampoMsg(
                    mensagem = mensagem,
                    onMensagemChange = { mensagem = it },
                    onEnviar = { enviarMensagem() },
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(8.dp))

                IconButton(
                    onClick = { enviarMensagem() }
                ) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = "Enviar mensagem",
                        tint = Color.Red
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CampoMsg(
    mensagem: String,
    onMensagemChange: (String) -> Unit,
    onEnviar: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        color = Color(0xFF2C2C2C),
        shape = RoundedCornerShape(5.dp),
        border = BorderStroke(1.dp, Color.White)
    ) {
        BasicTextField(
            value = mensagem,
            onValueChange = onMensagemChange,
            modifier = Modifier
                .fillMaxWidth()
                .onPreviewKeyEvent { evento ->
                    if (
                        evento.key == Key.Enter &&
                        evento.type == KeyEventType.KeyDown
                    ) {
                        onEnviar()
                        true
                    } else {
                        false
                    }
                }
                .padding(
                    horizontal = 12.dp,
                    vertical = 12.dp
                ),
            textStyle = TextStyle(
                color = Color.White,
                fontSize = 16.sp
            ),
            singleLine = true,
            decorationBox = { innerTextField ->
                if (mensagem.isEmpty()) {
                    Text(
                        text = "Digite uma mensagem...",
                        color = Color.Gray
                    )
                }
                innerTextField()
            }
        )
    }
}