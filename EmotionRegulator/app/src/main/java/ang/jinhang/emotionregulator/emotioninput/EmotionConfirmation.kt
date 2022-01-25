package ang.jinhang.emotionregulator.emotioninput

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import ang.jinhang.emotionregulator.R
import ang.jinhang.emotionregulator.emotionclassifier.Emotion
import ang.jinhang.emotionregulator.emotionclassifier.EmotionEnum
import ang.jinhang.emotionregulator.emotionregulation.NoRegulation
import ang.jinhang.emotionregulator.emotionregulation.RegulationConfirmation

class EmotionConfirmation : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emotionconfirmation)

        val guessTextView = findViewById<TextView>(R.id.emotionConfirmation_estimation)
        val valenceLevel = findViewById<SeekBar>(R.id.emotionConfirmation_valenceLevel)
        val arousalLevel = findViewById<SeekBar>(R.id.emotionConfirmation_arousalLevel)
        val yesButton = findViewById<Button>(R.id.emotionConfirmation_yesButton)
        val noButton = findViewById<Button>(R.id.emotionConfirmation_noButton)

        val emotion = Emotion(
            intent.extras?.get("valence") as Double,
            intent.extras?.get("arousal") as Double
        )

        when {
            emotion.getEmotion() == EmotionEnum.CALM -> {
                guessTextView.text = this.getText(R.string.emotionConfirmation_calm)
            }
            emotion.getEmotion() == EmotionEnum.HAPPY -> {
                guessTextView.text = this.getText(R.string.emotionConfirmation_happy)
            }
            emotion.getEmotion() == EmotionEnum.SAD -> {
                guessTextView.text = this.getText(R.string.emotionConfirmation_sad)
            }
            else -> {
                this.getText(R.string.emotionConfirmation_angry)
            }
        }

        valenceLevel.isEnabled = false
        valenceLevel.progress = ((emotion.valence - 1) * 10).toInt()
        arousalLevel.isEnabled = false
        arousalLevel.progress = ((emotion.arousal - 1) * 10).toInt()

        yesButton.setOnClickListener(View.OnClickListener {
            if (emotion.getEmotion() == EmotionEnum.ANGRY || emotion.getEmotion() == EmotionEnum.SAD) {
                val nextIntent = Intent(this, RegulationConfirmation::class.java)
                nextIntent.putExtra("valence", emotion.valence)
                nextIntent.putExtra("arousal", emotion.arousal)
                startActivity(nextIntent)
            } else {
                val nextIntent = Intent(this, NoRegulation::class.java)
                startActivity(nextIntent)
            }
        })

        noButton.setOnClickListener(View.OnClickListener {
            val emotionEditorIntent = Intent(this, EmotionEditor::class.java)
            emotionEditorIntent.putExtra("valence", emotion.valence)
            emotionEditorIntent.putExtra("arousal", emotion.arousal)

            startActivity(emotionEditorIntent)
        })
    }
}