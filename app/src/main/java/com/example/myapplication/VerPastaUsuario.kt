package com.example.myapplication

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.net.toUri
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.myapplication.data.Dados
import com.example.myapplication.data.Pasta
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun TelaVerPastaUsuario(navController: NavController, idPasta: Int) {

    val context = LocalContext.current
    val db = AppDatabase.getDatabase(context)
    val pastaDAO = db.pastaDAO()
    var pasta by remember {mutableStateOf<Pasta?>(null)}

    var totalPinsPasta by remember { mutableStateOf(0) }
    var pins by remember { mutableStateOf<List<Pin>>(emptyList()) }



    LaunchedEffect(idPasta) {
            val pastaEncontrada = withContext(Dispatchers.IO){
                buscarPastaId(pastaDAO, idPasta)
            }

            val total = withContext(Dispatchers.IO){
                contarPinsPasta(pastaDAO, idPasta)
            }

            val lista = withContext(Dispatchers.IO){
                buscarPinsPasta(pastaDAO, idPasta)
            }

            pasta = pastaEncontrada
            totalPinsPasta = total
            pins = lista
        }


    val pastaAtual = pasta
    if (pastaAtual == null) {
        Surface(modifier = Modifier.fillMaxSize(), color = Color.Transparent) {}
        return
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

        var nomePasta by remember { mutableStateOf(pastaAtual.nome) }
        var imagemAmpliada by remember {mutableStateOf<Pin?>(null)}


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
                                    onClick = {imagemAmpliada = item},
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
                                    onClick = {imagemAmpliada = item},
                                )
                            }
                        }
                    }
                }
            }
        }

        //amplia a imagem quando clicada
        imagemAmpliada?.let{pin ->
            Dialog(
                onDismissRequest = { imagemAmpliada = null}

            ) {
                Column(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0x74525252))
                        .padding(12.dp)
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(pin.imagem.toUri()),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 450.dp)
                            .clickable {
                                imagemAmpliada = null
                            },
                        contentScale = ContentScale.Fit
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = if (pin.titulo.isNotBlank()) pin.titulo else "Sem título",
                            color = if (pin.titulo.isNotBlank()) Color.White else Color.Gray,
                            modifier = Modifier.weight(1f)
                        )

                    }
                }
            }
        }
    }
}


