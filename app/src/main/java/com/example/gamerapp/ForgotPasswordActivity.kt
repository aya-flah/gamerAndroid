package com.example.gamerapp

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

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var etEmail: EditText
    private lateinit var btnSubmit: Button
    private lateinit var btnSendSms: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        etEmail = findViewById(R.id.etEmailF)
        btnSubmit = findViewById(R.id.btnSubmitF)
        btnSendSms = findViewById(R.id.btnSendSms)

        etEmail.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (etEmail.text.toString().isNotEmpty() && !Patterns.EMAIL_ADDRESS.matcher(etEmail.text.toString()).matches()) {
                    etEmail.error = "Email invalide"
                } else {
                    etEmail.error = null
                }
            }
        })

        btnSubmit.setOnClickListener { v ->
            val email = etEmail.text.toString()
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                showSnackbar(v, "Veuillez entrer un email valide")
                return@setOnClickListener
            }
            // nav vers OTP avec code 1234
            val i = Intent(this, OTPActivity::class.java)
            i.putExtra("code", "1234")
            startActivity(i)
        }

        btnSendSms.setOnClickListener { v ->
            val email = etEmail.text.toString()
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                showSnackbar(v, "Veuillez entrer un email valide")
                return@setOnClickListener
            }
            val i = Intent(this, OTPActivity::class.java)
            i.putExtra("code", "6789")
            startActivity(i)
        }
    }

    private fun showSnackbar(v: View, text: String) {
        Snackbar.make(v, text, Snackbar.LENGTH_SHORT).show()
    }
}
