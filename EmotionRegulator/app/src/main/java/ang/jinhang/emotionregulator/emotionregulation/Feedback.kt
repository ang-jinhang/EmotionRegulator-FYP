package ang.jinhang.emotionregulator.emotionregulation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import ang.jinhang.emotionregulator.R

class Feedback : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback)

        val yesButton = findViewById<Button>(R.id.feedback_yesButton)
        val noButton = findViewById<Button>(R.id.feedback_noButton)

        yesButton.setOnClickListener(View.OnClickListener {
            val nextIntent = Intent(this, FeedbackContinue::class.java)
            startActivity(nextIntent)
        })

        noButton.setOnClickListener(View.OnClickListener {
            val nextIntent = Intent(this, FeedbackEnd::class.java)
            startActivity(nextIntent)
        })
    }
}