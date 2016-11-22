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

    // initialize SmartWord with a file of English words
    // For each word in words.txt, add to trie with frequency 0
	//hello
    public SmartWord (String wordFile) {

    }

    // process old messages from oldMessageFile
    // For each word in oldMessages.txt, add to trie, including frequency
    public void processOldMessages (String oldMessageFile) {

    }

    // based on a letter typed in by the user, return 3 word guesses in an array
    // letter: letter typed in by the user
    // letterPosition:  position of the letter in the word, starts from 0
    // wordPosition: position of the word in a message, starts from 0
    public String[] guess (char letter,  int letterPosition, int wordPosition) {
	
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

    }

}
