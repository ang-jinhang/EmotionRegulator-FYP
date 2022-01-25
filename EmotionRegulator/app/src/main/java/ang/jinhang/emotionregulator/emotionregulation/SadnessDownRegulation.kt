package ang.jinhang.emotionregulator.emotionregulation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import ang.jinhang.emotionregulator.R

class SadnessDownRegulation : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sadnessdownregulation)

        val question1 = intent.extras?.get("q1") as Int?
        val question2 = intent.extras?.get("q2") as Int?

        val questionView = findViewById<TextView>(R.id.sadnessDownRegulation_question)
        val nextButton = findViewById<Button>(R.id.sadnessDownRegulation_next)

        val questionMap = mapOf(
            1 to R.string.sadnessDownRegulation_actionQuestion,
            2 to R.string.sadnessDownRegulation_eventQuestion,
            3 to R.string.sadnessDownRegulation_foodQuestion,
            4 to R.string.sadnessDownRegulation_placeQuestion,
            5 to R.string.sadnessDownRegulation_successQuestion,
            6 to R.string.sadnessDownRegulation_surpriseQuestion
        )

        val questionList = mutableListOf(1, 2, 3, 4, 5, 6)

        if (question1 != null) {
            questionList.remove(element = question1)
        }
        if (question2 != null) {
            questionList.remove(element = question2)
            nextButton.text = this.getText(R.string.button_finish)
        }

        questionList.shuffle()
        val chosenQuestion = questionList[0]
        questionView.text = this.getText(questionMap[chosenQuestion]!!)

        nextButton.setOnClickListener(View.OnClickListener {
            if (question1 != null && question2 != null) {
                val newIntent = Intent(this, Feedback::class.java)
                startActivity(newIntent)
            } else {
                val newIntent = Intent(this, SadnessDownRegulation::class.java)
                if (question1 == null) {
                    newIntent.putExtra("q1", chosenQuestion)
                } else {
                    newIntent.putExtra("q1", question1)
                    newIntent.putExtra("q2", chosenQuestion)
                }
                startActivity(newIntent)
            }
        })
    }
}