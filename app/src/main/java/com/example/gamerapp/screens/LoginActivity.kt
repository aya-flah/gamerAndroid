package com.example.gamerapp.screens

import android.util.Patterns
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.gamerapp.ui.theme.GamerAppTheme
import kotlinx.coroutines.launch
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.foundation.clickable
import com.example.gamerapp.R

@Composable
fun LoginScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }
    var rememberMe by remember { mutableStateOf(false) } // Add this line
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    fun validateFields(): Boolean {
        emailError = if (email.isBlank()) "Email is required"
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) "Invalid email format"
        else ""

        passwordError = if (password.isBlank()) "Password is required"
        else if (password.length < 6) "Password must be at least 6 characters"
        else ""

        return emailError.isEmpty() && passwordError.isEmpty()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_gamer), // image li hchty beha
            contentDescription = "App Logo",
            modifier = Modifier
                .size(120.dp) // Adjust size as needed
                .padding(bottom = 32.dp)
        )

        // Email Field
        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                if (emailError.isNotEmpty()) validateFields()
            },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            isError = emailError.isNotEmpty(),
            supportingText = {
                if (emailError.isNotEmpty()) {
                    Text(text = emailError)
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Password Field
        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                if (passwordError.isNotEmpty()) validateFields()
            },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            isError = passwordError.isNotEmpty(),
            supportingText = {
                if (passwordError.isNotEmpty()) {
                    Text(text = passwordError)
                }
            },
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Login Button
        Button(
            onClick = {
                if (validateFields()) {
                    navController.navigate("home")
                } else {
                    scope.launch {
                        snackbarHostState.showSnackbar("Please check your inputs")
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFE91E63), // Pink background
                contentColor = Color.White          // White text
            )
        ) {
            Text("Login")
        }

        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ){
            Checkbox(
                checked = rememberMe,
                onCheckedChange = { rememberMe = it },
                colors = CheckboxDefaults.colors(
                    checkedColor = Color(0xFFE91E63)
                )
            )
            Text(
                text = "Remember me",
                modifier = Modifier.clickable { rememberMe = !rememberMe }
            )
        // Forgot Password
        TextButton(
            onClick = { navController.navigate("forgot_password") },
            colors = ButtonDefaults.textButtonColors(
                contentColor = Color(0xFFE91E63) // Pink color
            )
        ) {
            Text("Forgot Password?")
        }
    }
        Spacer(modifier = Modifier.height(24.dp))

        // Social Login Buttons - IN SAME ROW
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Facebook Button
            OutlinedButton(
                onClick = {
                    scope.launch {
                        snackbarHostState.showSnackbar("Coming soon")
                    }
                },
                modifier = Modifier.weight(1f), // Equal width
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color(0xFF3F51B5), // Pink background
                    contentColor = Color.White          // White text and icon
                )
            ) {
               // Icon(Icons.Default.Facebook, contentDescription = "Facebook")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Facebook")
            }

            // Google Button
            OutlinedButton(
                onClick = {
                    scope.launch {
                        snackbarHostState.showSnackbar("Coming soon")
                    }
                },
                modifier = Modifier.weight(1f), // Equal width
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color(0xFFE8A6BC), // Pink background
                    contentColor = Color.White          // White text and icon
                )
            ) {
                Icon(Icons.Default.AccountCircle, contentDescription = "Google")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Google")
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Register Now
        TextButton(
            onClick = { navController.navigate("signup") },
            colors = ButtonDefaults.textButtonColors(
                contentColor = Color(0xFFE91E63) // Pink color
            )
        ) {
            Text("Don't have an account? Register Now")
        }
    }

    SnackbarHost(hostState = snackbarHostState)
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    GamerAppTheme {
        LoginScreen(navController = rememberNavController())
    }
}