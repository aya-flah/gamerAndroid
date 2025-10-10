package com.example.gamerapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import android.text.Editable
import android.text.TextWatcher
import android.util.Log

class SignUpActivity : AppCompatActivity() {
    private lateinit var etFullName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var etConfirm: EditText
    private lateinit var btnSubmit: Button
    private lateinit var tvPrivacy: TextView // Changed from Button to TextView

    @SuppressLint("Unspecified")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        Log.d("SignUpActivity", "onCreate called")

        etFullName = findViewById(R.id.etFullName)
        etEmail = findViewById(R.id.etEmailS)
        etPassword = findViewById(R.id.etPasswordS)
        etConfirm = findViewById(R.id.etConfirmS)
        btnSubmit = findViewById(R.id.btnSubmitS)
        tvPrivacy = findViewById(R.id.tvTerms) // Corrected to TextView

        val watcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                validateRealtime()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }

        etFullName.addTextChangedListener(watcher)
        etEmail.addTextChangedListener(watcher)
        etPassword.addTextChangedListener(watcher)
        etConfirm.addTextChangedListener(watcher)

        tvPrivacy.setOnClickListener {
            Log.d("SignUpActivity", "Privacy TextView clicked")
            showSnackbar(it, "Coming soon")
        }

        btnSubmit.setOnClickListener { v ->
            val fullName = etFullName.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val pwd = etPassword.text.toString()
            val conf = etConfirm.text.toString()

            if (fullName.isEmpty() || fullName.length < 3) {
                showSnackbar(v, "Nom complet invalide (min 3 caractères)")
                return@setOnClickListener
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                showSnackbar(v, "Email invalide")
                return@setOnClickListener
            }
            if (pwd.length < 6) {
                showSnackbar(v, "Mot de passe trop court")
                return@setOnClickListener
            }
            if (pwd != conf) {
                showSnackbar(v, "Les mots de passe ne correspondent pas")
                return@setOnClickListener
            }
            // tout OK -> retourne à Login
            Log.d("SignUpActivity", "Sign-up successful, finishing activity")
            finish()
        }

        validateRealtime()
    }

    private fun validateRealtime() {
        val fullName = etFullName.text.toString().trim()
        val email = etEmail.text.toString().trim()
        val pwd = etPassword.text.toString()
        val conf = etConfirm.text.toString()

        // Full Name check
        if (fullName.isNotEmpty() && fullName.length < 3) {
            etFullName.error = "Nom trop court"
        } else etFullName.error = null

        // Email check
        if (email.isNotEmpty() && !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.error = "Email invalide"
        } else etEmail.error = null

        // Password check
        if (pwd.isNotEmpty() && pwd.length < 6) {
            etPassword.error = "Au moins 6 caractères"
        } else etPassword.error = null

        // Confirm password check
        if (conf.isNotEmpty() && conf != pwd) {
            etConfirm.error = "Ne correspond pas"
        } else etConfirm.error = null

        btnSubmit.isEnabled =
            fullName.length >= 3 &&
                    Patterns.EMAIL_ADDRESS.matcher(email).matches() &&
                    pwd.length >= 6 &&
                    pwd == conf
    }

    private fun showSnackbar(v: View, text: String) {
        Snackbar.make(v, text, Snackbar.LENGTH_SHORT).show()
    }
}