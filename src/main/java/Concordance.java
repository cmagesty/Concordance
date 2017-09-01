/**
 * Created by Chris on 8/30/17.
 */

import java.text.BreakIterator;
import java.util.*;

public class Concordance {
    private Map<String, List<Integer>> concordanceMap;

    public Concordance() {
        this.concordanceMap = new TreeMap<>();
    }

    public void run(String text) {
        BreakIterator sentenceIterator = BreakIterator.getSentenceInstance();
        BreakIterator wordIterator = BreakIterator.getWordInstance();

        int sentenceCounter = 0;
        int sentenceIndex = 0;

        sentenceIterator.setText(text);

        while (BreakIterator.DONE != sentenceIterator.next()) {
            String sentence = text.substring(sentenceIndex, sentenceIterator.current());
            sentenceCounter++;

            int wordIdx = 0;
            wordIterator.setText(sentence);

            while (BreakIterator.DONE != wordIterator.next()) {
                String word = sentence.substring(wordIdx, wordIterator.current());

                if (Character.isLetterOrDigit(word.charAt(0))) {
                    if (this.concordanceMap.containsKey(word)) {
                        this.concordanceMap.get(word).add(sentenceCounter);
                    } else {
                        List<Integer> sentenceNumbers = new ArrayList<>();
                        sentenceNumbers.add(sentenceCounter);
                        this.concordanceMap.put(word, sentenceNumbers);
                    }
                }
                wordIdx = wordIterator.current();
            }
            sentenceIndex = sentenceIterator.current();
        }
        print();
    }

    public void print() {
        int counter = 0;
        for (String word : this.concordanceMap.keySet()) {
            List<Integer> sentenceNumbers = this.concordanceMap.get(word);
            System.out.println(getLetterCounter(counter) + ". " + word + " {" + sentenceNumbers.size() + ":" + sentenceNumbers.toString().replace("[", "").replace("]", "") + "}");
        counter++;
        }
    }

    private static String getLetterCounter(int counter) {
        StringBuffer result = new StringBuffer();
        int remainder = (counter) % 26;
        char digit = (char) (remainder + 97);
        for(int i = counter/26; i >= 0; i--) {
            result.append(digit);
        }
        return result.toString();
    }

    public static void main(String[] args) {

            String text = "Given an arbitrary text document written in English, write a program that will generate \n" +
                    "a concordance, i.e. an alphabetical list of all word occurrences, labeled with word \n" +
                    "frequencies. Bonus: label each word with the sentence numbers in which each occurrence appeared.";

                Concordance concordance = new Concordance();
                concordance.run(text.toLowerCase());
    }
}