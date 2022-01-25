package ang.jinhang.emotionregulator.emotioninput

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import ang.jinhang.emotionregulator.R
import ang.jinhang.emotionregulator.emotionclassifier.Emotion
import ang.jinhang.emotionregulator.emotionclassifier.EmotionEnum
import ang.jinhang.emotionregulator.emotionregulation.NoRegulation
import ang.jinhang.emotionregulator.emotionregulation.RegulationConfirmation
import ang.jinhang.emotionregulator.emotionregulation.StrategySelector

class EmotionEditor : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emotioneditor)

        val valenceLevel = findViewById<SeekBar>(R.id.emotionEditor_valenceLevel)
        val arousalLevel = findViewById<SeekBar>(R.id.emotionEditor_arousalLevel)
        val emotionDisplay = findViewById<TextView>(R.id.emotionEditor_emotion)
        val doneButton = findViewById<Button>(R.id.emotionEditor_doneButton)

        val emotion = Emotion(
            intent.extras?.get("valence") as Double,
            intent.extras?.get("arousal") as Double
        )

        fun updateEmotion() {
            if (valenceLevel.progress >= 40 && arousalLevel.progress < 40) {
                emotionDisplay.text = this.getText(R.string.emotionEditor_calm)
            } else if (valenceLevel.progress >= 40 && arousalLevel.progress >= 40) {
                emotionDisplay.text = this.getText(R.string.emotionEditor_happy)
            } else if (valenceLevel.progress < 40 && arousalLevel.progress < 40) {
                emotionDisplay.text = this.getText(R.string.emotionEditor_sad)
            } else {
                emotionDisplay.text = this.getText(R.string.emotionEditor_angry)
            }
        }

        valenceLevel.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                updateEmotion()
                emotion.valence = (valenceLevel.progress.toDouble() / 10) + 1
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {}

            override fun onStopTrackingTouch(p0: SeekBar?) {}
        })

        arousalLevel.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                updateEmotion()
                emotion.arousal = (arousalLevel.progress.toDouble() / 10) + 1
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {}

            override fun onStopTrackingTouch(p0: SeekBar?) {}
        })

        valenceLevel.progress = ((emotion.valence - 1) * 10).toInt()
        arousalLevel.progress = ((emotion.arousal - 1) * 10).toInt()

        doneButton.setOnClickListener(View.OnClickListener {
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
    }
}