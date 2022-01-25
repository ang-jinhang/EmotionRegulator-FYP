package ang.jinhang.emotionregulator.emotionregulation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import ang.jinhang.emotionregulator.R

class FeedbackEnd : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedbackend)

        val endButton = findViewById<Button>(R.id.feedbackEnd_leaveButton)

        endButton.setOnClickListener(View.OnClickListener {
            finishAffinity()
        })
    }
}