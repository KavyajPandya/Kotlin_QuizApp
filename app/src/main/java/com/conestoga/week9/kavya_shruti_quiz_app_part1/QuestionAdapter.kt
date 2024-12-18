package com.conestoga.week9.kavya_shruti_quiz_app_part1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

class QuestionAdapter(
    options: FirebaseRecyclerOptions<Question>,
    private val selectedAnswers: ArrayList<Int?>,
    private val answerClickListener: (Boolean) -> Unit
) : FirebaseRecyclerAdapter<Question, QuestionAdapter.QuestionViewHolder>(options) {

    class QuestionViewHolder(inflater: LayoutInflater, parent: ViewGroup):
        RecyclerView.ViewHolder(inflater.inflate(R.layout.question_layout, parent, false)) {
        val questionTextView: TextView = itemView.findViewById(R.id.question)
        val optionButtons: List<Button> = listOf(
            itemView.findViewById(R.id.btn1),
            itemView.findViewById(R.id.btn2),
            itemView.findViewById(R.id.btn3)
        )
    }
    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): QuestionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return QuestionViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: QuestionViewHolder,position: Int,model: Question) {
        holder.questionTextView.text = model.question
        val options = model.options

        while (selectedAnswers.size <= position) {
            selectedAnswers.add(null)
        }

        var answered = selectedAnswers[position] != null
        holder.optionButtons.forEach { button ->
            button.text = options[holder.optionButtons.indexOf(button)]
            button.setBackgroundColor(
                ContextCompat.getColor(holder.itemView.context, android.R.color.darker_gray)
            )
            button.setOnClickListener {
                if (!answered) {
                    answered = true
                    selectedAnswers[position] = holder.optionButtons.indexOf(button)
                    val isCorrect = button.text == model.correctAnswer
                    button.setBackgroundColor(
                        ContextCompat.getColor(
                            holder.itemView.context,
                            if (isCorrect) android.R.color.holo_green_light else android.R.color.holo_red_light
                        )
                    )

                    holder.optionButtons.forEach { btn ->
                        if (btn.text == model.correctAnswer) {
                            btn.setBackgroundColor(
                                ContextCompat.getColor(holder.itemView.context, android.R.color.holo_green_light)
                            )
                        } else if (btn != button) {
                            btn.setBackgroundColor(
                                ContextCompat.getColor(holder.itemView.context, android.R.color.holo_red_light)
                            )
                        }
                    }
                    answerClickListener(isCorrect)
                }
            }
        }

        val selectedIndex = selectedAnswers[position]
        if (selectedIndex != null) {
            holder.optionButtons.forEach { btn ->
                if (btn.text == model.correctAnswer) {
                    btn.setBackgroundColor(
                        ContextCompat.getColor(holder.itemView.context, android.R.color.holo_green_light)
                    )
                } else {
                    btn.setBackgroundColor(
                        ContextCompat.getColor(holder.itemView.context, android.R.color.holo_red_light)
                    )
                }
            }
        }
    }
}