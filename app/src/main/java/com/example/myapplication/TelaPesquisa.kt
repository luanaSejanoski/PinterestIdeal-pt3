
package com.example.myapplication


import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.key
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.myapplication.data.Dados
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.coroutineScope
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch

@Composable
fun TelaPesquisa(navController: NavController, idUsuario: Int = 0) {
    val context = LocalContext.current

    val db = remember { AppDatabase.getDatabase(context) }
    val usuarioDAO = remember { db.usuarioDAO() }

    val coroutineScope = rememberCoroutineScope()

//    val usuario = Dados.usuarios.find{it.id == idUsuario}?: return;

    var usuario by remember { mutableStateOf<Usuario?>(null) }
    var idsAmigos by remember { mutableStateOf<List<Int>>(emptyList()) }
    var listaUsuarios by remember { mutableStateOf<List<Usuario>>(emptyList()) }

    LaunchedEffect(Unit) {
        val listaAmigosBanco = buscarAmigosDoUsuario(usuarioDAO, idUsuario)
        idsAmigos = listaAmigosBanco.map { amigo -> amigo.id }

        listaUsuarios = listarUsuarios(usuarioDAO)
        usuario = buscarUsuarioPorId(usuarioDAO, idUsuario)
    }


    val usuarioAtual = usuario

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFF1e1e1e)
    ) {

        Box(
            modifier = Modifier.fillMaxSize()
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 50.dp)
            ) {

                Spacer(modifier = Modifier.height(40.dp))

                if(usuarioAtual != null) {
                    BarraPesquisa(
                        usuarios = listaUsuarios,
                        usuarioLogado = usuarioAtual,
                        idsAmigos = idsAmigos,
                        onAdicionarAmigo = { amigo ->
                            coroutineScope.launch {
                                if (amigo.id != idUsuario && amigo.id !in idsAmigos) {
                                    adicionarAmigoBanco(usuarioDAO, idUsuario, amigo.id)
                                    idsAmigos = idsAmigos + amigo.id
                                }
                            }
                        },
                        navController = navController
                    )
                }
            }
        }
    }
}

@Composable
fun criaBotao(x: Float,
              texto: String,
              adicionado: Boolean,
              onClick: () -> Unit) {

    Button(
        modifier = Modifier.fillMaxWidth(x),
        contentPadding = PaddingValues(horizontal = 3.dp, vertical = 3.dp),
        onClick = onClick,
        enabled = !adicionado,
        shape = RoundedCornerShape(13.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (adicionado) Color.Gray else Color.Red,
            contentColor = Color.White,
            disabledContentColor = Color.LightGray,
            disabledContainerColor = Color.DarkGray
        )
    ) {
        Text(text = if(adicionado) "Adicionado" else texto)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BarraPesquisa(
    usuarios: List<Usuario>,
    idsAmigos: List<Int>,
    onAdicionarAmigo: (Usuario) -> Unit,
    usuarioLogado: Usuario,
    navController: NavController
) {
    var pesquisarP by remember { mutableStateOf("") }

    val usuariosFiltrados = usuarios.filter {
        it.id != 0 && (
                it.nomeExibicao.contains(pesquisarP, ignoreCase = true) ||
                it.nomeUsuario.contains(pesquisarP, ignoreCase = true)
            )

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
                            text = "Pesquisar pessoas",
                            color = Color.Gray
                        )
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Usuários encontrados
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(
                items = usuariosFiltrados,
                key = { usuario -> usuario.id }
            ) { usuario ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 5.dp)
                        .clickable{
                            navController.navigate(Rotas.VER_PERFIL_USUARIO.replace("{idUsuario}", usuario.id.toString()))
                        },

                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF2c2c2c)
                    )
                ) {
                    Usuarios(
                        usuario = usuario,
                        usuarioLogado = usuarioLogado,
                        isAmigo = usuario.id in idsAmigos,
                        imagem = R.drawable.user,
                        onAdicionarAmigo = onAdicionarAmigo
                    )
                }
            }
        }
    }
}
@Composable
fun Usuarios(usuario: Usuario,
             usuarioLogado: Usuario,
             isAmigo: Boolean,
             imagem: Int? = null,
             onAdicionarAmigo: (Usuario) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    )
    {

        var adicionado by remember(isAmigo) {
            mutableStateOf(isAmigo)
        }

        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Avatar (circulo)
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
                Text(text = "@${usuario.nomeUsuario}", color = Color.White)
            }

        }
        if(usuario.id != usuarioLogado.id ) {

            criaBotao(
                x = 0.25f,
                texto = "Adicionar",
                adicionado = adicionado,
                onClick = {
                    if (usuario.id != usuarioLogado.id) {
                        onAdicionarAmigo(usuario)
                        adicionado = true
                    }
                }
            )
        }
    }
}




