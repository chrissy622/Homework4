/*
Chrisina Nguyen
CSC 331 - 002
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class TextFileManager {
    private String fileName;
    private String author;
    private int tokenCount;
    private static int outputCounter = 0;
    private Scanner scanFile;
    private String[] wordsDB;
    private WordFrequency[] WordFrequencyList, WordFrequencySorted;



    public TextFileManager(String inputFile) throws FileNotFoundException {
        this.fileName = inputFile;
        this.scanFile = new Scanner(new File(this.fileName));

        // counts number of tokens
        while (this.scanFile.hasNextLine()) {
            this.tokenCount++;
            this.scanFile.next();
        }

        this.scanFile.close();
    }

    /*
    reads a text file and saves each word to an instance variable one-dimensional string array called wordsDB.
    The method parameters are (1) the name of the author or speaker and (2) the name of the text file containing the
    author’s or speaker’s words. The method stores each word in the file in a separate element in the wordsDB array.
     */
    public void processFile(String author, String textFile) throws FileNotFoundException {
        this.scanFile = new Scanner(new File(this.fileName));
        this.author = author;
        this.wordsDB = new String[this.tokenCount]; //init array with index == amount of words ^

        int index = 0;
        String word = null;
        while (this.scanFile.hasNextLine()) {
            word = this.scanFile.next();
            this.wordsDB[index] = word;
            index++;
        }

        this.scanFile.close();
    }


    /*
    writes the elements of wordsDB to a text file, one word per line in the order in which they were spoken or written.
     The name of the output file should be the name of the author or speaker plus “Words.txt”. For example, if the
     author is “Obama” then the output text file would have the name “ObamaWords.txt”. if the method is called several
     times with the same author name, the name of the file should change by appending 1,2,3 etc. For example,
     “ObamaWords.txt”. “ObamaWords1.txt”. “ObamaWords2.txt”, etc.
     */
    public void saveOriginalWords() {
        outputCounter++;
        String outputFileName = this.author + "Words" + outputCounter;
        try {
            Formatter output = new Formatter(outputFileName);
            for (String word : this.wordsDB) {
                output.format("%s\n", word);
            }
            output.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    /*
    uses the wordsDB array to compute and store the number of times each word is used. This method assumes the
    existence of a class called WordFrequency  that that has two instance variables representing (1) a word and
    (2) the number of time that word was used (the words frequency)
     */
    public void computeFrequency() {
        ArrayList<String> uniqueWords = new ArrayList<>();
        for(String word : this.wordsDB){
            String clean = word.replaceAll("[^a-zA-Z]","").toLowerCase();
            if (!uniqueWords.contains(clean)){
                uniqueWords.add(clean);
            }
        }

        this.WordFrequencyList = new WordFrequency[uniqueWords.size()];
        int freqIndex = 0;
        for(String unique : uniqueWords) {
            int uniqueFrequency = 0;
            for(String counting : this.wordsDB) {
                String cleanCounting = counting.replaceAll("[^a-zA-Z]", "").toLowerCase();
                try {
                    if (cleanCounting.equals(unique)) {
                        uniqueFrequency++;
                    }
                } catch (Exception ignored) {
                }
            }
            WordFrequency obj = new WordFrequency(unique, uniqueFrequency);
            this.WordFrequencyList[freqIndex++] = obj;

        }
    }


    /*
    writes the list of words and their frequencies to a text file. The name of the file should be the author’s
    name + “SortedWordsFrequencies.txt”
     */
    public void writeFrequencyData() {
        String outputFileName = this.author + "SortedWordFrequency.txt";
        try {
            FileWriter output = new FileWriter(outputFileName);
            for (WordFrequency wf : WordFrequencyList) {
                output.write(wf.getWord() + " " + wf.getFrequency() + "\n");
            }
            output.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /*
    Sorts the given array by its frequency
    I couldnt figure out how to use the comparable so I just did a bubble sort
     */
    public void sorter(){
        this.WordFrequencySorted = new WordFrequency[WordFrequencyList.length];
        System.arraycopy(WordFrequencyList, 0, WordFrequencySorted, 0, WordFrequencySorted.length);

        for (int i = 0; i < WordFrequencySorted.length - 1;  i++){
            for (int j = 0; j < WordFrequencySorted.length - 1 - i; j++) {
                if (WordFrequencySorted[j].getFrequency() > WordFrequencySorted[j+1].getFrequency()) {
                    WordFrequency limbo = WordFrequencySorted[j];
                    WordFrequencySorted[j] = WordFrequencySorted[j+1];
                    WordFrequencySorted[j+1] = limbo;
                }
            }

        }
    }

    /*
    sorts the wordFrequencyList by the words in the list in descending order and writes it to an output file.
     */
    public void sortByFrequency() {

        String outputFileName = this.author + "SortedWordFrequencyDescending.txt";
        try {
            FileWriter output = new FileWriter(outputFileName);
            output.write("SORTED IN DESCENDING ORDER:\n\n");
            for (WordFrequency wf : this.WordFrequencySorted) {
                output.write(wf.getWord() + " " + wf.getFrequency() + "\n");
            }
            output.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    sorts the wordFrequencyList by the words in the list in ascending order and writes it to an output file.
     */
    public void sortByWord() {
        String outputFileName = this.author + "SortedWordFrequencyAscending.txt";
        try {
            FileWriter output = new FileWriter(outputFileName);
            output.write("SORTED IN ascending ORDER:\n\n");
            for (int i = this.WordFrequencySorted.length - 1; i >= 0; i--) {
                output.write(this.WordFrequencySorted[i].getWord() + " " + this.WordFrequencySorted[i].getFrequency() + "\n");
            }
            output.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    findWord that searches for and returns a word and its frequency, if the word is in the list of or an appropriate
    message if the word is not in the list.
     */
    public void findWord(String targetWord) {
        String targetLower = targetWord.replaceAll("[^a-zA-Z]", "").toLowerCase();
        int frequency = 0;
        for (String findWord : this.wordsDB) {
            try {
                String findLower = findWord.replaceAll("[^a-zA-Z]", "").toLowerCase();
                if (findLower.equals(targetLower)){
                    frequency++;
                }
            }
            catch (Exception ignored) {
                System.out.println("Sorry we could not find that word");
            }

        }
        System.out.println("The word " + "\"" + targetWord + "\"" + " has been found " + frequency + " times");
    }

    /*
    searches for and prints all the words with a frequency of “val”.
     */
    public void findFrequency(int val) {
        System.out.println("These are all the words that have been found " + val + " times: \n");
        for(WordFrequency wf : this.WordFrequencySorted) {
            if (wf.getFrequency() == val){
                System.out.print(wf.getWord() + ", ");
            }
        }
    }

    public void minFrequency(int min) {
        System.out.println("These are all the words that have been found a minimum of " + min + " times:\n");
        for (WordFrequency wf : this.WordFrequencySorted) {
            if (wf.getFrequency() >= min) {
                System.out.print(wf.getWord() + ", ");
            }
        }
    }







}





