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

class ValenceEstimator() {
    private val valenceModel = SerializationHelper.read(
        appContext.resources.openRawResource(R.raw.valence_model)
    ) as InputMappedClassifier

    private val valenceSource =
        ConverterUtils.DataSource(appContext.resources.openRawResource(R.raw.valence_dataset))
    private val valenceDataset = valenceSource.dataSet

    init {
        valenceDataset.setClassIndex(1)
    }

    private val tokenizer = WordTokenizer()

    init {
        tokenizer.delimiters = " "
    }

    private val valenceS2wvFilter = StringToWordVector()

    init {
        valenceS2wvFilter.tfTransform = true
        valenceS2wvFilter.idfTransform = true
        valenceS2wvFilter.attributeIndices = "1"
        valenceS2wvFilter.minTermFreq = 5
        valenceS2wvFilter.outputWordCounts = true
        valenceS2wvFilter.tokenizer = tokenizer
        valenceS2wvFilter.normalizeDocLength =
            SelectedTag(StringToWordVector.FILTER_NORMALIZE_ALL, StringToWordVector.TAGS_FILTER)
    }

    fun estimateValence(input: String): Double {
        val testInstance = DenseInstance(2)
        testInstance.setDataset(valenceDataset)
        testInstance.setValue(0, input)
        valenceDataset.add(testInstance)

        val filterCopy = Filter.makeCopy(valenceS2wvFilter)
        val datasetCopy = Instances(valenceDataset)
        filterCopy.setInputFormat(datasetCopy)

        val processedDataset = Filter.useFilter(datasetCopy, filterCopy)
        processedDataset.setClass(processedDataset.attribute("valence"))
        while (true) {
            val processedInstance = filterCopy.output()
            if (processedInstance != null) {
                processedDataset.add(processedInstance)
            } else {
                break
            }
        }

        return valenceModel.classifyInstance(processedDataset.lastInstance())
    }
}