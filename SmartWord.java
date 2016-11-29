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


*/


public class SmartWord
{
    String[] guesses = new String[3];  // 3 guesses from SmartWord
    String previouslyEntered = "";   // Letters in string that have already been typed by user
    Trie wordDatabase = new Trie (new Node<String>("", null));  // Trie containing all words entered
    boolean endOfWord = false;  // Checks whether the letter being checked is the last letter in its word

    // initialize SmartWord with a file of English words
    public SmartWord (String wordFile) throws FileNotFoundException {
        final Scanner sc = new Scanner(new File(wordFile));
        while (sc.hasNextLine()) {
            String word = sc.nextLine();
            word = word.replaceAll("[^a-zA-Z]", "").toLowerCase();
            wordDatabase.addWord(word, false);
        }
    }

    // process old messages from oldMessageFile
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
    public String[] guess (char letter,  int letterPosition, int wordPosition) {
        if (!endOfWord) {
            previouslyEntered += letter;
            guesses = wordDatabase.findGuesses(previouslyEntered);
        }
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

        //Printing loop; only used for testing, remove before submitting
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
            //Guessed correctly
            previouslyEntered = "";
            wordDatabase.addWord(correctWord, true);
            System.out.println("Correctly guessed. Correct word: " + correctWord); //Remove before submitting
        } else {
            if (correctWord == null) {
                //Guessed incorrectly and not reached end of word
                //Ensure that the program is not checking the last letter of the word anymore
                endOfWord = false;
            } else {
                //Guessed incorrectly and reached end of word
                previouslyEntered = "";
                wordDatabase.addWord(correctWord, true);
                endOfWord = true;
                System.out.println("Incorrectly guessed. Correct word: " + correctWord); //Remove before submitting
            }
        }
    }
}
