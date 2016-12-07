import java.util.ArrayList;
import java.util.Collections;

public class Trie extends Tree<Character> {

    Node<Character> currentLetter = root;  //Keeps track of the node of the letter being checked

    //Uses super constructor to set root, and set root element to blank
    public Trie (Node<Character> r) {
        super(r);
    }

    //Set the current letter back to the root
    public void resetCurrentLetter () {
        currentLetter = root;
    }

    //Adds a word to the trie by adding the letter to the trie if not present
    public void addWord (final String s, boolean isFromMessages) {
        Node<Character> current = root;
        for (int i = 0; i < s.length(); i++) {
            if (current.getChildren()[s.charAt(i) - 97] != null) {
                current = current.getChildren()[s.charAt(i) - 97];
            } else {
                current = addChild(current, new Node<Character>(s.charAt(i), current));
            }
        }

        //Mark last node as the end of a word, and if it is from a message file, increment its frequency
        current.setEndOfWord(true);
        if (isFromMessages) {
            current.incrementFrequency();
        }
    }

    //Finds all words that could end with entered characters, and return 3 with highest frequency
    public String[] findGuesses (final char c) {
        final SortedPriorityQueue<Integer, String> words = new SortedPriorityQueue();

        //Traverse down the trie to match the entered characters
        currentLetter = currentLetter.getChildren()[c - 97];

        //Traverse all nodes below end of entered string; guessing is done in method
        postOrder(currentLetter, words);

        //Convert sorted priority queue to array of strings
        final String[] guesses = new String[3];
        for (int i = Math.min(2, words.size() - 1); i >= 0 ; i--) {
            guesses[i] = words.removeMin().getValue();
        }
        return guesses;
    }

    //Traverses through all nodes, and traverses upward to construct a word if the current node is the end of a word
    public void postOrder (final Node<Character> v, final SortedPriorityQueue words) {
        for (final Node<Character> w : v.getChildren()) {
            if (w != null) {
                postOrder(w, words);
            }
        }

        if (v.isEndOfWord()) {
            constructWord (v, words);
        }
    }

    //Construct a word by adding each letter up and not including the root
    public void constructWord (final Node<Character> v, final SortedPriorityQueue<Integer, String> words) {
        final int frequency = v.getFrequency();
        Node<Character> current = v;
        String word = "";
        while (!current.isRoot()) {
            word = current.getElement() + word;
            current = current.getParent();
        }

        /*
         * If the priority queue is not full (size 3), add word to priority queue
         * Otherwise, check if current word's frequency is smaller than the priority
         * min, and if so remove the min and replace it with the current word
         */
        if (words.size() < 3) {
            words.insert(frequency, word);
        } else if (frequency >= (Integer) words.min().getKey()) {
            words.removeMin();
            words.insert(frequency, word);
        }
    }
}
