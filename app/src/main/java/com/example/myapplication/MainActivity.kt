package com.example.myapplication


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val context = LocalContext.current

            LaunchedEffect(Unit) {
                carregarDadosBanco(AppDatabase.getDatabase(context))
            }
//
//            LaunchedEffect(Unit) {
//                val db = AppDatabase.getDatabase(context)
//
//                // Apaga os dados de todas as tabelas do Room
//                withContext(Dispatchers.IO) {
//                    db.clearAllTables()
//                }
//
//
//            }

            MyApplicationTheme {
                val navController = rememberNavController()

                AppNavigation(
                    navController = navController
                )
            }

        }
    }
}