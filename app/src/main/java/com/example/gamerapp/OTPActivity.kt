package com.example.gamerapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.EditorInfo
import androidx.appcompat.widget.Toolbar

class OTPActivity : AppCompatActivity() {

    private lateinit var et1: EditText
    private lateinit var et2: EditText
    private lateinit var et3: EditText
    private lateinit var et4: EditText
    private lateinit var btnVerify: Button
    private lateinit var btnResend: Button
    private lateinit var toolbar: Toolbar
    private var expectedCode: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp)

        et1 = findViewById(R.id.otp1)
        et2 = findViewById(R.id.otp2)
        et3 = findViewById(R.id.otp3)
        et4 = findViewById(R.id.otp4)
        btnVerify = findViewById(R.id.btnVerify)
        btnResend = findViewById(R.id.btnResend)
        toolbar = findViewById(R.id.topAppBar)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { onBackPressed() }

        expectedCode = intent.getStringExtra("code") ?: ""

        setupOtpEditTexts()

        btnResend.setOnClickListener {
            showSnackbar(it, "Coming soon")
        }

        btnVerify.setOnClickListener { v ->
            val code = et1.text.toString() + et2.text.toString() + et3.text.toString() + et4.text.toString()
            if (code.length < 4) {
                showSnackbar(v, "Please enter the 4-digit code")
                return@setOnClickListener
            }
            if (code == expectedCode) {
                // success -> ResetPassword
                val i = Intent(this, ResetPasswordActivity::class.java)
                startActivity(i)
            } else {
                showSnackbar(v, "Code incorrect")
            }
        }
    }

    private fun setupOtpEditTexts() {
        val list = listOf(et1, et2, et3, et4)
        for (i in 0 until list.size) {
            list[i].addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    if (s?.length == 1) {
                        if (i < list.size - 1) list[i + 1].requestFocus()
                        else {
                            list[i].clearFocus()
                            list[i].onEditorAction(EditorInfo.IME_ACTION_DONE)
                        }
                    } else if (s?.length == 0) {
                        if (i > 0) list[i - 1].requestFocus()
                    }
                }
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })
        }
    }

    private fun showSnackbar(v: View, message: String) {
        Snackbar.make(v, message, Snackbar.LENGTH_SHORT).show()
    }
}