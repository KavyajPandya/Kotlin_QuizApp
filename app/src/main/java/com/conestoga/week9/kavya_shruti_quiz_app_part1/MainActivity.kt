package com.conestoga.week9.kavya_shruti_quiz_app_part1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {

    private var questionAdapter: QuestionAdapter? = null
    private var score = 0
    private val selectedAnswers = ArrayList<Int?>()
    private var totalQuestions = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val scoreTView = findViewById<TextView>(R.id.score)
        val questionRview = findViewById<RecyclerView>(R.id.questionRview)
        questionRview.layoutManager = LinearLayoutManager(this)
        val query = FirebaseDatabase.getInstance().reference.child("questions")
        val options = FirebaseRecyclerOptions.Builder<Question>()
            .setQuery(query, Question::class.java)
            .build()

        questionAdapter = QuestionAdapter(options, selectedAnswers) { correctAnswer ->
            if (correctAnswer) {
                score++
                scoreTView.text = "$score/10"
            }
        }
        questionRview.adapter = questionAdapter
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                totalQuestions = snapshot.childrenCount.toInt()
            }
            override fun onCancelled(error: DatabaseError) {
                Log.i("error", "Database Error")
            }
        })

        val finishButton = findViewById<Button>(R.id.finishBtn)
        finishButton.setOnClickListener {
            var allQuestionsAnswered = true
            for (answer in selectedAnswers) {
                if (answer == null) {
                    allQuestionsAnswered = false
                    break
                }
            }

            if (allQuestionsAnswered && selectedAnswers.size == totalQuestions) {
                val intent = Intent(this, ThankYou::class.java)
                intent.putExtra("score", score)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Please answer all questions before finishing.", Toast.LENGTH_SHORT).show()
            }
        }
    }
    override fun onStart() {
        super.onStart()
        questionAdapter?.startListening()
    }

    override fun onStop() {
        super.onStop()
        questionAdapter?.stopListening()
    }
}