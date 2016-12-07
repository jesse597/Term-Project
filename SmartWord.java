import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/*

  Authors (group members): Jesse Torres, Ian Dillon, Rishob Guha Patra
  Email addresses of group members: jtorres2015@my.fit.edu, idillon2015@my.fit.edu
  Group name: Group 3d

  Course: CSE2010
  Section: 03

  Description of the overall algorithm:
  Stores all words in words.txt in a standard trie, with no frequency
  Stores all words in old messages in same standard trie, incrementing frequency of each word
  For each letter entered, add to previously entered string, descend trie to match
  entered string, and find 3 words with highest frequency below the matched string
  When either guessed correctly or reached end of word, clear previously entered string

*/


public class SmartWord {

    String[] guesses = new String[3];  // 3 guesses from SmartWord
    //String[] oldGuesses = new String[3];
    String previouslyEntered = "";   // Letters that have already been typed by user
    Trie wordDatabase = new Trie (new Node<Character>(' ', null));  // Trie containing all words entered

    // Initialize SmartWord with a file of English words
    // Add each word to a standard trie without incrementing each word's frequency
    public SmartWord (String wordFile) throws FileNotFoundException {
        
        final Scanner sc = new Scanner(new File(wordFile));
        while (sc.hasNextLine()) {
            String word = sc.nextLine();
            word = word.replaceAll("[^a-zA-Z]", "").toLowerCase();
            wordDatabase.addWord(word, false);
        }
        
    }

    // Process old messages from oldMessageFile
    // Add each word to the same trie from before, this time incrementing frequency
    public void processOldMessages (String oldMessageFile) throws FileNotFoundException {
        final Scanner sc = new Scanner(new File(oldMessageFile));
        while (sc.hasNextLine()) {
            final String[] words = sc.nextLine().split(" ");
            for (String word : words) {
                word = word.replaceAll("[^a-zA-Z]", "").toLowerCase();
                wordDatabase.addWord(word, true); 
            }
        }
    }

    // based on a letter typed in by the user, return 3 word guesses in an array
    // letter: letter typed in by the user
    // letterPosition:  position of the letter in the word, starts from 0
    // wordPosition: position of the word in a message, starts from 0
    public String[] guess (char letter, int letterPosition, int wordPosition) {
        previouslyEntered += letter;
        guesses = wordDatabase.findGuesses(letter);
        return guesses;
    }

    // feedback on the 3 guesses from the user
    // isCorrectGuess: true if one of the guesses is correct
    // correctWord: 3 cases:
    // a.  correct word if one of the guesses is correct
    // b.  null if none of the guesses is correct, before the user has typed in 
    //            the last letter
    // c.  correct word if none of the guesses is correct, and the user has 
    //            typed in the last letter
    // That is:
    // Case       isCorrectGuess      correctWord   
    // a.         true                correct word
    // b.         false               null
    // c.         false               correct word
    public void feedback (boolean isCorrectGuess, String correctWord) {

        
        if (!previouslyEntered.equals("")) {
            System.out.println("Entered: " + previouslyEntered);
            System.out.print("Guesses: ");
            for (int i = 0; i < 3; i++) {
                if (guesses[i] != null) {
                    System.out.print(guesses[i] + " ");
                }
            }
            System.out.println();
        }
        

        if (isCorrectGuess) {
            //Guessed correctly, clear previously entered and add correct word to trie
            previouslyEntered = "";
            wordDatabase.resetCurrentLetter();
            wordDatabase.addWord(correctWord, true);
            System.out.println("Correctly guessed. Correct word: " + correctWord);
        } else {
            if (correctWord == null) {
                //Guessed incorrectly and not reached end of word
                
            } else {
                //Guessed incorrectly and reached end of word
                //Clear previously entered and add word to trie
                previouslyEntered = "";
                wordDatabase.resetCurrentLetter();
                wordDatabase.addWord(correctWord, true);
                System.out.println("Incorrectly guessed. Correct word: " + correctWord);
            }
        }
    }
}
