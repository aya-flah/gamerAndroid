package com.example.gamerapp.screens
import androidx.navigation.compose.rememberNavController
import com.example.gamerapp.ui.theme.GamerAppTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch

@Composable
fun ResetPasswordScreen(navController: NavController) {
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var newPasswordError by remember { mutableStateOf("") }
    var confirmPasswordError by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    fun validateFields(): Boolean {
        newPasswordError = if (newPassword.isBlank()) "Password is required"
        else if (newPassword.length < 6) "Password must be at least 6 characters"
        else ""

        confirmPasswordError = if (confirmPassword != newPassword) "Passwords don't match" else ""

        return newPasswordError.isEmpty() && confirmPasswordError.isEmpty()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Reset Password",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        OutlinedTextField(
            value = newPassword,
            onValueChange = {
                newPassword = it
                if (newPasswordError.isNotEmpty()) validateFields()
            },
            label = { Text("New Password") },
            modifier = Modifier.fillMaxWidth(),
            isError = newPasswordError.isNotEmpty(),
            supportingText = {
                if (newPasswordError.isNotEmpty()) Text(newPasswordError)
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

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                if (validateFields()) {
                    navController.navigate("login") {
                        popUpTo(0) // Clear entire back stack
                    }
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
            )){
            Text("Reset Password")
        }
    }

    SnackbarHost(hostState = snackbarHostState)
}

@Preview(showBackground = true)
@Composable
fun ResetPasswordScreenPreview() {
    GamerAppTheme {
        ResetPasswordScreen(navController = rememberNavController())
    }
}