package ang.jinhang.emotionregulator.emotionregulation

import android.content.Context
import android.content.Intent
import ang.jinhang.emotionregulator.emotionclassifier.Emotion
import ang.jinhang.emotionregulator.emotionclassifier.EmotionEnum

class StrategySelector {
    fun chooseStrategy(context: Context, emotion: Emotion): Intent {
        return when {
            emotion.getEmotion() == EmotionEnum.SAD -> {
                Intent(context, SadnessDownRegulation::class.java)
            }
            emotion.getEmotion() == EmotionEnum.ANGRY -> {
                Intent(context, AngerDownRegulation::class.java)
            }
            else -> {
                Intent(context, NoRegulation::class.java)
            }
        }
    }
}