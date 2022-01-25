package ang.jinhang.emotionregulator.emotionregulation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import ang.jinhang.emotionregulator.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AngerDownRegulation : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_angerdownregulation)

        val startButton = findViewById<Button>(R.id.angerDownRegulation_start)
        val stepTextView = findViewById<TextView>(R.id.angerDownRegulation_action)
        val progressBar = findViewById<ProgressBar>(R.id.angerDownRegulation_progress)
        val counterTextView = findViewById<TextView>(R.id.angerDownRegulation_counter)

        val context = this
        var buttonIsStart = true

        startButton.setOnClickListener(View.OnClickListener {
            if (buttonIsStart) {
                GlobalScope.launch(Dispatchers.Main) {
                    startButton.isEnabled = false

                    for (i in 1..5) {
                        if (i != 5) {
                            counterTextView.text =
                                context.getText(R.string.angerDownRegulation_remainingCounter)
                        } else {
                            counterTextView.text =
                                context.getText(R.string.angerDownRegulation_remainingCounterLast)
                        }

                        stepTextView.text = context.getText(R.string.angerDownRegulation_inhale)
                        progressBar.progress = 5
                        for (j in 1..14) {
                            delay(500)
                            progressBar.progress += 10
                        }
                        delay(500)
                        progressBar.progress = progressBar.max
                        stepTextView.text = context.getText(R.string.angerDownRegulation_exhale)
                        for (j in 1..12) {
                            progressBar.progress -= 20
                            delay(500)
                        }
                    }

                    stepTextView.text = context.getText(R.string.angerDownRegulation_finish)
                    counterTextView.text = ""
                    startButton.isEnabled = true
                    startButton.text = context.getText(R.string.button_next)
                    buttonIsStart = false
                }
            } else {
                val nextIntent = Intent(this, Feedback::class.java)
                startActivity(nextIntent)
            }
        })
    }
}