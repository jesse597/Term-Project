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
    Trie wordDatabase = new Trie (new Node<String>("", null));

    // initialize SmartWord with a file of English words
    // For each word in words.txt, add to trie with frequency 0
    public SmartWord (String wordFile) throws FileNotFoundException {
        final Scanner sc = new Scanner(new File(wordFile));
        while (sc.hasNextLine()) {
            String word = sc.nextLine();
            word = word.replaceAll("[^a-zA-Z]", "").toLowerCase();
            wordDatabase.addWord(word);
        }
    }

    // process old messages from oldMessageFile
    // For each word in oldMessages.txt, add to trie, including frequency
    public void processOldMessages (String oldMessageFile) throws FileNotFoundException {
        final Scanner sc = new Scanner(new File(oldMessageFile));
        while (sc.hasNextLine()) {
            final String[] words = sc.nextLine().split(" ");
            for (String word : words) {
                word = word.replaceAll("[^a-zA-Z]", "").toLowerCase();
                wordDatabase.addWord(word); 
            }
        }
    }

    // based on a letter typed in by the user, return 3 word guesses in an array
    // letter: letter typed in by the user
    // letterPosition:  position of the letter in the word, starts from 0
    // wordPosition: position of the word in a message, starts from 0
    public String[] guess (char letter,  int letterPosition, int wordPosition) {
	previouslyEntered += letter;
	final ArrayList<StringFrequencyPair> words = wordDatabase.findAll(previouslyEntered);
	guesses = findMax3(words);
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
        if (correctWord != null) {
            previouslyEntered = "";
            wordDatabase.addWord(correctWord);
        }
    }

    public String[] findMax3 (final ArrayList<StringFrequencyPair> words) {
        final ArrayList<StringFrequencyPair> max3 = new ArrayList<StringFrequencyPair>();
        int max = Integer.MIN_VALUE;
        StringFrequencyPair min = new StringFrequencyPair("", Integer.MAX_VALUE);
        for (final StringFrequencyPair SFP : words) {
            if (SFP.getFrequency() >= max) {
                if (max3.size() == 3) {
                    for (final StringFrequencyPair SFP2 : max3) {
                        if (SFP2.getFrequency() <= min.getFrequency()) {
                            min = SFP2;
                        }
                    }
                    max3.remove(min);
                }
                max3.add(SFP);
                min = SFP;
                max = min.getFrequency();
            }
        }

        final String[] strings = new String[3];
        for (int i = 0; i < Math.min(3, max3.size()); i++) {
            strings[i] = max3.get(i).getWord();
        }
        return strings;
    }
}
