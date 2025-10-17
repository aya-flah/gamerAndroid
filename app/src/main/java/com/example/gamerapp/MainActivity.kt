package com.example.gamerapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.gamerapp.screens.ForgotPasswordScreen
import com.example.gamerapp.screens.HomeScreen
import com.example.gamerapp.screens.LoginScreen
import com.example.gamerapp.screens.OTPValidationScreen
import com.example.gamerapp.screens.ResetPasswordScreen
import com.example.gamerapp.screens.SignUpScreen
import com.example.gamerapp.screens.SplashScreen
import com.example.gamerapp.ui.theme.GamerAppTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GamerAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppNavigation(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "splash",
        modifier = modifier.fillMaxSize()
    ) {
        composable("splash") {
            SplashScreen(navController = navController)
        }
        composable("login") {
            LoginScreen(navController = navController)
        }
        composable("signup") {
            SignUpScreen(navController = navController)
        }
        composable("forgot_password") {
            ForgotPasswordScreen(navController = navController)
        }
        composable(
            route = "otp_validation/{expectedCode}",
            arguments = listOf(navArgument("expectedCode") { type = NavType.StringType })
        ) { backStackEntry ->
            OTPValidationScreen(
                navController = navController,
                expectedCode = backStackEntry.arguments?.getString("expectedCode") ?: ""
            )
        }
        composable("reset_password") {
            ResetPasswordScreen(navController = navController)
        }
        composable("home") {
            HomeScreen(navController = navController)
        }
    }
}