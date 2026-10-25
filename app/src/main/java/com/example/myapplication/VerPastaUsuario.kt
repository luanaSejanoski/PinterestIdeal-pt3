package com.example.myapplication

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.net.toUri
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.myapplication.data.Dados
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun TelaVerPastaUsuario(navController: NavController, idPasta: Int?) {
    val pasta = Dados.pastas.find{it.id == idPasta}?: return

    val context = LocalContext.current
    val db = AppDatabase.getDatabase(context)
    val pastaDAO = db.pastaDAO()
    val pinDAO = db.pinDAO()

    var totalPinsPasta by remember { mutableStateOf(0) }
    var pins by remember { mutableStateOf<List<Pin>>(emptyList()) }

    LaunchedEffect(pasta.id) {
        CoroutineScope(Dispatchers.IO).launch {
            totalPinsPasta = contarPinsPasta(pastaDAO,pasta.id)
            pins = buscarPinsPasta(pastaDAO, pasta.id)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1e1e1e)
                ),
                navigationIcon = {
                    BotaoVoltar(
                        modifier = Modifier.padding(top = 25.dp, start = 6.dp),
                        navController = navController)
                }
            )
        }
    ) { paddingValues ->

        var nomePasta by remember { mutableStateOf(pasta.nome) }
        var imagemAmpliada by remember {mutableStateOf<Uri?>(null)}


        Surface(
            modifier = Modifier.fillMaxSize()
                .padding(paddingValues),
            color = Color(0xFF1e1e1e)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    //informações da pasta
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp, vertical = 20.dp),
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row() {
                            Column() {
                                Row() {
                                    Text(
                                        text = "$nomePasta",
                                        color = Color.White,
                                        fontSize = 20.sp
                                    )
                                }
                                Row() {
                                    Text(
                                        text = "$totalPinsPasta Pins",
                                        color = Color.White
                                    )
                                }
                            }
                        }
                    }


                    //cria lista das imagens

                    val colunaEsquerda = mutableListOf<Pin>()
                    val colunaDireita = mutableListOf<Pin>()

                    organizaImagensColuna(pins, colunaEsquerda, colunaDireita)


                    //área das imagens
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .padding(3.dp)
                        .verticalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    )
                    {
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            colunaEsquerda.forEach { item ->
                                // Define a proporção da imagem para que ela mantenha suas medidas originais na tela
                                val proporcao = item.largura.toFloat() / item.altura.toFloat()

                                gerarBlocoImagem(item, proporcao,
                                    onClick = {imagemAmpliada = item.imagem.toUri()},
                                    )
                            }
                        }
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            colunaDireita.forEach { item ->
                                val proporcao =
                                    item.largura.toFloat() / item.altura.toFloat()
                                gerarBlocoImagem(item, proporcao,
                                    onClick = {imagemAmpliada = item.imagem.toUri()},
                                )
                            }
                        }
                    }
                }
            }
        }

        //amplia a imagem quando clicada
        imagemAmpliada?.let{imagem ->
            Dialog(
                onDismissRequest = {imagemAmpliada = null}
            ){
                Image(
                    painter = rememberAsyncImagePainter(imagem),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { imagemAmpliada = null },
                    contentScale = ContentScale.Fit
                )

            }
        }
    }
}


