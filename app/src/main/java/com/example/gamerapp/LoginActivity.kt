package com.example.gamerapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import android.text.Editable
import android.text.TextWatcher

class LoginActivity : AppCompatActivity() {

    private lateinit var emailEt: EditText
    private lateinit var passwordEt: EditText
    private lateinit var loginBtn: Button
    private lateinit var fbBtn: Button
    private lateinit var googleBtn: Button
    private lateinit var forgotBtn: Button
    private lateinit var registerBtn: Button

    @SuppressLint()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        emailEt = findViewById(R.id.etEmail)
        passwordEt = findViewById(R.id.etPassword)
        loginBtn = findViewById(R.id.btnLogin)
        fbBtn = findViewById(R.id.btnFacebook)
        googleBtn = findViewById(R.id.btnGoogle)
        forgotBtn = findViewById(R.id.tvForgotPassword)
        registerBtn = findViewById(R.id.tvRegister)

        val watcher = object: TextWatcher {
            override fun afterTextChanged(s: Editable?) { validateFieldsRealtime() }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }
        emailEt.addTextChangedListener(watcher)
        passwordEt.addTextChangedListener(watcher)

        fbBtn.setOnClickListener {
            showSnackbar(it, "Coming soon")
        }
        googleBtn.setOnClickListener {
            showSnackbar(it, "Coming soon")
        }

        forgotBtn.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }

        registerBtn.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        loginBtn.setOnClickListener { v ->
            if (isValidEmail(emailEt.text.toString()) && isValidPassword(passwordEt.text.toString())) {
                // ici on simule login ok -> Home
                startActivity(Intent(this, HomeActivity::class.java))
            } else {
                showSnackbar(v, "Email ou mot de passe invalide")
            }
        }

        validateFieldsRealtime()
    }

    private fun validateFieldsRealtime() {
        val email = emailEt.text.toString()
        val pwd = passwordEt.text.toString()
        if (email.isNotEmpty() && !isValidEmail(email)) {
            emailEt.error = "Email invalide"
        } else {
            emailEt.error = null
        }
        if (pwd.isNotEmpty() && !isValidPassword(pwd)) {
            passwordEt.error = "Au moins 6 caractères"
        } else {
            passwordEt.error = null
        }
        loginBtn.isEnabled = isValidEmail(email) && isValidPassword(pwd)
    }

    private fun isValidEmail(email: String) = Patterns.EMAIL_ADDRESS.matcher(email).matches()
    private fun isValidPassword(pwd: String) = pwd.length >= 6

    private fun showSnackbar(v: View, message: String) {
        Snackbar.make(v, message, Snackbar.LENGTH_SHORT).show()
    }
}
