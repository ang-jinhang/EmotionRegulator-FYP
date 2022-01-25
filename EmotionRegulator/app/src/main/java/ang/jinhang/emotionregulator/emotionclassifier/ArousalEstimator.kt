package ang.jinhang.emotionregulator.emotionclassifier

import ang.jinhang.emotionregulator.AppContext.Companion.appContext
import ang.jinhang.emotionregulator.R
import weka.classifiers.misc.InputMappedClassifier
import weka.core.DenseInstance
import weka.core.Instances
import weka.core.SelectedTag
import weka.core.SerializationHelper
import weka.core.converters.ConverterUtils
import weka.core.tokenizers.WordTokenizer
import weka.filters.Filter
import weka.filters.unsupervised.attribute.StringToWordVector

class ArousalEstimator() {
    private val arousalModel = SerializationHelper.read(
        appContext.resources.openRawResource(R.raw.arousal_model)
    ) as InputMappedClassifier

    private val arousalSource =
        ConverterUtils.DataSource(appContext.resources.openRawResource(R.raw.arousal_dataset))
    private val arousalDataset = arousalSource.dataSet

    init {
        arousalDataset.setClassIndex(1)
    }

    private val tokenizer = WordTokenizer()

    init {
        tokenizer.delimiters = " "
    }

    private val arousalS2wvFilter = StringToWordVector()

    init {
        arousalS2wvFilter.tfTransform = true
        arousalS2wvFilter.idfTransform = true
        arousalS2wvFilter.attributeIndices = "1"
        arousalS2wvFilter.minTermFreq = 12
        arousalS2wvFilter.outputWordCounts = true
        arousalS2wvFilter.tokenizer = tokenizer
        arousalS2wvFilter.normalizeDocLength =
            SelectedTag(StringToWordVector.FILTER_NORMALIZE_ALL, StringToWordVector.TAGS_FILTER)
    }

    fun estimateArousal(input: String): Double {
        val testInstance = DenseInstance(2)
        testInstance.setDataset(arousalDataset)
        testInstance.setValue(0, input)
        arousalDataset.add(testInstance)

        val filterCopy = Filter.makeCopy(arousalS2wvFilter)
        val datasetCopy = Instances(arousalDataset)
        filterCopy.setInputFormat(datasetCopy)

        val processedDataset = Filter.useFilter(datasetCopy, filterCopy)
        processedDataset.setClass(processedDataset.attribute("arousal"))
        while (true) {
            val processedInstance = filterCopy.output()
            if (processedInstance != null) {
                processedDataset.add(processedInstance)
            } else {
                break
            }
        }

        return arousalModel.classifyInstance(processedDataset.lastInstance())
    }
}