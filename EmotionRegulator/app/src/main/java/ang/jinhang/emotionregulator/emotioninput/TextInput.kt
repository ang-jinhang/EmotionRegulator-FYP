package ang.jinhang.emotionregulator.emotioninput

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import ang.jinhang.emotionregulator.AppContext.Companion.appContext
import ang.jinhang.emotionregulator.R
import ang.jinhang.emotionregulator.emotionclassifier.Emotion
import ang.jinhang.emotionregulator.emotionclassifier.EmotionClassifier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TextInput : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_textinput)

        val textInputBox = findViewById<EditText>(R.id.textInput_editText)
        val nextButton = findViewById<Button>(R.id.textInput_next)
        val progressBar = findViewById<ProgressBar>(R.id.textInput_progressBar)
        val waitReason = findViewById<TextView>(R.id.textInput_waitReason)

        nextButton.setOnClickListener(View.OnClickListener {
            textInputBox.isEnabled = false
            nextButton.isEnabled = false
            nextButton.text = appContext.getText(R.string.pleasewait)
            progressBar.visibility = View.VISIBLE
            waitReason.visibility = View.VISIBLE

            val text = textInputBox.text.toString()
            var emotion: Emotion
            val currentContext = this
            GlobalScope.launch(Dispatchers.Main) {
                emotion = EmotionClassifier.estimateEmotion(text)
                textInputBox.isEnabled = true
                nextButton.text = appContext.getText(R.string.button_next)
                nextButton.isEnabled = true
                progressBar.visibility = View.INVISIBLE
                waitReason.visibility = View.INVISIBLE

                val emotionConfirmationIntent =
                    Intent(currentContext, EmotionConfirmation::class.java)
                emotionConfirmationIntent.putExtra("valence", emotion.valence)
                emotionConfirmationIntent.putExtra("arousal", emotion.arousal)

                startActivity(emotionConfirmationIntent)
            }
        })
    }
}