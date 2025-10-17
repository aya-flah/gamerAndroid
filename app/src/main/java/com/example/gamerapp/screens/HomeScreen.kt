package com.example.gamerapp.screens
import androidx.navigation.compose.rememberNavController
import com.example.gamerapp.ui.theme.GamerAppTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController

@Composable
fun HomeScreen(navController: NavController) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Welcome to Home Screen!")
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    GamerAppTheme {
        HomeScreen(navController = rememberNavController())
    }
}