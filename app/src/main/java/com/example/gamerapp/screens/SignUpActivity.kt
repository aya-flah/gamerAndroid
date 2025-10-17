package com.example.gamerapp.screens

import android.util.Patterns
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import com.example.gamerapp.R
import com.example.gamerapp.ui.theme.GamerAppTheme
import kotlinx.coroutines.launch

@Composable
fun SignUpScreen(navController: NavController) {
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var fullNameError by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }
    var confirmPasswordError by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    fun validateFields(): Boolean {
        fullNameError = if (fullName.isBlank()) "Full name is required"
        else if (fullName.length < 6) "Full name must be at least 6 characters"
        else ""

        emailError = if (email.isBlank()) "Email is required"
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) "Invalid email format"
        else ""

        passwordError = if (password.isBlank()) "Password is required"
        else if (password.length < 6) "Password must be at least 6 characters"
        else ""

        confirmPasswordError = if (confirmPassword != password) "Passwords don't match" else ""

        return fullNameError.isEmpty() && emailError.isEmpty() && passwordError.isEmpty() && confirmPasswordError.isEmpty()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_gamer),
            contentDescription = "App Logo",
            modifier = Modifier
                .size(120.dp)
                .padding(bottom = 32.dp)
        )
        OutlinedTextField(
            value = fullName,
            onValueChange = {
                fullName = it
                if (fullNameError.isNotEmpty()) validateFields()
            },
            label = { Text("Full Name") },
            modifier = Modifier.fillMaxWidth(),
            isError = fullNameError.isNotEmpty(),
            supportingText = {
                if (fullNameError.isNotEmpty()) Text(fullNameError)
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

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
                if (emailError.isNotEmpty()) Text(emailError)
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

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
                if (passwordError.isNotEmpty()) Text(passwordError)
            },
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = {
                confirmPassword = it
                if (confirmPasswordError.isNotEmpty()) validateFields()
            },
            label = { Text("Confirm Password") },
            modifier = Modifier.fillMaxWidth(),
            isError = confirmPasswordError.isNotEmpty(),
            supportingText = {
                if (confirmPasswordError.isNotEmpty()) Text(confirmPasswordError)
            },
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(24.dp))

        TextButton(
            onClick = {
                scope.launch {
                    snackbarHostState.showSnackbar("Privacy Policy - Coming soon")
                }
            },
            colors = ButtonDefaults.textButtonColors(
                contentColor = Color(0xFFE91E63)
            )
        ) {
            Text("Privacy Policy")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (validateFields()) {
                    navController.navigate("login") {
                        popUpTo("signup") { inclusive = true }
                    }
                } else {
                    scope.launch {
                        snackbarHostState.showSnackbar("Please check your inputs")
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFE91E63),
                contentColor = Color.White
            )
        ) {
            Text("Submit")
        }
    }

    SnackbarHost(hostState = snackbarHostState)
}

@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    GamerAppTheme {
        SignUpScreen(navController = rememberNavController())
    }
}