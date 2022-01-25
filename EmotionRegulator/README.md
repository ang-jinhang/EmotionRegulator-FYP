Open this project in Android Studio to compile and run the application. 

---

The Weka JAR file included in the `/weka` folder is compiled from the `tiny-weka` repository (https://github.com/Waikato/tiny-weka) after including the InputMappedClassifier, RandomForest, StringToWordVector classes and their dependencies. These added classes come from the Weka 3.8 repository (https://github.com/Waikato/weka-3.8). 

The `.arff` and `.model` files in the directory `\app\src\main\res\raw` are generated with Weka from the datasets created with the Python script in the `dataset_cleaner` folder in the parent directory. See the README in the `dataset_cleaner` folder for dataset sources and citations. 