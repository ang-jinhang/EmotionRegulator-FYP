package ang.jinhang.emotionregulator.emotionclassifier

import java.util.*

class TextPreprocessor {
    private val regexNonAscii = Regex("[^\\x00-\\x7F]")
    private val regexPeriods = Regex("\\.\\s+(\\.\\s+)+")
    private val regexDisallowedChars = Regex("[^a-z.,!?]")
    private val regexWhiteSpace = Regex("\\s")

    private val emoticonConverter = EmoticonConverter()
    private val stopwordIdentifier = StopwordIdentifier()

    fun preprocessText(input: String): String {
        var newString = input

        newString = newString.replace(regexNonAscii, "")

        newString = emoticonConverter.convertString(newString)

        newString = newString.replace(".", " . ")
        newString = newString.replace(regexPeriods, " .. ")
        newString = newString.replace("'", " ' ")
        newString = newString.replace(",", " , ")
        newString = newString.replace("?", " ? ")
        newString = newString.replace("!", " ! ")

        newString = newString.toLowerCase(Locale.ENGLISH)

        val stringTokens = newString.split(regexWhiteSpace)
        val newStringTokens = mutableListOf<String>()

        stringTokens.forEach {
            val token = it.replace(regexDisallowedChars, "")
            if (!stopwordIdentifier.isStopword(token)) {
                newStringTokens.add(token)
            }
        }

        return newStringTokens.joinToString(separator = " ")
    }
}