package com.prd.notesapp.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.prd.notesapp.nav.AppNavigation

//Poner a los model y pasar los estados al host que distribuye en la app.
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun NotesApp(
) {
    val navController = rememberNavController()
    AppNavigation(
        navController = navController
    )

}