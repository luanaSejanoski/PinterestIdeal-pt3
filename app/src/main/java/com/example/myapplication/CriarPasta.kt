package com.example.myapplication

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Switch
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.data.Dados
import com.example.myapplication.data.local.AppDatabase
import com.example.myapplication.data.repository.PastaRepository
import com.example.myapplication.data.repository.PinRepository
import com.example.myapplication.data.repository.criarPasta
import com.example.myapplication.ui.pasta.CriarPastaViewModel
import com.example.myapplication.ui.pasta.CriarPastaViewModelFactory
import com.example.myapplication.ui.pasta.ItensPastaViewModel
import com.example.myapplication.ui.pasta.ItensPastaViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaCriarPasta(navController: NavController,
                   idUsuario: Int = 0,
                   viewModel: CriarPastaViewModel = viewModel(
                       key = "$idUsuario",
                       factory = CriarPastaViewModelFactory(
                           PastaRepository(
                               AppDatabase.getDatabase(LocalContext.current).pastaDAO()
                           ),
                           idUsuario
                       )
                   )
){

//    val usuario = Dados.usuarios.find { it.id == idUsuario }?: return;

//    var nomePasta by remember { mutableStateOf("") }
//    var privado by remember { mutableStateOf(false) }
//    var nomeFocado by remember { mutableStateOf(false) }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val context = LocalContext.current
    val db = AppDatabase.getDatabase(context)
    val pastaDAO = db.pastaDAO()
//    var pastaCriada by remember { mutableStateOf(false) }

    LaunchedEffect(uiState.pastaCriada) {
        if(uiState.pastaCriada) navController.navigate(Rotas.HOME)
    }



    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFF1e1e1e)
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
                BotaoVoltar(navController = navController,
                    onClick = {navController.navigate(Rotas.HOME)})

                Text(
                    text = "Criar uma Pasta",
                    color = Color.White,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
            }

            Box(
                modifier = Modifier.fillMaxSize()
            ) {

                Spacer(modifier = Modifier.padding(100.dp))

                Column(
                    modifier = Modifier.fillMaxSize()
                        .offset(y = 40.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Box(
                        modifier = Modifier
                            .padding(vertical = 2.dp)
                            .size(height = 180.dp, width = 280.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(0xFF3D3D3D))
                            .border(
                                width = 1.dp,
                                color = Color.Transparent,
                                shape = RoundedCornerShape(10.dp)
                            ),
                        contentAlignment = Alignment.Center,

                        ) {
                        
                            Box(
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .width(0.6.dp)
                                    .height(180.dp)
                                    .background(Color.Black)
                            )

                    }

                    Spacer(modifier = Modifier.height(70.dp))

                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(65.dp)
                            .padding(horizontal = 20.dp),
                        color = Color.Transparent,
                        shape = RoundedCornerShape(5.dp),
                        border = BorderStroke(
                            1.dp,
                            if (uiState.nomeFocado) Color.Red else Color.White
                        )
                    ) {
                        TextField(
                            value = uiState.nomePasta,
                            onValueChange = { viewModel.onNomeChange(it)},
                            modifier = Modifier.onFocusChanged {
                                uiState.nomeFocado = it.isFocused
                            },
                            textStyle = LocalTextStyle.current.copy(
                                color = Color.White
                            ),
                            placeholder = {
                                Text(
                                    text = "Nome da pasta",
                                    color = Color.White
                                )
                            },
                            colors = TextFieldDefaults.textFieldColors(
                                containerColor = Color.Transparent
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(30.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(15.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically

                    ) {
                        Column(
                            modifier = Modifier.weight(1f)
                               ,
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.Start,
                        ) {
                            Text(
                                text = "Tornar essa pasta privada",
                                color = Color.White,
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                text = "Defina quem pode ver sua pasta. As pastas são públicas por padrão, e qualquer pessoa pode vê-las.",
                                color = Color.LightGray,
                            )
                        }

                            Switch(
                                checked = uiState.privado,
                                onCheckedChange = { viewModel.onPrivadoChange(it) }
                            )
                    }
                }


                BtnCriarPasta(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    onClick = { viewModel.aoClicarCriarPasta(idUsuario) },
                    habilitado = uiState.nomePasta.isNotBlank()
                )
            }
        }
    }
}

@Composable
fun BtnCriarPasta(
    modifier: Modifier,
    habilitado: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 30.dp),
        enabled = habilitado,
        colors = ButtonDefaults.buttonColors(
            containerColor = if(habilitado )Color.Red else Color.Gray,
            contentColor = Color.White,
            disabledContainerColor = Color.LightGray,
            disabledContentColor = Color.White
        ),
        shape = RoundedCornerShape(5.dp)
    ) {
        Text(
            text = "Criar pasta",
            color = Color.White
        )
    }
}