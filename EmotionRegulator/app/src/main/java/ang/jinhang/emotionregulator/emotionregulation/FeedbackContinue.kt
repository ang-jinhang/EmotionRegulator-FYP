package ang.jinhang.emotionregulator.emotionregulation

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ang.jinhang.emotionregulator.R
import ang.jinhang.emotionregulator.service.NotificationSender

class FeedbackContinue : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedbackcontinue)

        val leaveButton = findViewById<Button>(R.id.feedbackContinue_leaveButton)

        val notificationSender = NotificationSender()

        leaveButton.setOnClickListener(View.OnClickListener {
            notificationSender.scheduleNotification(this, intent)
            finishAffinity()
        })
    }
}