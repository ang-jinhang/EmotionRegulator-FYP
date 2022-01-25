package ang.jinhang.emotionregulator.emotionregulation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import ang.jinhang.emotionregulator.R
import ang.jinhang.emotionregulator.emotionclassifier.Emotion

class RegulationConfirmation : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_regulationconfirmation)

        val yesButton = findViewById<Button>(R.id.regulationConfirmation_yesButton)
        val noButton = findViewById<Button>(R.id.regulationConfirmation_noButton)

        yesButton.setOnClickListener(View.OnClickListener {
            val strategySelector = StrategySelector()
            val nextIntent = strategySelector.chooseStrategy(
                this,
                Emotion(
                    intent.extras?.get("valence") as Double,
                    intent.extras?.get("arousal") as Double
                )
            )
            startActivity(nextIntent)
        })

        noButton.setOnClickListener(View.OnClickListener {
            val nextIntent = Intent(this, RejectRegulation::class.java)
            startActivity(nextIntent)
        })
    }
}