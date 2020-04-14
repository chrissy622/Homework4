/*
Chrisina Nguyen
CSC 331 - 002
 */

import java.io.FileNotFoundException;

public class TestTextFileManager {

    public static void main(String[] args) throws FileNotFoundException {
        String inputFile = "Text1";
        TextFileManager prog = new TextFileManager(inputFile);

        System.out.println("Testing processFile...");
        prog.processFile("Obama", "Text1");

        System.out.println("Testing saveOriginalWords...");
        prog.saveOriginalWords();
        System.out.println("Testing saveOrginalWords again...");
        prog.saveOriginalWords();

        System.out.println("Testing computeFrequency...");
        prog.computeFrequency();

        System.out.println("Testing writeFrequencyData...");
        prog.writeFrequencyData();

        System.out.println("Testing sorter...");
        prog.sorter();

        System.out.println("Testing sortByFrequency...");
        prog.sortByFrequency();

        System.out.println("Testing sortbyWord...");
        prog.sortByWord();
        System.out.println();

        System.out.println("Testing findWord...");
        prog.findWord("the");
        prog.findWord("american");
        prog.findWord("economy");
        System.out.println();

        System.out.println("Testing findFrequency, finding words with a frequency of 13...");
        prog.findFrequency(13);
        System.out.println();
        System.out.println();

        System.out.println("Testing minFrequency, finding words with a frequency of 20 or more...");
        prog.minFrequency(20);

    }
}
