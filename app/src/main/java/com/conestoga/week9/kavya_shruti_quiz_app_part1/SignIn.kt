package com.conestoga.week9.kavya_shruti_quiz_app_part1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class SignIn : AppCompatActivity() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        val email = findViewById<EditText>(R.id.email)
        val password = findViewById<EditText>(R.id.password)
        val error = findViewById<TextView>(R.id.error)
        val signInBtn = findViewById<Button>(R.id.signInBtn)
        val loginLnk = findViewById<TextView>(R.id.loginLnk)

        signInBtn.setOnClickListener {
            val email = email.text.toString()
            val password = password.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                if (password.length >= 8) {
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                startActivity(Intent(this, Login::class.java))
                                Toast.makeText(this, "Signup Successful.", Toast.LENGTH_LONG).show()
                                finish()
                            } else {
                                error.text = ""
                                Toast.makeText(this, "Signup Unsuccessful.", Toast.LENGTH_LONG).show()
                            }
                        }
                } else {
                    error.text = "Password must be at least 8 characters long."
                }
            } else {
                error.text = "Email and Password should not be empty"
            }
        }
        loginLnk.setOnClickListener {
            startActivity(Intent(this, Login::class.java))
        }
    }
}