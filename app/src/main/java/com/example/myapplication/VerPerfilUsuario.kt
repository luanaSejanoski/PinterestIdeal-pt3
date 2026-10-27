package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.myapplication.data.Dados
import com.example.myapplication.data.Pasta
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun TelaVerPerfilUsuario(navController: NavHostController, idUsuario: Int = 1){

    val usuario = Dados.usuarios.find { it.id == idUsuario }?: return

    val context = LocalContext.current
    val db = AppDatabase.getDatabase(context)
    val pastaDAO = db.pastaDAO()
    val usuarioDAO = db.usuarioDAO()

    var pastasDoUsuario by remember { mutableStateOf<List<Pasta>>(emptyList()) }
    var totalAmigos by remember{mutableStateOf(0)}

    LaunchedEffect(idUsuario) {
        CoroutineScope(Dispatchers.IO).launch {
            pastasDoUsuario = buscarPastas(pastaDAO, idUsuario)
            totalAmigos = usuarioDAO.contarAmigos(idUsuario)
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFF1e1e1e)
    ){
        Column(modifier = Modifier.fillMaxSize()){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 5.dp, vertical = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BotaoVoltar(
                    navController = navController,
                    onClick = { navController.popBackStack() }
                )

                Text(
                    text = "Perfil",
                    color = Color.White,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
            }

            // Usuario
            Row(
                modifier = Modifier.padding(top = 10.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .height(60.dp)
                ) {
                    Column {
                        Image(
                            painter = rememberAsyncImagePainter(usuario.foto?: R.drawable.foto_perfil_editar),
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
                        Text(text = "${usuario.nomeExibicao}", color = Color.White, fontSize = 20.sp)
                        Text(text = "${usuario.nomeUsuario}", color = Color.White, fontSize = 15.sp)
                    }
                }
            }

            // Amigos e bio
            Row {
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
                        )
                        Text(
                            text = "${usuario.biografia}".take(50),
                            color = Color.White,
                            fontSize = 15.sp,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }

            // Área de pastas
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
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

            // Pastas criadas
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
//                val pastasDoUsuario = Dados.pastas.filter { it.id in usuario.pastas }
                items(pastasDoUsuario.chunked(2)){ linha ->
                    Row(
                        modifier = Modifier.fillMaxWidth()
                            .padding(horizontal = 15.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
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
                                                    Rotas.VER_PASTA_USUARIO.replace(
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