package ang.jinhang.emotionregulator.emotionclassifier

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object EmotionClassifier {
    private val valenceEstimator = ValenceEstimator()
    private val arousalEstimator = ArousalEstimator()
    private val textPreprocessor = TextPreprocessor()

    private fun estimateValence(input: String): Double {
        return valenceEstimator.estimateValence(input)
    }

    private fun estimateArousal(input: String): Double {
        return arousalEstimator.estimateArousal(input)
    }

    suspend fun estimateEmotion(input: String): Emotion {
        val processedString = textPreprocessor.preprocessText(input)

        var valence: Double
        var arousal: Double

        withContext(Dispatchers.IO) {
            valence = estimateValence(processedString)
            arousal = estimateArousal(processedString)
        }

        return Emotion(valence, arousal)
    }
}