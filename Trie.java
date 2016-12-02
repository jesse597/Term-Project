import java.util.ArrayList;

public class Trie extends Tree<String> {

    //Uses super constructor to set root, and set root element to blank
    public Trie (Node<String> r) {
        super(r);
        root.setElement("");
    }

    //Adds a word to the trie by adding the letter to the trie if not present
    public void addWord (final String s, boolean isFromMessages) {
        Node<String> v = root;
        for (int i = 0; i < s.length(); i++) {
            boolean letterFound = false;
            for (final Node<String> n : v.getChildren()) {
                if (n.getElement().charAt(0) == s.charAt(i)) {
                    v = n;
                    letterFound = true;
                    break;
                }
            }
            if (!letterFound) {
                v = addChild(v, new Node(((Character) s.charAt(i)).toString(), v));
            }
        }

        //Mark last node as the end of a word, and if it is from a message file, increment its frequency
        v.setEndOfWord(true);
        if (isFromMessages) {
            v.incrementFrequency();
        }
    }

    //Finds all words that could end with entered characters, and return 3 with highest frequency
    public String[] findGuesses (final String s) {
        final SortedPriorityQueue<Integer, String> words = new SortedPriorityQueue();

        //Traverse down the trie to match the entered characters
        Node<String> current = root;
        for (int i = 0; i < s.length(); i++) {
            for (final Node<String> n : current.getChildren()) {
                if (n.getElement().charAt(0) == s.charAt(i)) {
                    current = n;
                    break;
                }
            }
        }

        //Traverse all nodes below end of entered string; guessing is done in method
        postOrder (current, words);

        //Convert sorted priority queue to array of strings
        final String[] guesses = new String[3];
        for (int i = Math.min(2, words.size() - 1); i >= 0 ; i--) {
            guesses[i] = words.removeMin().getValue();
        }
        return guesses;
    }

    //Traverses through all nodes, and traverses upward to construct a word if the current node is the end of a word
    public void postOrder (final Node<String> v, final SortedPriorityQueue words) {
        for (final Node<String> w : v.getChildren()) {
            postOrder(w, words);
        }

        if (v.isEndOfWord()) {
            constructWord (v, words);
        }
    }

    //Construct a word by adding each letter up and not including the root
    public void constructWord (final Node<String> v, final SortedPriorityQueue<Integer, String> words) {
        final int frequency = v.getFrequency();
        Node<String> current = v;
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
