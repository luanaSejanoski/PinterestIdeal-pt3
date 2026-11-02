package com.example.myapplication.ui.componentes

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.myapplication.R
import com.example.myapplication.Rotas

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
            selected = rotaAtual == Rotas.VER_AMIGOS,
            onClick = {
                navController.navigate(Rotas.VER_AMIGOS)
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
