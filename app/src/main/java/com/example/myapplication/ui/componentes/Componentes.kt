package com.example.myapplication.ui.componentes

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.R


@Composable
fun BotaoVoltar( modifier: Modifier = Modifier,
                 navController: NavController,
                 onClick: (() -> Unit)? = null) {

    Box(
        modifier = modifier
            .requiredSize(45.dp)
            .padding(5.dp)
            .clickable {
                if (onClick != null) onClick()
                else navController.popBackStack()
            }
        .background(Color(0xFF1e1e1e))
    ) {
        Icon(
            painter = painterResource(R.drawable.voltar),
            contentDescription = null,
            tint = Color.White
        )

    }


}