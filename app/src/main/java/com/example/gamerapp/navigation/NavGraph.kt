package com.example.gamerapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gamerapp.screens.ForgotPasswordScreen
import com.example.gamerapp.screens.HomeScreen
import com.example.gamerapp.screens.LoginScreen
import com.example.gamerapp.screens.OTPValidationScreen
import com.example.gamerapp.screens.ResetPasswordScreen
import com.example.gamerapp.screens.SignUpScreen
import com.example.gamerapp.screens.SplashScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(navController = navController)
        }
        composable(Screen.Login.route) {
            LoginScreen(navController = navController)
        }
        composable(Screen.SignUp.route) {
            SignUpScreen(navController = navController)
        }
        composable(Screen.ForgotPassword.route) {
            ForgotPasswordScreen(navController = navController)
        }
        composable("${Screen.OTPValidation.route}/{code}") { backStackEntry ->
            val code = backStackEntry.arguments?.getString("code") ?: ""
            OTPValidationScreen(
                navController = navController,
                expectedCode = code
            )
        }
        composable(Screen.ResetPassword.route) {
            ResetPasswordScreen(navController = navController)
        }
        composable(Screen.Home.route) {
            HomeScreen(navController = navController)
        }
    }
}

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object SignUp : Screen("signup")
    object ForgotPassword : Screen("forgot_password")
    object OTPValidation : Screen("otp_validation")
    object ResetPassword : Screen("reset_password")
    object Home : Screen("home")
}