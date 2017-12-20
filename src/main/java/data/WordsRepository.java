package data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 *
 */
public class WordsRepository {

  private Set<Word> words = new LinkedHashSet<>();

  public void addWord(Word word) {
    words.add(word);
  }

  public List<Word> getDictionary() {
    return new ArrayList<>(words);
  }

  public Word findWord(String token) {
    if (token == null) {
      return null;
    }
    for (Word word : words) {
      if (token.equals(word.original)
          || token.equals(word.translation)) {
        return word;
      }
    }
    return null;
  }

  public int size() {
    return words.size();
  }

  public static class Word {
    public final String original;
    public final String translation;

    public Word(String original, String translation) {
      this.original = original;
      this.translation = translation;
    }
  }
}


