package com.example.gamerapp

import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import android.text.Editable
import android.text.TextWatcher

class SignUpActivity : AppCompatActivity() {
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var etConfirm: EditText
    private lateinit var btnSubmit: Button
    private lateinit var btnPrivacy: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        etEmail = findViewById(R.id.etEmailS)
        etPassword = findViewById(R.id.etPasswordS)
        etConfirm = findViewById(R.id.etConfirmS)
        btnSubmit = findViewById(R.id.btnSubmitS)
        btnPrivacy = findViewById(R.id.btnPrivacy)

        val watcher = object: TextWatcher {
            override fun afterTextChanged(s: Editable?) { validateRealtime() }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }
        etEmail.addTextChangedListener(watcher)
        etPassword.addTextChangedListener(watcher)
        etConfirm.addTextChangedListener(watcher)

        btnPrivacy.setOnClickListener {
            showSnackbar(it, "Coming soon")
        }

        btnSubmit.setOnClickListener { v ->
            val email = etEmail.text.toString()
            val pwd = etPassword.text.toString()
            val conf = etConfirm.text.toString()
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
            finish()
        }

        validateRealtime()
    }

    private fun validateRealtime() {
        val email = etEmail.text.toString()
        val pwd = etPassword.text.toString()
        val conf = etConfirm.text.toString()
        if (email.isNotEmpty() && !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.error = "Email invalide"
        } else etEmail.error = null
        if (pwd.isNotEmpty() && pwd.length < 6) etPassword.error = "Au moins 6 caractères"
        else etPassword.error = null
        if (conf.isNotEmpty() && conf != pwd) etConfirm.error = "Ne correspond pas"
        else etConfirm.error = null

        btnSubmit.isEnabled = Patterns.EMAIL_ADDRESS.matcher(email).matches() && pwd.length >=6 && pwd==conf
    }

    private fun showSnackbar(v: View, text: String) {
        Snackbar.make(v, text, Snackbar.LENGTH_SHORT).show()
    }
}
