package com.example.myapplication


import android.widget.Toast
import androidx.compose.material3.TextField
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.data.Dados
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//class VerAmigos : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            MyApplicationTheme {
//              // A surface container using the 'background' color from the theme
//                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
//                    TelaVerAmigos()
//                 }
//            }
//        }
//    }
//}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaVerAmigos(navController: NavController, idUsuario: Int = 0){
    val usuario = Dados.usuarios.find { it.id == idUsuario } ?: return
    val context = LocalContext.current
    val db = AppDatabase.getDatabase(context)
    val usuarioDAO = db.usuarioDAO()

    var amigos by remember { mutableStateOf<List<Usuario>>(emptyList()) }

    LaunchedEffect(idUsuario) {
        CoroutineScope(Dispatchers.IO).launch {
            amigos = usuarioDAO.buscarAmigos(idUsuario)
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFF1e1e1e)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
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
                        navController = navController,
                        onClick = { navController.navigate(Rotas.HOME) }
                    )

                    Text(
                        text = "Amigos",
                        color = Color.White,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                BarraPesquisaAmigos(
                    amigos = amigos,
                    onRemoverAmigo = { amigoId ->
                        CoroutineScope(Dispatchers.IO).launch {
                            usuarioDAO.removerAmigo(idUsuario, amigoId)
                            amigos = usuarioDAO.buscarAmigos(idUsuario)
                        }
                    },
                    onMensagemClick = { amigo ->
                        navController.navigate("chat/${amigo.id}")
                    },
                    navController = navController
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BarraPesquisaAmigos(
    amigos: List<Usuario>,
    onRemoverAmigo: (Int) -> Unit,
    onMensagemClick: (Usuario) -> Unit,
    navController: NavController
) {
    var pesquisarP by remember { mutableStateOf("") }

    val amigosFiltrados = amigos.filter {
        it.nomeExibicao.contains(pesquisarP, ignoreCase = true) ||
                it.nomeUsuario.contains(pesquisarP, ignoreCase = true)
    }

    Column {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .height(56.dp),
            color = Color(0xFF2c2c2c),
            shape = RoundedCornerShape(5.dp),
            border = BorderStroke(2.dp, Color.White)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(8.dp))

                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "buscar",
                    tint = Color.White
                )

                Spacer(modifier = Modifier.width(5.dp))

                TextField(
                    value = pesquisarP,
                    onValueChange = { pesquisarP = it },
                    singleLine = true,
                    modifier = Modifier.weight(1f),
                    textStyle = LocalTextStyle.current.copy(
                        color = Color.White
                    ),
                    placeholder = {
                        Text(
                            text = "Pesquisar amigos",
                            color = Color.Gray
                        )
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Lista otimizada para exibir os amigos filtrados
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            items(amigosFiltrados, key = { it.id }) { amigo ->
                ItemAmigo(
                    usuario = amigo,
                    imagem = R.drawable.user,
                    onMensagemClick = { onMensagemClick(amigo) },
                    onExcluirClick = { onRemoverAmigo(amigo.id) },
                    navController
                )
            }
        }
    }
}

@Composable
fun ItemAmigo(
    usuario: Usuario,
    imagem: Int? = null,
    onMensagemClick: () -> Unit,
    onExcluirClick: () -> Unit,
    navController: NavController
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 5.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF2c2c2c)
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Lado Esquerdo: Avatar e Nomes
            Row(
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        navController.navigate(
                            Rotas.VER_PERFIL_USUARIO.replace("{idUsuario}", usuario.id.toString())
                        )
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    modifier = Modifier.size(40.dp),
                    color = Color.White,
                    shape = CircleShape
                ) {
                    if (imagem != null) {
                        Image(
                            painter = painterResource(id = imagem),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                }

                Spacer(modifier = Modifier.width(10.dp))

                Column {
                    Text(text = usuario.nomeExibicao, color = Color.White)
                    Text(text = "@${usuario.nomeUsuario}", color = Color.Gray)
                }
            }

            // Lado Direito: 2 Ícones (Mensagem e Excluir)
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Ícone de Mensagem
                IconButton(onClick = onMensagemClick) {
                    Icon(
                        painter = painterResource(id = R.drawable.chat),
                        contentDescription = "Mensagem",
                        tint = Color.White,

                    )
                }

                // Ícone de Excluir (Remove da lista de amigos)
                IconButton(onClick = onExcluirClick) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Excluir amigo",
                        tint = Color.White
                    )
                }
            }
        }
    }
}