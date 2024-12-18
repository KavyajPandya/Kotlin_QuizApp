package com.conestoga.week9.kavya_shruti_quiz_app_part1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class ThankYou : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thank_you)

        val scoreTView = findViewById<TextView>(R.id.score)
        val score = intent.getIntExtra("score", 0)
        scoreTView.text = "$score/10"
    }
}