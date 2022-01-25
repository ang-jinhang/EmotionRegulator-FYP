import csv
import re
import emot
import random
from nltk.tokenize import word_tokenize
from nltk.corpus import stopwords
from cleaner_helper import custom_extended_stopwords, custom_shortforms, custom_direct_replacement_dict

predict = "arousal"

infile = open("dataset-fb-valence-arousal-anon.csv", "r", encoding="utf-8")
infile_reader = csv.reader(infile, delimiter=",")
next(infile_reader)

emot_obj = emot.core.emot()
full_stopwords_set = set.union(set(custom_extended_stopwords), set(stopwords.words("english")))

outfile = open(f"dataset-bow-{predict}.csv", "w", encoding="utf-8", newline="")
outfile_writer = csv.writer(outfile, delimiter=",")

outfile_columns = ["text", predict]
outfile_writer.writerow(outfile_columns)

for row_values in infile_reader:
  contents = row_values[0]
  contents = re.sub(r"<\w+>", "", contents)
  contents = re.sub(r"[^\x00-\x7F]", "", contents)
  contents = re.sub(r"\.\.+", " .. ", contents)
  text_emot_info = emot_obj.emoticons(contents)
  segments = []
  scratch_text = contents
  pos_counter = 0
  for i in range(0, len(text_emot_info["location"])):
    location = text_emot_info["location"][i]
    replacement = text_emot_info["mean"][i]
    segment = scratch_text[0:location[0] - pos_counter]
    segments.append(segment)
    segments.append(replacement)
    scratch_text = scratch_text[location[1] - pos_counter:]
    pos_counter = location[1]
  segments.append(scratch_text)
  contents = " ".join(segments)

  contents = contents.lower()
  # for direct_replacement in custom_direct_replacement_dict.items():
  #   contents = re.sub(direct_replacement[0], direct_replacement[1], contents)

  contents_tokens = word_tokenize(contents)
  contents_segments = []
  for token in contents_tokens:
    # if token in custom_shortforms.keys():
    #   token = custom_shortforms[token]
    token = re.sub(r"[^a-z.,!?]", "", token)
    if token not in full_stopwords_set:
      contents_segments.append(token)
    contents = " ".join(contents_segments)

  to_write_contents = [contents]

  if predict == "valence":
    valence1 = row_values[1]
    valence2 = row_values[2]
    to_write_contents.append((float(valence1) + float(valence2)) / 2)
  elif predict == "arousal":
    arousal1 = row_values[3]
    arousal2 = row_values[4]
    to_write_contents.append((float(arousal1) + float(arousal2)) / 2)

  outfile_writer.writerow(to_write_contents)

