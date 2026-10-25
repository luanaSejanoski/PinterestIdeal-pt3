package com.example.myapplication

import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.compose.ui.window.Dialog
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.core.net.toUri
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.myapplication.data.Dados
import com.example.myapplication.data.Pasta
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun gerarBlocoIcone(cor: Color? = null,
                    x: Int,
                    y: Int,
                    raio: Int? = 0,
                    imagem: Int? = null,
                    icon: Painter? = null,
                    texto: String? = null,
                    corTexto: Color = Color.White,
                    onClick: () -> Unit = {}
){
    Box(contentAlignment = Alignment.Center,
        modifier = Modifier.clickable {
            onClick()
        }) {
        gerarBloco(cor, x, y, raio, imagem, icon)
        if(!texto.isNullOrEmpty()){
            Text(text = texto, color = corTexto)
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun gerarBlocoImagem(
    pin: Pin,
    proporcao: Float,
    onClick: () -> Unit = {},
    onLongClick: () -> Unit = {}
){
    Box(modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(12.dp))
        .combinedClickable(
            onClick = onClick,
            onLongClick = onLongClick
        )
    ){
        Image(
            painter = rememberAsyncImagePainter(pin.imagem.toUri()),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(proporcao),
            contentScale = ContentScale.Crop
        )
    }
}

fun organizaImagensColuna(
    listaPins: List<Pin>,
    colunaEsquerda: MutableList<Pin>,
    colunaDireita: MutableList<Pin>
){
    var alturaEsquerda = 0f
    var alturaDireita = 0f


    listaPins.forEach { imagem ->
        // Decide em qual coluna colocar (menor)
        val alturaRelativa= imagem.altura.toFloat() / imagem.largura.toFloat() //quao alta a imagem é

        if(alturaEsquerda <= alturaDireita){
            colunaEsquerda.add(imagem)
            alturaEsquerda += alturaRelativa
        }else{
            colunaDireita.add(imagem)
            alturaDireita += alturaRelativa
        }
    }
}

fun salvarNomePasta(
    pasta: Pasta,
    nomePasta: String,
    pastaDAO: PastaDAO
){
    CoroutineScope(Dispatchers.IO).launch {
        mudarNomePasta(nomePasta, pasta.id, pastaDAO)
    }
}

fun salvarTituloPin(
    pin: Pin,
    tituloPin: String,
    pinDAO: PinDAO
){
    CoroutineScope(Dispatchers.IO).launch {
        alterarTituloPin(tituloPin, pin.id, pinDAO)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTelaItensPasta() {
    val navController = rememberNavController()

    TelaItensPasta(
        navController = navController,

        )
}
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)

@Composable
fun TelaItensPasta(navController: NavController,idPasta: Int = 0) {

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
        var mostrarDialogoMudarNome by remember { mutableStateOf(false) }
        var mostrarDialogoExcluir by remember { mutableStateOf(false) }
        var novoNome by remember { mutableStateOf("") }


        var imagemAmpliada by remember {mutableStateOf<Pin?>(null)}
        var editandoPin by remember { mutableStateOf(false)}
        var novoTitulo by remember { mutableStateOf("") }

        val selecionarImagens = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetMultipleContents()
        ) { uris ->

            uris.forEach { uri ->

                val drawable = context.contentResolver
                    .openInputStream(uri)
                    ?.use {
                        Drawable.createFromStream(it, null)
                    }

                val largura = drawable?.intrinsicWidth ?: 0
                val altura = drawable?.intrinsicHeight ?: 0


                CoroutineScope(Dispatchers.IO).launch {
                  val novoPinId = adicionarPin(uri.toString(), largura, altura, pinDAO)
                    adicionarPinNaPasta(novoPinId, pasta.id, pastaDAO)
                }
            }
        }

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
                        Column(modifier = Modifier.fillMaxWidth()
                        ) {
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
                                            gerarBlocoIcone(
                                                Color(0xFF1e1e1e),
                                                30,
                                                5,
                                                icon = painterResource(R.drawable.editar),
                                                onClick = {
                                                    novoNome = nomePasta
                                                    mostrarDialogoMudarNome = true
                                                }
                                            )
                                        }
                                        if (mostrarDialogoMudarNome) {
                                            AlertDialog(
                                                onDismissRequest = {
                                                    mostrarDialogoMudarNome = false
                                                },

                                                title = {
                                                    Text("Renomear pasta")
                                                },

                                                text = {
                                                    TextField(
                                                        value = novoNome,
                                                        onValueChange = {
                                                            novoNome = it
                                                        },
                                                        singleLine = true
                                                    )
                                                },

                                                confirmButton = {
                                                    TextButton(
                                                        onClick = {
                                                            nomePasta = novoNome
                                                            mostrarDialogoMudarNome = false
                                                            salvarNomePasta(pasta, nomePasta, pastaDAO)
                                                        }
                                                    ) {
                                                        Text("Salvar")
                                                    }
                                                },

                                                dismissButton = {
                                                    TextButton(
                                                        onClick = {
                                                            mostrarDialogoMudarNome = false
                                                        }
                                                    ) {
                                                        Text("Cancelar")
                                                    }
                                                }
                                            )
                                        }
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Row(
                                                modifier = Modifier
                                                    .clip(RoundedCornerShape(50))
                                                    .background(Color(0xFF313030))
                                                    .border(
                                                        width = 1.dp,
                                                        color = Color.Transparent
                                                    )
                                                    .padding(horizontal = 8.dp)
                                                    .combinedClickable(
                                                        onClick = {},
                                                        onLongClick = {
                                                            pasta.privado = !pasta.privado
                                                            Toast.makeText(
                                                                context,
                                                                "Visibilidade da pasta alterada!",
                                                                Toast.LENGTH_LONG
                                                            ).show()
                                                        }
                                                    ),
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                            ) {
                                                gerarBlocoIcone(x = 23, y = 5,
                                                    icon = painterResource( if(pasta.privado) R.drawable.pasta_privada else R.drawable.pasta_publica)
                                                )

                                                Text(
                                                    text = if(pasta.privado) "Pasta privada" else "Pasta pública",
                                                    color = Color.White,
                                                )
                                            }

                                            Text(
                                                text = " • $totalPinsPasta Pins",
                                                color = Color.LightGray,
                                            )
                                        }
                                    }
                                }

                                Row() {
                                    gerarBlocoIcone(
                                        Color(0xFF1e1e1e),
                                        30,
                                        5,
                                        icon = painterResource(R.drawable.carregar_imagem),
                                        onClick = { selecionarImagens.launch("image/*") }
                                    )
                                    gerarBlocoIcone(
                                        Color(0xFF1e1e1e),
                                        30,
                                        5,
                                        icon = painterResource(R.drawable.excluir),
                                        onClick = {
                                            mostrarDialogoExcluir = true
                                        }
                                    )


                                    if (mostrarDialogoExcluir) {
                                        AlertDialog(
                                            onDismissRequest = { mostrarDialogoExcluir = false },
                                            title = { Text("Excluir pasta") },
                                            text = { Text("Tem certeza de que deseja excluir essa pasta?") },
                                            confirmButton = {
                                                TextButton(
                                                    onClick = {
                                                        mostrarDialogoExcluir = false

                                                        CoroutineScope(Dispatchers.IO).launch {
                                                            excluirPasta(pasta, pastaDAO )
                                                        }

                                                        Toast.makeText(
                                                            context,
                                                            "Pasta removida!",
                                                            Toast.LENGTH_LONG
                                                        ).show()

                                                        navController.popBackStack()
                                                    }
                                                ) {
                                                    Text("Confirmar")
                                                }
                                            },
                                            dismissButton = {
                                                TextButton(
                                                    onClick = { mostrarDialogoExcluir = false }
                                                ) {
                                                    Text("Cancelar")
                                                }
                                            }
                                        )
                                    }
                                }
                            }


                            //cria lista das imagens
                            val colunaEsquerda = mutableListOf<Pin>()
                            val colunaDireita = mutableListOf<Pin>()
//                            val pinsDaPasta = Dados.pins.filter { pin -> pin.id in pasta.pins }.reversed()
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
                                            onLongClick = {
                                                CoroutineScope(Dispatchers.IO).launch {
                                                    excluirPinDaPasta(item.id, pasta.id, pastaDAO, pinDAO)
                                                }
                                                Toast.makeText(
                                                    context,
                                                    "Pin removido!!",
                                                    Toast.LENGTH_SHORT
                                                ).show()}
                                        )
                                    }
                                }
                                Column(
                                    modifier = Modifier.weight(1f),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    colunaDireita.forEach { item ->
                                        val proporcao = item.largura.toFloat() / item.altura.toFloat()

                                        gerarBlocoImagem(item, proporcao,
                                            onClick = {imagemAmpliada = item},
                                            onLongClick = {
                                                CoroutineScope(Dispatchers.IO).launch {
                                                    excluirPinDaPasta(item.id, pasta.id, pastaDAO, pinDAO)
                                                }
                                                Toast.makeText(
                                                    context,
                                                    "Pin removido!!",
                                                    Toast.LENGTH_SHORT
                                                ).show()}
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
                        painter = rememberAsyncImagePainter(pin.imagem),
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
                        gerarBlocoIcone(
                            Color(0xFF1E1E1E), 30, 5,
                            icon = painterResource(R.drawable.editar),
                            onClick = {
                                novoTitulo = pin.titulo
                                editandoPin = true
                            }
                        )
                    }
                }
            }
        }
        if (editandoPin) {
            AlertDialog(
                onDismissRequest = { editandoPin = false },
                title = { Text("Editar título") },
                text = {
                    TextField(
                        value = novoTitulo,
                        onValueChange = { novoTitulo = it },
                        singleLine = true
                    )
                },
                confirmButton = {
                    TextButton(onClick = {
                        imagemAmpliada?.let { pin ->
                            salvarTituloPin(pin, novoTitulo, pinDAO)
                            imagemAmpliada = pin.copy(titulo = novoTitulo)
                        }
                        editandoPin = false
                    }) { Text("Salvar") }
                },
                dismissButton = {
                    TextButton(onClick = { editandoPin = false }) { Text("Cancelar") }
                }
            )
        }
    }
}