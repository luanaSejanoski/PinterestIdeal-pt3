package com.example.myapplication

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.net.toUri
import coil.compose.rememberAsyncImagePainter
import com.example.myapplication.data.Dados
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.data.Pasta
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun gerarBotao(x: Float,
               texto: String,
               habilitado: Boolean,
               onclick: () -> Unit = {}){
    Button(
        modifier = Modifier.fillMaxWidth(x),
        contentPadding = PaddingValues(horizontal = 3.dp, vertical = 3.dp),
        onClick = onclick,
        enabled = habilitado,
        shape = RoundedCornerShape(13.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.DarkGray,
            contentColor = Color.White,
            disabledContainerColor = (Color(0xFF757575))
        )){
        Text(text = texto)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTelaPerfil() {
    val navController = rememberNavController()

    TelaPerfil(
        navController = navController,

        )
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun gerarPasta(pasta: Pasta, pastaDAO: PastaDAO, onclick: () -> Unit){

    var totalPinsPasta by remember { mutableStateOf(0) }
    var pins by remember { mutableStateOf<List<Pin>>(emptyList()) }

    LaunchedEffect(pasta.id) {
        CoroutineScope(Dispatchers.IO).launch {
            totalPinsPasta = contarPinsPasta(pastaDAO,pasta.id)
            pins = buscarDoisUltimosPins(pastaDAO, pasta.id)
        }
    }


    //pega a imagem dos dois ultimos pins
    val imagens = pins.map{pin -> pin.imagem}

    Column(
        modifier= Modifier.fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .padding(vertical = 2.dp)
                .size(height = 130.dp, width = 180.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Color(0xFF3D3D3D))
                .border(
                    width = 1.dp,
                    color = Color.Transparent,
                    shape = RoundedCornerShape(10.dp)
                )
                .clickable{onclick()},
            contentAlignment = Alignment.Center,

        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
            ) {

                imagens.forEach { imagem ->
                        Image(
                            painter = rememberAsyncImagePainter(imagem),
                            contentDescription = null,
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight(),
                                contentScale = ContentScale.Crop

                        )
                }

            }
            if(imagens.size != 1) {
                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .width(0.6.dp)
                        .height(130.dp)
                        .background(Color.Black)
                )
            }
        }

        Column(modifier = Modifier.width(180.dp)) {
            Text(
                text = "${pasta.nome}",
                color = Color.White,
                fontSize = 14.sp
            )
            Text(
                text = "$totalPinsPasta Pins",
                color = Color.White,
                fontSize = 12.sp
            )
        }

    }
}


@Composable
fun TelaPerfil(navController: NavHostController, idUsuario: Int = 0) {


    val context = LocalContext.current
    val db = AppDatabase.getDatabase(context)
    val pastaDAO = db.pastaDAO()
    val usuarioDAO = db.usuarioDAO()
    var usuario by remember { mutableStateOf<Usuario?>(null) }

    var pastasDoUsuario by remember { mutableStateOf<List<Pasta>>(emptyList()) }
    var totalAmigos by remember{mutableStateOf(0)}

    LaunchedEffect(idUsuario) {
        CoroutineScope(Dispatchers.IO).launch {
            usuario = buscarUsuarioPorId(usuarioDAO,idUsuario)
            pastasDoUsuario = buscarPastas(pastaDAO, idUsuario)
            totalAmigos = contarAmigosUsuario(usuarioDAO,idUsuario)
        }
    }

        val usuarioAtual = usuario ?: return

        Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFF1e1e1e)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(modifier = Modifier.padding(top = 60.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .height(60.dp)
                ) {
                    Column {
                            val fotoPerfil = usuarioAtual.foto?.toUri()
                        Image(
                            painter = rememberAsyncImagePainter(fotoPerfil?: R.drawable.foto_perfil_editar),
                            contentDescription = "Foto de perfil",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(60.dp)
                                .clip(CircleShape)
                        )
                    }
                    Column(
                        modifier = Modifier.padding(start = 10.dp),
                        verticalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        Text(text = "${usuarioAtual.nomeExibicao}", color = Color.White, fontSize = 20.sp)
                        Text(text = "${usuarioAtual.nomeUsuario}", color = Color.White, fontSize = 15.sp)
                    }
                }
            }
            Row(
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 5.dp)
                        .height(70.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(start = 10.dp, top = 10.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(
                            text = "$totalAmigos amigos",
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.clickable{
                                navController.navigate(
                                    Rotas.VER_AMIGOS.replace("{idUsuario}", usuarioAtual.id.toString())
                                )
                            }
                        )
                        Text(
                            text = "${usuarioAtual.biografia}".take(50),
                            color = Color.White,
                            fontSize = 15.sp,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                }

            }
            Row(
                modifier = Modifier
                    .padding(vertical = 10.dp).size(height = 40.dp, width = 130.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize().padding(start = 30.dp),
                    shape = RoundedCornerShape(15.dp),
                ) {
                    gerarBotao(0.2f, "Editar perfil", habilitado = true,
                        onclick = {
                            navController.navigate(Rotas.EDITARPERFIL)
                        });
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Column(
                    modifier = Modifier
                        .size(height = 40.dp, width = 90.dp)
                        .padding(top = 10.dp, end = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Pastas",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Row(modifier = Modifier
                        .height(1.dp)
                        .width(50.dp)
                        .background(Color.White)
                    ){}

                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(pastasDoUsuario.chunked(2)){ linha ->
                    Row(
                        modifier = Modifier.fillMaxWidth()
                            .padding(horizontal = 15.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),

                    ){
                        linha.forEach { pasta ->
                            Column(
                                modifier = Modifier.weight(1f),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Card(
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = CardDefaults.cardColors(
                                        containerColor = Color.Transparent
                                    )
                                ) {
                                    Box(
                                        modifier = Modifier.fillMaxWidth(),
                                        contentAlignment = Alignment.CenterStart
                                    ) {
                                        gerarPasta(
                                            pasta,
                                            pastaDAO,
                                            onclick = {
                                                navController.navigate(
                                                    Rotas.DETALHES_PASTA.replace(
                                                        "{idPasta}",
                                                        pasta.id.toString()
                                                    )
                                                )
                                            })
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}