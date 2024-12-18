package com.conestoga.week9.kavya_shruti_quiz_app_part1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val email = findViewById<EditText>(R.id.email)
        val password = findViewById<EditText>(R.id.password)
        val error = findViewById<TextView>(R.id.error)
        val loginBtn = findViewById<Button>(R.id.loginBtn)
        val signupLnk = findViewById<TextView>(R.id.signupLnk)

        loginBtn.setOnClickListener {
            val email = email.text.toString()
            val password = password.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            startActivity(Intent(this, MainActivity::class.java))
                            Toast.makeText(this, "Login Successfull.", Toast.LENGTH_LONG).show()
                            finish()
                        } else {
                            error.text = ""
                            Toast.makeText(this, "Login Unsuccessful.", Toast.LENGTH_LONG).show()
                        }
                    }
            } else {
                error.text="Email and Password should not be empty"
            }
        }
        signupLnk.setOnClickListener {
            startActivity(Intent(this, SignIn::class.java))
        }
    }
}