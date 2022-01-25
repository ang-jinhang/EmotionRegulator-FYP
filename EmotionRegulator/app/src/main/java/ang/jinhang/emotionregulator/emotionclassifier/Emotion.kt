package ang.jinhang.emotionregulator.emotionclassifier

class Emotion(var valence: Double, var arousal: Double) {
    fun getEmotion(): EmotionEnum {
        return if (valence >= 5 && arousal < 5) {
            EmotionEnum.CALM
        } else if (valence >= 5 && arousal >= 5) {
            EmotionEnum.HAPPY
        } else if (valence < 5 && arousal < 5) {
            EmotionEnum.SAD
        } else {
            EmotionEnum.ANGRY
        }
    }
}