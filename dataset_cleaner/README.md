Usage: 

Use `create_bow_dataset.py` to generate CSV files containing only the cleaned text and a column of either arousal or valence values. Edit the `predict`, `infile` and `outfile` variables in the script to make the script generate different datasets depending on needs. 

Variables to edit: 

- `predict` - Takes a value of either "arousal" or "valence". 
- `infile` - The filename of the original dataset in CSV format. 
- `outfile` - The filename of the output cleaned dataset in CSV format.

---

The `emot` library is adapted from https://github.com/NeelShah18/emot with more emoticons added into the dictionary while removing some emoticon definitions that do not convey emotions. 

The dataset `dataset-fb-valence-arousal-anon.csv` is downloaded from http://wwbp.org/downloads/public_data/dataset-fb-valence-arousal-anon.csv, while the other dataset `dataset-fb-valence-arousal-anon-nouseless.csv` is adapted from the previous dataset with some rows that do not reflect emotions (advertisements, "share this on your wall", etc) removed. 

Citation for the dataset: 

> Preoţiuc-Pietro, D., Schwartz, H. A., Park, G., Eichstaedt, J., Kern, M., Ungar, L., & Shulman, E. (2016, June). Modelling Valence and Arousal in Facebook posts. In Proceedings of the 7th workshop on computational approaches to subjectivity, sentiment and social media analysis (pp. 9-15).