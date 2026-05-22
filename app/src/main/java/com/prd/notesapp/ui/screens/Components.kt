package com.prd.notesapp.ui.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Article
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.prd.notesapp.nav.Screen
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier

@Composable
fun BottomNavBar(
    navController: NavController
) {
    val list = listOf(
        Screen.Notes,
        Screen.Todo
    )
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStack?.destination?.route

    NavigationBar(
        modifier = Modifier.fillMaxWidth()
    ) {
        list.forEach { screen ->
            NavigationBarItem(
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true // Guardar el estado de la pantalla al hacer pop up
                        }
                        launchSingleTop = true // Evitar múltiples instancias de la misma pantalla
                        restoreState = true // Restaurar el estado guardado al volver a la pantalla
                    }
                },
                label = { Text(screen.title) },
                icon = {
                    when (screen) {
                        is Screen.Notes -> Icon(
                            Icons.AutoMirrored.Default.Article,
                            contentDescription = "Notes"
                        )

                        is Screen.Todo -> Icon(
                            Icons.AutoMirrored.Filled.List,
                            contentDescription = "Todo"
                        )

                        else -> Text("")
                    }
                }
            )
        }
    }

}