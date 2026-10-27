package com.example.myapplication

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun MenuInferior(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val rotaAtual = navBackStackEntry?.destination?.route

    NavigationBar(
        containerColor = Color(0xFF757575)
    ) {
        NavigationBarItem(
            selected = rotaAtual == Rotas.HOME,
            onClick = {
                    if (navController.currentDestination?.route != Rotas.HOME) {
                        navController.navigate(Rotas.HOME) {
                            popUpTo(Rotas.HOME) {
                                inclusive = false
                            }
                            launchSingleTop = true
                        }
                    }
            },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.home),
                    contentDescription = "Tela inicial",
                    tint = Color.White
                )
            }
        )

        NavigationBarItem(
            selected = rotaAtual == Rotas.PESQUISAR_PESSOAS,
            onClick = {
                navController.navigate(Rotas.PESQUISAR_PESSOAS)
            },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.magnifier),
                    contentDescription = "Pesquisar pessoas",
                    tint = Color.White,
                )
            }
        )

        NavigationBarItem(
            selected = rotaAtual == Rotas.CRIAR_PASTA,
            onClick = {
                navController.navigate(Rotas.CRIAR_PASTA)
            },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.addpasta),
                    contentDescription = "Criar pasta",
                    tint = Color.White
                )
            }
        )

        NavigationBarItem(
            selected = rotaAtual == Rotas.MENSAGENS,
            onClick = {
                navController.navigate(Rotas.MENSAGENS)
            },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.chat),
                    contentDescription = "Mensagem",
                    tint = Color.White
                )
            }
        )
    }
}
