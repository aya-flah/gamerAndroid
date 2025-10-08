package com.example.gamerapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

class ResetPasswordActivity : AppCompatActivity() {
    private lateinit var etNew: EditText
    private lateinit var etConfirm: EditText
    private lateinit var btnReset: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)

        etNew = findViewById(R.id.etNewPassword)
        etConfirm = findViewById(R.id.etConfirmPassword)
        btnReset = findViewById(R.id.btnResetPass)

        btnReset.setOnClickListener { v ->
            val newP = etNew.text.toString()
            val conf = etConfirm.text.toString()
            if (newP.length < 6) {
                showSnackbar(v, "Mot de passe trop court")
                return@setOnClickListener
            }
            if (newP != conf) {
                showSnackbar(v, "Les mots de passe ne correspondent pas")
                return@setOnClickListener
            }
            // success -> go to Login and clear backstack
            val i = Intent(this, LoginActivity::class.java)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(i)
        }
    }

    private fun showSnackbar(v: View, text: String) {
        Snackbar.make(v, text, Snackbar.LENGTH_SHORT).show()
    }
}
